#!/bin/bash

# Prompt the user to enter the PostgreSQL password
echo "Please enter the PostgreSQL password:"
read -s POSTGRES_PASSWORD  # -s flag to hide input for security

# Stop and remove the PostgreSQL container if it is running
if [ "$(docker ps -a -f name=DB)" ]; then
    echo "Stopping and removing existing PostgreSQL container..."
    docker stop DB
    docker rm DB
fi

# Stop and remove the Spring Boot container if it is running
if [ "$(docker ps -a -f name=spring-demo)" ]; then
    echo "Stopping and removing existing Spring Boot container..."
    docker kill spring-demo
    docker rm spring-demo
fi

# Create Docker network if it doesn't exist
docker network inspect DB_net >/dev/null 2>&1 || \
    docker network create DB_net

# Run PostgreSQL containerfor data if it's not already running
if [ ! "$(docker ps -q -f name=DB)" ]; then
    echo "Starting new PostgreSQL container..."
    docker run \
      --net=DB_net \
      --net-alias=DB \
      --name DB \
      -e POSTGRES_PASSWORD="$POSTGRES_PASSWORD" \
      -d \
      --restart unless-stopped \
      --health-cmd="pg_isready -U postgres" \
      --health-interval=30s \
      --health-timeout=5s \
      --health-retries=3 \
      -p 5432:5432 \
      postgres:latest
else
    echo "PostgreSQL container is already running, reusing it."
fi

# Wait for PostgreSQL to be ready
echo "Waiting for PostgreSQL to become ready..."
sleep 10  # Adjust this based on your startup time

# Execute SQL script using psql CLI
echo "Executing SQL script..."

docker run --rm \
  --net=DB_net \
  -e POSTGRES_PASSWORD="$POSTGRES_PASSWORD" \
  -v ./src/main/resources:/tmp \
  postgres:latest sh -c "PGPASSWORD='$POSTGRES_PASSWORD' psql -h DB -p 5432 -U postgres -d postgres -f /tmp/schema.sql"

# Check if the execution was successful
if [ $? -ne 0 ]; then
    echo "Failed to execute SQL script. Exiting..."
    exit 1
fi

# Always rebuild the Spring Boot application image
echo "Building Spring Boot application image..."
docker build -t spring-demo .

# Run Spring Boot container, passing the POSTGRES_PASSWORD environment variable
echo "Starting new Spring Boot container..."
docker run \
  --net=DB_net \
  -p 8080:8080 \
  --name spring-demo \
  -e SPRING_DATASOURCE_PASSWORD="$POSTGRES_PASSWORD" \
  -d \
  --restart unless-stopped \
  --health-cmd="curl --fail http://localhost:8080/actuator/health || exit 1" \
  --health-interval=30s \
  --health-timeout=10s \
  --health-retries=3 \
  spring-demo


echo "swager: http://localhost:8080/swagger-ui.html"







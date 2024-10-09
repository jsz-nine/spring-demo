FROM gradle:8.10.2-jdk23 AS build
WORKDIR /app
COPY settings.gradle .
COPY build.gradle .
COPY src ./src
RUN gradle clean bootJar
FROM bellsoft/liberica-openjre-alpine:23 AS run
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-Xms32m","-Xmx64m", "-jar", "app.jar"]


SELECT 'CREATE DATABASE nine'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'nine')\gexec

CREATE SCHEMA IF NOT EXISTS internal;

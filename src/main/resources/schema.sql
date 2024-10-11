SELECT 'CREATE DATABASE nine'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'nine')\gexec

\c nine;
CREATE SCHEMA IF NOT EXISTS internal;

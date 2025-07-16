#!/bin/bash
set -e

echo "Postgres custom init script starting..."

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
  CREATE DATABASE "$POSTGRES_DB_NAME";
EOSQL

echo "Database '$POSTGRES_DB_NAME' created. Importing backup.sql..."

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB_NAME" </docker-entrypoint-initdb.d/backup.sql

echo "Backup import completed."

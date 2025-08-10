FROM postgres:latest

COPY init.sh /docker-entrypoint-initdb.d/init.sh
COPY backup.sql /docker-entrypoint-initdb.d/backup.sql

RUN chmod +x /docker-entrypoint-initdb.d/init.sh
RUN chmod +x /docker-entrypoint-initdb.d/backup.sql

RUN mkdir -p /var/log/postgresql && chown postgres:postgres /var/log/postgresql

CMD ["postgres", "-c", "config_file=/etc/postgresql/postgresql.conf"]

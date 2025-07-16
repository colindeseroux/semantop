# POSTGRES

## Backup

```sh
docker exec -t postgres pg_dump -U postgres semantop > backup.sql
```

## Fixed utf8

```sh
iconv -f UTF-16 -t UTF-8 backup.sql > fixed.sql
```

```sh
(Get-Content fixed.sql) | Set-Content -Encoding utf8 fixed.sql
```

```sh
rm backup.sql
```

```sh
mv fixed.sql backup.sql
```

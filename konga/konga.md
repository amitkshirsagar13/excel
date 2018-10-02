Points to be noted:
1. Konga can only allow adding admin urls if its not protected, or protected with key-auth or JWT.
2. Konga can only allow nodes which are using kong in cluster mode.
3. Kong in cluster mode means, running Multiple Kong instances using same database (Cassandra or Postgres)
```
docker run -d \
    --name kong-database \
    -p 9042:9042 \
    --rm cassandra:3

docker run --rm \
    --link kong-database:kong-database \
    -e "KONG_DATABASE=cassandra" \
    -e "KONG_CASSANDRA_CONTACT_POINTS=kong-database" \
    kong kong migrations up

docker run -d --rm --name kong \
    --link kong-database:kong-database \
    -e "KONG_DATABASE=cassandra" \
    -e "KONG_CASSANDRA_CONTACT_POINTS=kong-database" \
    -e "KONG_PROXY_ACCESS_LOG=/dev/stdout" \
    -e "KONG_ADMIN_ACCESS_LOG=/dev/stdout" \
    -e "KONG_PROXY_ERROR_LOG=/dev/stderr" \
    -e "KONG_ADMIN_ERROR_LOG=/dev/stderr" \
    -e "KONG_ADMIN_LISTEN=0.0.0.0:8001" \
    -e "KONG_ADMIN_LISTEN_SSL=0.0.0.0:8444" \
    -p 8000:8000 \
    -p 8443:8443 \
    -p 8001:8001 \
    -p 8444:8444 \
    kong

docker run -d --rm --name konga -p 1337:1337 \
             --link kong:kong \
             --name konga \
             -e "NODE_ENV=production" \
             pantsel/konga

```
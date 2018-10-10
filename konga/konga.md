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














docker run --rm \
-e "KONG_DATABASE=cassandra" \
-e "KONG_CASSANDRA_CONTACT_POINTS=168.61.55.8,52.162.212.69" \
-e "KONG_CASSANDRA_KEYSPACE=master_kong" \
-e "KONG_CASSANDRA_CONSISTENCY=ONE" \
-e "KONG_CASSANDRA_LB_POLICY=DCAwareRoundRobin" \
-e "KONG_CASSANDRA_LOCAL_DATACENTER=EUS" \
-e "KONG_CASSANDRA_REPL_STRATEGY=NetworkTopologyStrategy" \
-e "KONG_CASSANDRA_DATA_CENTERS=EUS:1,NCUS:1" \
-e "KONG_DB_UPDATE_PROPAGATION=5" \
-e "KONG_LICENSE_DATA=$KONG_LICENSE_DATA" \
kong-ee:latest \
kong migrations up

docker run -d --name kong-ee \
-e "KONG_LOG_LEVEL=notice" \
-e "KONG_DATABASE=cassandra" \
-e "KONG_CASSANDRA_CONTACT_POINTS=168.61.55.8,52.162.212.69" \
-e "KONG_CASSANDRA_KEYSPACE=master_kong" \
-e "KONG_CASSANDRA_CONSISTENCY=ONE" \
-e "KONG_CASSANDRA_LB_POLICY=DCAwareRoundRobin" \
-e "KONG_CASSANDRA_LOCAL_DATACENTER=EUS" \
-e "KONG_CASSANDRA_REPL_STRATEGY=NetworkTopologyStrategy" \
-e "KONG_CASSANDRA_DATA_CENTERS=EUS:1,NCUS:1" \
-e "KONG_DB_UPDATE_PROPAGATION=5" \
-e "KONG_PROXY_ACCESS_LOG=/dev/stdout" \
-e "KONG_ADMIN_ACCESS_LOG=/dev/stdout" \
-e "KONG_PROXY_ERROR_LOG=/dev/stderr" \
-e "KONG_ADMIN_ERROR_LOG=/dev/stderr" \
-e "KONG_VITALS=on" \
-e "KONG_ADMIN_LISTEN=0.0.0.0:8001" \
-e "KONG_LICENSE_DATA=$KONG_LICENSE_DATA" \
-e "KONG_ADMIN_GUI_AUTH=ldap-auth-advanced" \
-e "KONG_ADMIN_GUI_AUTH_CONF=$KONG_ADMIN_GUI_AUTH_CONF" \
-e "KONG_ENFORCE_RBAC=on" \
-p 8000:8000 \
-p 8443:8443 \
-p 8001:8001 \
-p 8444:8444 \
-p 8002:8002 \
-p 8445:8445 \
-p 8003:8003 \
-p 8004:8004 \
kong-ee:latest





export KONG_LICENSE_DATA='{"license":{"signature":"24dd81953adad3a69f067c1ae0f2f8cc34134d15c5ab7f43a0136261d63f6293873ac1ea0885615d76f6fb745ab50efbdde1fb815ad380d1ccbc7bdf5
aa8ce9e","payload":{"customer":"Mastercard","license_creation_date":"2018-09-11","product_subscription":"Kong Enterprise Edition","admin_seats":"5","support_plan":"None","l
icense_expiration_date":"2018-10-11","license_key":"00Q4100000wJ4diEAC_a1V41000006MdgFEAS"},"version":1}}'





# Setup Kong

Execute Below commands to setup Kong with DNS resolver in docker containers:

```

docker run -d --rm --name kong-ee-database -p 5432:5432 -e "POSTGRES_USER=kong" -e "POSTGRES_DB=kong" postgres:9.5
cat > license.json < EOF
{"license":{"signature":"24dd81953adad3a69f067c1ae0f2f8cc34134d15c5ab7f43a0136261d63f6293873ac1ea0885615d76f6fb745ab50efbdde1fb815ad380d1ccbc7bdf5aa8ce9e","payload":{"customer":"Private","license_creation_date":"2018-09-11","product_subscription":"Kong Enterprise Edition","admin_seats":"5","support_plan":"None","license_expiration_date":"2018-10-11","license_key":"00Q4100000wJ4diEAC_a1V41000006MdgFEAS"},"version":1}}
EOF
export KONG_LICENSE_DATA=`cat license.json`

docker run --rm --link kong-ee-database:kong-ee-database   -e "KONG_DATABASE=postgres" -e "KONG_PG_HOST=kong-ee-database"   -e "KONG_CASSANDRA_CONTACT_POINTS=kong-ee-database"   -e "KONG_LICENSE_DATA=$KONG_LICENSE_DATA"   kong-ee kong migrations up

docker run -d --rm --name kong-ee --link kong-ee-database:kong-ee-database   -e "KONG_DATABASE=postgres"   -e "KONG_PG_HOST=kong-ee-database"   -e "KONG_CASSANDRA_CONTACT_POINTS=kong-ee-database"   -e "KONG_PROXY_ACCESS_LOG=/dev/stdout"   -e "KONG_ADMIN_ACCESS_LOG=/dev/stdout"   -e "KONG_PROXY_ERROR_LOG=/dev/stderr"   -e "KONG_ADMIN_ERROR_LOG=/dev/stderr"   -e "KONG_VITALS=on"   -e "KONG_ADMIN_LISTEN=0.0.0.0:8001"   -e "KONG_PORTAL=on"   -e "KONG_PORTAL_GUI_URI=localhost:8003" -e "KONG_DNS_RESOLVER=172.17.0.2:8600" -e "KONG_LICENSE_DATA=$KONG_LICENSE_DATA"   -p 8000:8000   -p 8443:8443   -p 8001:8001   -p 8444:8444   -p 8002:8002   -p 8445:8445   -p 8003:8003   -p 8004:8004   kong-ee



docker run -d --name kong-ee \
-e "KONG_DATABASE=cassandra" \
-e "KONG_CASSANDRA_CONTACT_POINTS=168.61.55.8,52.162.212.69" \
-e "KONG_CASSANDRA_KEYSPACE=raj_kong" \
-e "KONG_DNS_RESOLVER=10.0.2.9:8600"  \
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
-e "KONG_ADMIN_GUI_AUTH=ldap-auth-advanced" \
-e "KONG_ENFORCE_RBAC=off" \
-e "KONG_ADMIN_GUI_AUTH_CONF={"anonymous":"","attribute":"uid","base_dn":"ou=scientists,dc=example,dc=com","cache_ttl": 2,"header_type":"Basic","keepalive":60000,"ldap_host":"ldap.forumsys.com","ldap_port":389,"start_tls":false,"timeout":10000,"verify_ldap_host":true}" \
-e "KONG_LICENSE_DATA=$KONG_LICENSE_DATA" \
-p 8000:8000 \
-p 8443:8443 \
-p 8001:8001 \
-p 8444:8444 \
-p 8002:8002 \
-p 8445:8445 \
-p 8003:8003 \
-p 8004:8004 \
kong-ee:latest
```
Not sure why but below working:
```
docker run -d --name kong-ee \
-e "KONG_DATABASE=cassandra" \
-e "KONG_CASSANDRA_CONTACT_POINTS=168.61.55.8,52.162.212.69" \
-e "KONG_CASSANDRA_KEYSPACE=raj_kong" \
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
-e "KONG_LOG_LEVEL=debug" \
-e "KONG_ADMIN_GUI_AUTH=ldap-auth-advanced" -e "KONG_ENFORCE_RBAC=off" \
-e "KONG_ADMIN_GUI_AUTH_CONF=$KONG_ADMIN_GUI_AUTH_CONF" \
-e "KONG_DNS_RESOLVER=10.0.2.9:8600"  \
-p 8000:8000 \
-p 8443:8443 \
-p 8001:8001 \
-p 8444:8444 \
-p 8002:8002 \
-p 8445:8445 \
-p 8003:8003 \
-p 8004:8004 \
kong-ee:latest
```

docker login -u mastercard_eval_rajesh-evuri@kong -p 2cdc1266a3b552151eb98dd584ea434a5207b407 kong-docker-kong-enterprise-edition-docker.bintray.io
docker pull kong-docker-kong-enterprise-edition-docker.bintray.io/kong-enterprise-edition
docker tag kong-docker-kong-enterprise-edition-docker.bintray.io/kong-enterprise-edition kong-ee:latest
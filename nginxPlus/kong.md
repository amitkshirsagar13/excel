# Setup Kong

Execute Below commands to setup Kong with DNS resolver in docker containers:

```

docker run -d --rm --name kong-ee-database -p 5432:5432 -e "POSTGRES_USER=kong" -e "POSTGRES_DB=kong" postgres:9.5
cat > license.json < EOF
{"license":{"signature":"24dd81953adad3a69f067c1ae0f2f8cc34134d15c5ab7f43a0136261d63f6293873ac1ea0885615d76f6fb745ab50efbdde1fb815ad380d1ccbc7bdf5aa8ce9e","payload":{"customer":"Mastercard","license_creation_date":"2018-09-11","product_subscription":"Kong Enterprise Edition","admin_seats":"5","support_plan":"None","license_expiration_date":"2018-10-11","license_key":"00Q4100000wJ4diEAC_a1V41000006MdgFEAS"},"version":1}}
EOF
export KONG_LICENSE_DATA=`cat license.json`

docker run --rm --link kong-ee-database:kong-ee-database   -e "KONG_DATABASE=postgres" -e "KONG_PG_HOST=kong-ee-database"   -e "KONG_CASSANDRA_CONTACT_POINTS=kong-ee-database"   -e "KONG_LICENSE_DATA=$KONG_LICENSE_DATA"   kong-ee kong migrations up


docker run -d --rm --name kong-ee --link kong-ee-database:kong-ee-database   -e "KONG_DATABASE=postgres"   -e "KONG_PG_HOST=kong-ee-database"   -e "KONG_CASSANDRA_CONTACT_POINTS=kong-ee-database"   -e "KONG_PROXY_ACCESS_LOG=/dev/stdout"   -e "KONG_ADMIN_ACCESS_LOG=/dev/stdout"   -e "KONG_PROXY_ERROR_LOG=/dev/stderr"   -e "KONG_ADMIN_ERROR_LOG=/dev/stderr"   -e "KONG_VITALS=on"   -e "KONG_ADMIN_LISTEN=0.0.0.0:8001"   -e "KONG_PORTAL=on"   -e "KONG_PORTAL_GUI_URI=localhost:8003" -e "KONG_DNS_RESOLVER=10.0.2.9:8600"  -e "KONG_LICENSE_DATA=$KONG_LICENSE_DATA"   -p 8000:8000   -p 8443:8443   -p 8001:8001   -p 8444:8444   -p 8002:8002   -p 8445:8445   -p 8003:8003   -p 8004:8004   kong-ee


```
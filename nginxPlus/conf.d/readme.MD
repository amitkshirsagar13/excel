## Create self signed certs for https site for nginx
Execute below command in command prompt to generate self signed certificates:

```
openssl req -config openssl.cnf -x509 -nodes -days 365 -newkey rsa:2048 -keyout /etc/nginx/conf.d/sample.key -out /etc/nginx/conf.d/sample.crt -subj "/C=US/ST=TX/L=TX/O=SampleNGINX/CN=sample.nginx.io"
```

## Create api_secret for validating the JWT tokens:

```
cat <<EOF > api_secret.jwk
{"keys":
    [{
        "k":"ZmFudGFzdGljand0",
        "kty":"oct",
        "kid":"0001"
    }]
}
EOF

```

## Create JWT Client Tokens for testing:

This can be mapped in nginx configuration in map section
map $jwt_claim_sub $jwt_status {
    "quotes" "jwt_quotes";
    "test"   "jwt_test";
    default  "";
}


### Quotes:
```
./token.sh quotes
```

### Test:
```
./token.sh test
```
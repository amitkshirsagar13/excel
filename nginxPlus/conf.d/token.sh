#!/bin/sh
CLAIM=`echo "{\"name\":\"Quotation System\",\"sub\":\"$1\",\"exp\":\"1577836800\",\"iss\":\"My API Gateway\"}"`

HEADER=`echo -n '{"typ":"JWT","alg":"HS256","kid":"0001"}' | base64 | tr '+\/' '-_' | tr -d '='`

PAYLOAD=`echo -n $CLAIM | base64 | tr '+\/' '-_' | tr -d '='`
PAYLOAD=`echo -n $PAYLOAD`
PAYLOAD=`echo ${PAYLOAD//[[:blank:]]/}`
echo $PAYLOAD
HEADER_PAYLOAD=$HEADER.$PAYLOAD

SIGN=`echo -n $HEADER_PAYLOAD | openssl dgst -binary -sha256 -hmac fantasticjwt | base64 | tr '+\/' '-_' | tr -d '='`

echo $HEADER_PAYLOAD.$SIGN
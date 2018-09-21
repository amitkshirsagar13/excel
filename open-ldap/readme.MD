## Open-LDAP Server Setup:

```
docker run -d --rm --name test-openldap -p 8993:389 -e SLAPD_PASSWORD=password  -e SLAPD_CONFIG_PASSWORD=password \
 -e SLAPD_DOMAIN=ldap.k8scluster.io dinkel/openldap
```




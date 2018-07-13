# Overview

## Pre-requisite
* Saas and uaa on-boarding should be done.
  * csc-ems-t1: https://csc-ems-t1.authentication.sap.hana.ondemand.com/oauth/token
  * csc-ems-t2: https://csc-ems-t2.authentication.sap.hana.ondemand.com/oauth/token

![tenant](/picture/tenant.png "Optional title") 

```
grant_type:client_credentials
response_type:token
```

* Get JWToken and Format JWToken via :https://jwt.io/ </br>
Get zid information such as csc-ems-t1/csc-ems-t2

Example JWToken

```
{
  "jti": "121082b643434c72913415785213062b",
  "ext_attr": {
    "enhancer": "XSUAA"
  },
  "sub": "sb-cscems-perf!t50",
  "authorities": [
    "uaa.resource"
  ],
  "scope": [
    "uaa.resource"
  ],
  "client_id": "sb-cscems-perf!t50",
  "cid": "sb-cscems-perf!t50",
  "azp": "sb-cscems-perf!t50",
  "grant_type": "client_credentials",
  "rev_sig": "8b28625d",
  "iat": 1521621320,
  "exp": 1521664520,
  "iss": "http://csc-ems-t1.localhost:8080/uaa/oauth/token",
  "zid": "csc-ems-t1",
  "aud": [
    "uaa",
    "sb-cscems-perf!t50"
  ]
}
```


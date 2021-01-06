# Java JWT Examples

This code uses the FusionAuth JWT library: https://github.com/FusionAuth/fusionauth-jwt and runs through a couple of different scenarios.

* building a JWT with the 'hmac' algorithm
* building a JWT with the 'rsa' algorithm
* building a JWT with the 'hmac' algorithm but verifying additional claims
* decoding an invalid JWT with the 'hmac' algorithm

## To install

You'll need maven.

`mvn compile`

`mvn exec:java -Dexec.mainClass="io.fusionauth.example.jwt.Hmac"`

## Environment

Tested on java 15.0.1

JWTs originally built by [FusionAuth](http://fusionauth.io).

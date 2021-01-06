package io.fusionauth.example.jwt;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import io.fusionauth.jwt.InvalidJWTSignatureException;
import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;

public class HmacWithIncorrectKey {

    public static void main(String[] args) {
    	
    	// User API
    	
        // Build an HMAC signer using a SHA-256 hash
        Signer signer = HMACSigner.newSHA256Signer("too many secrets");

        // Build a new JWT with an issuer(iss), issued at(iat), subject(sub) and expiration(exp)
        JWT jwt = new JWT().setIssuer("fusionauth.io")
                   .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                   .setAudience("238d4793-70de-4183-9707-48ed8ecd19d9")
                   .setSubject("19016b73-3ffa-4b26-80d8-aa9287738677")
                   .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(5));
                       
        jwt.getOtherClaims().put("name", "Dan Moore");
        jwt.getOtherClaims().put("roles", new String[]{"admin"});
        
        // Sign and encode the JWT to a JSON string representation
        String encodedJWT = JWT.getEncoder().encode(jwt, signer);
        
        System.out.println(encodedJWT);
        
        // Todo API
        Verifier verifier = HMACVerifier.newVerifier("not so many secrets");
        try {
        	JWT decoded = JWT.getDecoder().decode(encodedJWT, verifier);
            System.out.println(decoded);
        } catch (InvalidJWTSignatureException e) {
        	System.out.println("invalid signature!");
        }
    }

}

package io.fusionauth.example.jwt;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import io.fusionauth.jwt.InvalidJWTException;
import io.fusionauth.jwt.InvalidJWTSignatureException;
import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;

public class HmacCheckingClaims {

    public static void main(String[] args) {
    	
    	// User API
    	
        // Build an HMAC signer using a SHA-256 hash
        Signer signer = HMACSigner.newSHA256Signer("not enough many secrets");

        // Build a new JWT with an issuer(iss), issued at(iat), subject(sub) and expiration(exp)
        JWT jwt = new JWT().setIssuer("fusionauth.io")
                   .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                   .setAudience("238d4793-70de-4183-9707-48ed8ecd19d9")
                   .setSubject("19016b73-3ffa-4b26-80d8-aa9287738677")
                   .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(5));
                       
        jwt.getOtherClaims().put("name", "Dan Moore");
        jwt.getOtherClaims().put("roles", new String[]{"admin","superadmin"});
        jwt.getOtherClaims().put("premiumUser", Boolean.valueOf(true));
        
        // Sign and encode the JWT to a JSON string representation
        String encodedJWT = JWT.getEncoder().encode(jwt, signer);
        
        System.out.println(encodedJWT);
        
        // Todo API
        Verifier verifier = HMACVerifier.newVerifier("not enough many secrets");
          
        String expectedIssuer = "fusionauth.io";
        String expectedAudience = "238d4793-70de-4183-9707-48ed8ecd19d9";
        
        try {
            JWT decoded = JWT.getDecoder().decode(encodedJWT, verifier);
            if (! ( expectedIssuer.equals(decoded.issuer) && expectedAudience.equals(decoded.audience))) {
            	throw new InvalidJWTException("Incorrect claims");
            }
	    // continue processing
            System.out.println(decoded);
        } catch (InvalidJWTSignatureException e) {
            System.out.println("invalid signature!");
        } catch (InvalidJWTException e) {
            System.out.println("invalid claims!");
        }
        

    }

}

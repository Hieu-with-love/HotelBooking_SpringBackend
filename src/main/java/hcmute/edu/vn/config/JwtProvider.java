package hcmute.edu.vn.config;

import hcmute.edu.vn.utils.JwtUtils;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtProvider {
    public String generateToken(Authentication auth){
        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(auth.getName())
                .setIssuer("devzeus.com")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JwtUtils.EXPIRATION_TIME))
                .claim("role", auth.getAuthorities());

        // When we sign the toke, we use algorithm HS256 (Header: Algorithm: HS512 + Payload + Signature)
        return jwtBuilder.signWith(JwtUtils.decodeKey, SignatureAlgorithm.HS512).compact();
    }

    public String getEmailFromJwtToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(JwtUtils.decodeKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

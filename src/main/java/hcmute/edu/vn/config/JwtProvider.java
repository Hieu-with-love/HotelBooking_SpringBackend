package hcmute.edu.vn.config;

import hcmute.edu.vn.utils.JwtUtils;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtProvider {

    public String generateToken(String email, String role){
        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(email)
                .setIssuer("devzeus.com")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JwtUtils.EXPIRATION_TIME))
                .claim("role", role);

        // When we sign the toke, we use algorithm HS256 (Header: Algorithm: HS512 + Payload + Signature)
        return jwtBuilder.signWith(JwtUtils.getDecodeKey(), SignatureAlgorithm.HS512).compact();
    }

    public String getEmailFromJwtToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(JwtUtils.getDecodeKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

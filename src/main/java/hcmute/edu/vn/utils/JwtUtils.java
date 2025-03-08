package hcmute.edu.vn.utils;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;

public class JwtUtils {
    @Value("${jwt.secretKey}")
    private static String SECRET_KEY;

    public static final long EXPIRATION_TIME = 864_000_000; // 10 days

    public static final SecretKey decodeKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
}

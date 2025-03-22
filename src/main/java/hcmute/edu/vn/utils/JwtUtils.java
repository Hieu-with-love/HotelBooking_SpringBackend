package hcmute.edu.vn.utils;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtUtils {
    // Get secret key
    @Value("${jwt.secretKey}")
    private String secretKey;
    private static String SECRET_KEY;
    @PostConstruct
    public void init() {
        SECRET_KEY = this.secretKey; // Gán vào biến static sau khi bean được khởi tạo
    }
    public static SecretKey getDecodeKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
}

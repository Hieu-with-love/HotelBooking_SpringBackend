package hcmute.edu.vn.config;

import hcmute.edu.vn.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JwtAuthenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain filterChain) throws ServletException, IOException {
        // With login and register, these requests don't have token, so we need to bypass them
        if (isByPassToken(req)){
            filterChain.doFilter(req, res);
            return;
        }
        // At here, we need to get token from request header and check it
        String token = req.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")){
           try{
               // get actual jwt from token
               String jwt = token.substring(7);
               // decode jwt. We get claims
               Claims claims = Jwts.parserBuilder()
                       .setSigningKey(JwtUtils.getDecodeKey())
                       .build()
                       .parseClaimsJws(jwt)
                       .getBody();

               // get email and authorities from claims
               String email = String.valueOf(claims.getSubject());
               String role = String.valueOf(claims.get("role"));

               // save email and authorities to security context
               List<? extends GrantedAuthority> listAuth = AuthorityUtils.commaSeparatedStringToAuthorityList(role);
               Authentication auth = new UsernamePasswordAuthenticationToken(email, null, listAuth);
               SecurityContextHolder.getContext().setAuthentication(auth);
           }catch (Exception ex){
               throw new RuntimeException("Invalid token...");
           }
        }
        // validate token. continue to next filter
        filterChain.doFilter(req, res);
    }

    private boolean isByPassToken(HttpServletRequest req){
        List<String> byPassUrls = List.of("/api/auth/login", "/api/auth/signup");
        String path = req.getRequestURI();

        return byPassUrls.contains(path);
    }
}

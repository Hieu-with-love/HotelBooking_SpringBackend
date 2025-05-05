    package hcmute.edu.vn.config;

    import jakarta.servlet.http.HttpServletRequest;
    import org.apache.tomcat.util.file.ConfigurationSource;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
    import org.springframework.web.cors.CorsConfiguration;
    import org.springframework.web.cors.CorsConfigurationSource;

    import java.util.Arrays;

    @EnableWebSecurity
    @Configuration
    public class SecurityConfig {

        @Value("${frontend.url}")
        private String frontendUrl;

        private final String[] allowedResources = {
                "/api/auth/login",
                "/api/auth/signup",
                "/api/customer/hotels",
                "/api/customer/hotels/{hotelId}",
                "/api/customer/rooms/{roomId}",
                "/api/customer/rooms",
        };

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.csrf(AbstractHttpConfigurer::disable)
                    .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                    .sessionManagement(manager ->
                            manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(new JwtAuthenFilter(), BasicAuthenticationFilter.class)
                    .authorizeHttpRequests(req -> req
                            .requestMatchers("/api/auth/signup").permitAll()
                            .requestMatchers("/api/auth/login").permitAll()
                            .requestMatchers("/api/auth/verify-account").permitAll()
                            .requestMatchers(allowedResources).permitAll()
                            .requestMatchers("/api/customer/**").hasAnyRole("CUSTOMER", "LOYAL_CUSTOMER", "PARTNER")
                            .requestMatchers("/api/partner/**").hasRole("PARTNER")
                            .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                    .anyRequest().authenticated()
                    );

            return http.build();
        }

        // configure to connect with frontend
        private CorsConfigurationSource corsConfigurationSource(){
            return new CorsConfigurationSource() {
                @Override
                public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                    CorsConfiguration cfg = new CorsConfiguration();
                    cfg.addAllowedOrigin("https://zotels-booking.id.vn");
                    cfg.addAllowedHeader("*");
                    cfg.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                    cfg.setAllowCredentials(true);
                    cfg.setMaxAge(3600L);
                    return cfg;
                }
            };
        }

        @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }
    }

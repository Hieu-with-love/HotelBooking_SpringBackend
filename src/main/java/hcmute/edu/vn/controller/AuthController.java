package hcmute.edu.vn.controller;

import hcmute.edu.vn.config.JwtProvider;
import hcmute.edu.vn.dto.request.LoginRequest;
import hcmute.edu.vn.dto.request.SignupRequest;
import hcmute.edu.vn.dto.response.AuthResponse;
import hcmute.edu.vn.model.User;
import hcmute.edu.vn.service.EmailService;
import hcmute.edu.vn.service.UserService;
import hcmute.edu.vn.service.impl.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailService customUserDetailService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest req){
        try{
            User user = userService.signup(req);

            Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            String jwt = jwtProvider.generateToken(auth);

            AuthResponse authResponse = new AuthResponse();
            authResponse.setJwt(jwt);
            authResponse.setMessage("User registered successfully");

            return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                AuthResponse.builder().message("Error: " + e.getMessage()).build()
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req){
        try{
            String username = req.getEmail();
            String password = req.getPassword();
            Authentication auth = validate(username, password);

            SecurityContextHolder.getContext().setAuthentication(auth);

            String jwt = jwtProvider.generateToken(auth);
            AuthResponse authResponse = AuthResponse.builder()
                .jwt(jwt)
                .message("User logged in successfully")
                .build();
            return ResponseEntity.ok(authResponse);

        }catch (Exception ex){
            return ResponseEntity.badRequest().body(
                AuthResponse.builder().message("Error: " + ex.getMessage()).build()
            );
        }
    }

    private Authentication validate(String username, String password){
        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
        if (userDetails==null){
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Password not match");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @GetMapping("/verify-account")
    public ResponseEntity<?> sendEmailToVerifyAccount(@RequestParam("token") String token){
        boolean isVerified = emailService.verifyToken(token);
        return isVerified ?
                ResponseEntity.ok("Account verified successfully")
                :
                ResponseEntity.badRequest().body("Invalid verification code");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> sendEmailToResetPassword(){
        return null;
    }

}

package hcmute.edu.vn.controller;

import hcmute.edu.vn.dto.request.LoginRequest;
import hcmute.edu.vn.dto.request.SignupRequest;
import hcmute.edu.vn.dto.response.AuthResponse;
import hcmute.edu.vn.service.AuthService;
import hcmute.edu.vn.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://hotel-booking-zeta-azure.vercel.app")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest req){
        try{
            boolean registered = authService.signup(req);

            AuthResponse authResponse = new AuthResponse();
            authResponse.setJwt("");
            authResponse.setStatus(registered);
            authResponse.setMessage("User registered successfully");

            return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                AuthResponse.builder()
                        .message("Error: " + e.getMessage())
                        .status(false)
                        .build()
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req){
        try{
            String username = req.getEmail();
            String password = req.getPassword();

            String jwt = authService.login(username, password);
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

    @GetMapping("/verify-account")
    public ResponseEntity<?> verifyAccount(@RequestParam("token") String token){
        try {
            String jwt = authService.verifyAccount(token);
            return ResponseEntity.ok(AuthResponse.builder()
                    .message("Account verified successfully")
                            .jwt(jwt)
                            .status(true)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    AuthResponse.builder()
                            .message("Error: " + e.getMessage())
                            .status(false)
                            .build()
            );
        }
    }

    @GetMapping("/reset-password")
    public ResponseEntity<?> sendEmailToResetPassword(){
        try{
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            emailService.sendEmailToResetPassword(email);
            return ResponseEntity.ok("Change password successfully");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Change password failure");
        }
    }

    @GetMapping("/current-user")
    public ResponseEntity<?> getUserByJwt(){
        return ResponseEntity.ok(authService.getCurrentUser());
    }

}

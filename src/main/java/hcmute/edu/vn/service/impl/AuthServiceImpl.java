package hcmute.edu.vn.service.impl;

import hcmute.edu.vn.config.JwtProvider;
import hcmute.edu.vn.dto.request.EmailDetails;
import hcmute.edu.vn.dto.request.SignupRequest;
import hcmute.edu.vn.enums.EROLE;
import hcmute.edu.vn.model.Customer;
import hcmute.edu.vn.model.User;
import hcmute.edu.vn.repository.UserRepository;
import hcmute.edu.vn.service.AuthService;
import hcmute.edu.vn.service.EmailService;
import hcmute.edu.vn.service.UserService;
import hcmute.edu.vn.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final EmailService emailService;
    private final CustomUserDetailService customUserDetailService;

    @Override
    @Transactional
    public boolean signup(SignupRequest req) {
        EROLE role;
        if (req.getRole()==null){
            role = EROLE.CUSTOMER;
        }else {
            role = EROLE.valueOf(req.getRole());
        }

        if (userService.existsByEmail(req.getEmail())) {
            throw new BadCredentialsException("Email already exists");
        }

        if (!req.getPassword().equals(req.getConfirmPassword())) {
            throw new BadCredentialsException("Password not match");
        }

        User createUser = new Customer();
        createUser.setEmail(req.getEmail());
        createUser.setPassword(passwordEncoder.encode(req.getPassword()));
        createUser.setRole(role);
        createUser.setFirstName(req.getFirstName());
        createUser.setLastName(req.getLastName());
        createUser.setVerified(false);
        userRepository.save(createUser);

        // Generate JWT token for verification
        String jwt = jwtProvider.generateToken(createUser.getEmail(), createUser.getRole().name());

        // Send verification email with JWT token
        try {
            EmailDetails emailDetails = new EmailDetails();
            emailDetails.setSubject("Verify your email");
            emailDetails.setRecipient(req.getEmail());
            emailDetails.setMsgBody(EmailUtils.getVerifyAccountEmailBody(
                    createUser.getLastName(),
                    createUser.getEmail(),
                    jwt));

            emailService.sendEmailToVerifyAccount(emailDetails);
        } catch (Exception e) {
            // If email sending fails, we'll keep the user but mark them as unverified
            // This way they can request a new verification email later
            createUser.setVerified(false);
            userRepository.save(createUser);
            throw new RuntimeException("Failed to send verification email. Please try again later.");
        }

        return true;
    }

    @Override
    public String verifyAccount(String token) {
        try {
            // Extract email from JWT token
            String email = jwtProvider.getEmailFromJwtToken(token);
            
            // Find user by email
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Mark user as verified
            user.setVerified(true);
            userRepository.save(user);
            
            return jwtProvider.generateToken(user.getEmail(), user.getRole().name());
        } catch (Exception e) {
            throw new RuntimeException("Invalid or expired verification token");
        }
    }

    @Override
    public String login(String email, String password) {
        Authentication auth = validate(email, password);

        SecurityContextHolder.getContext().setAuthentication(auth);

        return jwtProvider.generateToken(email, auth.getAuthorities().iterator().next().getAuthority());
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

    @Override
    public void logout() {

    }

    @Override
    public String forgotPassword(String email) {
        return "";
    }

    @Override
    public String resetPassword(String email, String oldPassword, String newPassword) {
        return "";
    }
}

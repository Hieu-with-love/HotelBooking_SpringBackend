package hcmute.edu.vn.service.impl;

import hcmute.edu.vn.config.JwtProvider;
import hcmute.edu.vn.converter.UserConverter;
import hcmute.edu.vn.dto.request.EmailDetails;
import hcmute.edu.vn.dto.request.SignupRequest;
import hcmute.edu.vn.dto.response.UserResponse;
import hcmute.edu.vn.enums.EROLE;
import hcmute.edu.vn.model.User;
import hcmute.edu.vn.model.VerificationCode;
import hcmute.edu.vn.repository.UserRepository;
import hcmute.edu.vn.repository.VerificationCodeRepository;
import hcmute.edu.vn.service.EmailService;
import hcmute.edu.vn.service.UserService;
import hcmute.edu.vn.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final EmailService emailService;
    private final CustomUserDetailService customUserDetailService;
    private final UserConverter userConverter;

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhoneNumber(phone);
    }

    @Override
    public boolean validPassword(String password) {
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) hasUppercase = true;
            else if (Character.isLowerCase(ch)) hasLowercase = true;
            else if (Character.isDigit(ch)) hasDigit = true;
            else if (isSpecialCharacter(ch)) hasSpecialChar = true;
        }
        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
    }

    private boolean isSpecialCharacter(char ch) {
        String specialChars = "!@#$%^&*()-_=+[]{}|;:'\",.<>?/";
        return specialChars.indexOf(ch) >= 0;
    }

    @Override
    public UserResponse getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email " + email));

        return userConverter.toResponse(user);
    }

    @Override
    @Transactional
    public boolean signup(SignupRequest req) {
        // TODO register USE CASE
        EROLE role;
        if (req.getRole()==null){
            role = EROLE.CUSTOMER;
        }else {
            role = EROLE.valueOf(req.getRole().toUpperCase());
        }

        if (this.existsByEmail(req.getEmail())) {
            throw new BadCredentialsException("Email này đã được đăng ký. Vùi lòng nhập email khác");
        }

        if (this.existsByPhone(req.getPhone())){
            throw new BadCredentialsException("SĐT này đã được đăng kí. Vui lòng nhập số điện thoại khác");
        }

        if (!this.validPassword(req.getPassword())){
            throw new BadCredentialsException("Mật khẩu không hợp lệ. Vui lòng nhập lại.");
        }

        if (!req.getPassword().equals(req.getConfirmPassword())) {
            throw new BadCredentialsException("Mật khẩu không khớp. Vui lòng nhập lại!");
        }

        // Convert SignupRequest to User entity
        User createUser = new User();
        createUser.setEmail(req.getEmail());
        createUser.setPhoneNumber(req.getPhone());
        createUser.setFirstName(req.getFirstName());
        createUser.setLastName(req.getLastName());
        createUser.setPassword(passwordEncoder.encode(req.getPassword()));
        createUser.setRole(role);
        userRepository.save(createUser);

        // TODO verify email USE CASE
        verifyEmail(createUser, req);

        return true;
    }

    private void verifyEmail(User createUser, SignupRequest req){
        // Generate JWT token for verification
        String jwt = jwtProvider.generateToken(createUser.getEmail(), createUser.getRole().name());

        if (jwt.isEmpty()){
            throw new BadCredentialsException("Invalid jwt");
        }

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
            createUser.setVerified(false);
            userRepository.save(createUser);
            throw new RuntimeException("Failed to send verification email. Please try again later.");
        }
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

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("User not found"));
        if (!user.isVerified()){
            throw new BadCredentialsException("User is not verified");
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

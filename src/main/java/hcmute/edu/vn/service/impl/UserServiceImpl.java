package hcmute.edu.vn.service.impl;

import hcmute.edu.vn.dto.request.EmailDetails;
import hcmute.edu.vn.dto.request.SignupRequest;
import hcmute.edu.vn.enums.EROLE;
import hcmute.edu.vn.model.User;
import hcmute.edu.vn.model.VerificationCode;
import hcmute.edu.vn.repository.UserRepository;
import hcmute.edu.vn.service.EmailService;
import hcmute.edu.vn.service.UserService;
import hcmute.edu.vn.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public User signup(SignupRequest req) {
        EROLE role;
        if (req.getRole()==null){
            role = EROLE.CUSTOMER;
        }else {
            role = EROLE.valueOf(req.getRole());
        }

        if (existsByEmail(req.getEmail())) {
            throw new BadCredentialsException("Email already exists");
        }

        if (!req.getPassword().equals(req.getConfirmPassword())) {
            throw new BadCredentialsException("Password not match");
        }

        User createUser = new User();
        createUser.setEmail(req.getEmail());
        createUser.setPassword(passwordEncoder.encode(req.getPassword()));
        createUser.setRole(role);
        createUser.setFirstName(req.getFirstName());
        createUser.setLastName(req.getLastName());
        createUser.setVerified(false);
        userRepository.save(createUser);

        try{
            // We need to create verify code
            VerificationCode code = VerificationCode.builder()
                    .code(EmailUtils.generateVerificationCode())
                    .user(createUser)
                    .build();

            EmailDetails eDetails = new EmailDetails();
            eDetails.setRecipient(req.getEmail());
            eDetails.setSubject("Verify your account");
            eDetails.setMsgBody(EmailUtils.getVerifyAccountEmailBody(createUser.getLastName(), createUser.getEmail(), code.getCode()));
            emailService.sendEmailToVerifyAccount(eDetails);
        }catch (Exception e){
            userRepository.delete(createUser);
            throw new MailSendException("Cannot send email");
        }

        return createUser;
    }

    @Override
    public String login(String email, String password) {
        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}

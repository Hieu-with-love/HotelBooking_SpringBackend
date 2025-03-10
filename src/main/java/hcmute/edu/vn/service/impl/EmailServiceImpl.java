package hcmute.edu.vn.service.impl;

import hcmute.edu.vn.dto.request.EmailDetails;
import hcmute.edu.vn.model.User;
import hcmute.edu.vn.model.VerificationCode;
import hcmute.edu.vn.repository.UserRepository;
import hcmute.edu.vn.repository.VerificationCodeRepository;
import hcmute.edu.vn.service.EmailService;
import hcmute.edu.vn.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final VerificationCodeRepository verifyCodeRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
    private String sender;

    private final JavaMailSender mailSender;

    @Override
    public void sendEmailToVerifyAccount(EmailDetails details) {
        // Use JavaMailSender to send email.
        // create a instance of SimpleMailMessage
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject(details.getSubject());
        msg.setFrom(sender);
        msg.setTo(details.getRecipient());
        msg.setText(details.getMsgBody());

        // send the email by mailSender
        mailSender.send(msg);
    }

    @Override
    public void sendEmailToResetPassword(String email) {
        try{
            String newPassword = EmailUtils.generateRandomPassword();

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setSubject("Reset password");
            msg.setFrom(sender);
            msg.setTo(email);
            msg.setText(EmailUtils.getResetPasswordBody(email, newPassword));

            mailSender.send(msg);

            // Send email successfully -> update new password
            User existingUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            existingUser.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(existingUser);
        }catch (MailSendException e){
            throw new RuntimeException("Error sending email");
        }
    }

    @Override
    public boolean verifyToken(String code) {
        VerificationCode verificationCode = verifyCodeRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Invalid verification code"));
        // If has verification code -> existing user
        User existingUser = userRepository.findByEmail(verificationCode.getUser().getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setVerified(true);
        userRepository.save(existingUser);
        return true;
    }
}

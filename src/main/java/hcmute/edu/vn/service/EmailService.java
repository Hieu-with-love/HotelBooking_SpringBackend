package hcmute.edu.vn.service;

import hcmute.edu.vn.dto.request.EmailDetails;

public interface EmailService {
    void sendEmailToVerifyAccount(EmailDetails details);
    void sendEmailToResetPassword(String email);
    boolean verifyToken(String code);

}

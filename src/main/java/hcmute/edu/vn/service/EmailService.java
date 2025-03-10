package hcmute.edu.vn.service;

import hcmute.edu.vn.dto.request.EmailDetails;

public interface EmailService {
    void sendEmailToVerifyAccount(EmailDetails details);
    boolean resetPassword(EmailDetails details);
    boolean verifyToken(String code);

}

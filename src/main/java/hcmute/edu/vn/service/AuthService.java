package hcmute.edu.vn.service;

import hcmute.edu.vn.dto.request.SignupRequest;

public interface AuthService {
    boolean signup(SignupRequest request);
    String login(String email, String password);
    String verifyAccount(String token);
    void logout();
    String forgotPassword(String email);
    String resetPassword(String email, String oldPassword, String newPassword);
}

package hcmute.edu.vn.service;

import hcmute.edu.vn.dto.request.SignupRequest;

public interface AuthService {
    String signup(SignupRequest request);
    String login(String email, String password);
    void logout();
    String forgotPassword(String email);
    String resetPassword(String email, String oldPassword, String newPassword);
}

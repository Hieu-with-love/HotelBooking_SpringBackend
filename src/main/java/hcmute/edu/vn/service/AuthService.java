package hcmute.edu.vn.service;

import hcmute.edu.vn.dto.request.SignupRequest;
import hcmute.edu.vn.dto.response.UserResponse;

public interface AuthService {
    UserResponse getCurrentUser();
    boolean signup(SignupRequest request);
    String login(String email, String password);
    String verifyAccount(String token);
    void logout();
    String forgotPassword(String email);
    String resetPassword(String email, String oldPassword, String newPassword);
}

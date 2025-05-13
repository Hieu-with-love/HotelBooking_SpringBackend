package hcmute.edu.vn.service;

import hcmute.edu.vn.dto.request.SignupRequest;
import hcmute.edu.vn.dto.response.UserResponse;
import hcmute.edu.vn.model.User;

public interface UserService {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean validPassword(String password);
    UserResponse getCurrentUser();
    boolean signup(SignupRequest request);
    String login(String email, String password);
    String verifyAccount(String token);
    void logout();
    String forgotPassword(String email);
    String resetPassword(String email, String oldPassword, String newPassword);
}

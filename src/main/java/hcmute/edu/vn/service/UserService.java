package hcmute.edu.vn.service;

import hcmute.edu.vn.dto.request.SignupRequest;
import hcmute.edu.vn.model.User;

public interface UserService {
    User signup(SignupRequest req);
    String login(String email, String password);
    boolean existsByEmail(String email);
}

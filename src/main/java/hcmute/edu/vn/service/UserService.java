package hcmute.edu.vn.service;

import hcmute.edu.vn.dto.request.SignupRequest;
import hcmute.edu.vn.model.User;

public interface UserService {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}

package hcmute.edu.vn.service.impl;

import hcmute.edu.vn.dto.request.SignupRequest;
import hcmute.edu.vn.enums.EROLE;
import hcmute.edu.vn.model.User;
import hcmute.edu.vn.repository.UserRepository;
import hcmute.edu.vn.service.UserService;
import lombok.RequiredArgsConstructor;
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

        userRepository.save(createUser);

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

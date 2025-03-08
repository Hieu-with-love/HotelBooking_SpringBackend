package hcmute.edu.vn.dto.request;

import lombok.Data;

@Data
public class SignupRequest {
    private String email;
    private String password;
    private String confirmPassword;
}

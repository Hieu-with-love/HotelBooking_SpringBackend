package hcmute.edu.vn.dto.response;

import hcmute.edu.vn.enums.EROLE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dayOfBirth;
    private String gender;
    private String avatar;
    private EROLE role;
    private boolean isVerified;
    private boolean status;
} 
package hcmute.edu.vn.model;

import hcmute.edu.vn.enums.ENATION;
import hcmute.edu.vn.enums.EROLE;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String email;
    protected String password;
    @Column(name = "full_name")
    protected String confirmPassword;
    @Column(name = "create_at")
    protected LocalDate createdAt;
    @Column(name = "update_at")
    protected LocalDate updatedAt;
    @Column(name = "phone_number")
    protected String phoneNumber;
    @Column(name = "first_name")
    protected String firstName;
    @Column(name = "last_name")
    protected String lastName;
    @Column(name = "day_of_birth")
    protected LocalDate dayOfBirth;
    protected String gender;
    protected String status;

    protected EROLE role;

    protected ENATION nation;
    protected boolean isVerified;

    @OneToOne(mappedBy = "user")
    protected VerificationCode verificationCode;
}

package hcmute.edu.vn.model;

import hcmute.edu.vn.enums.ENATION;
import hcmute.edu.vn.enums.EROLE;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

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
    private VerificationCode verificationCode;
}

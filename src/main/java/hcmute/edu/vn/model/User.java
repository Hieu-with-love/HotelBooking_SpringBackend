package hcmute.edu.vn.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String email;

    protected String password;

    @Column(name = "phone_number")
    protected String phoneNumber;

    @Column(name = "first_name")
    protected String firstName;

    @Column(name = "last_name")
    protected String lastName;

    @Column(name = "day_of_birth")
    protected LocalDate dayOfBirth;

    protected String gender;

    protected String avatar;

    protected EROLE role;

    protected ENATION nation;

    protected boolean isVerified;

    protected boolean status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    protected List<Booking> bookings;

    @OneToOne(mappedBy = "user")
    protected VerificationCode verificationCode;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "customer_voucher",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "voucher_id")
    )
    protected List<Voucher> vouchers;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}

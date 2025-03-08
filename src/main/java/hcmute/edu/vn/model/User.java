package hcmute.edu.vn.model;

import hcmute.edu.vn.enums.ENATION;
import hcmute.edu.vn.enums.EROLE;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends Account {
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private LocalDate dayOfBirth;
    private EROLE gender;
    private ENATION nation;
}

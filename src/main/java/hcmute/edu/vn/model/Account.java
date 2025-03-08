package hcmute.edu.vn.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Account {
    protected String email;
    protected String password;
    @Column(name = "full_name")
    protected String confirmPassword;
    @Column(name = "create_at")
    protected LocalDate createdAt;
    @Column(name = "update_at")
    protected LocalDate updatedAt;
}

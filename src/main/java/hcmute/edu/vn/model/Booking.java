package hcmute.edu.vn.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Room> rooms = new ArrayList<>();

    private String checkIn;
    private String checkOut;

    @ManyToOne
    private Payment paymentType;

    private BigDecimal totalPrice;

    private String specialRequest;
}

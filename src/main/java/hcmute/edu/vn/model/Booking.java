package hcmute.edu.vn.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Identify this propoly booking
    private String bookingCode;

    private String checkIn;

    private String checkOut;

    private BigDecimal totalPrice;

    private String specialRequest;

    @ManyToOne
    private Payment paymentType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<BookingDetail> bookingDetails;

    private String status;

    public Booking(Long id,
                   String bookingCode,
                   String checkIn,
                   String checkOut,
                   BigDecimal totalPrice,
                   String specialRequest,
                   Payment paymentType,
                   User user,
                   List<BookingDetail> bookingDetails,
                   String status) {
        this.id = id;
        this.bookingCode = bookingCode;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPrice = totalPrice;
        this.specialRequest = specialRequest;
        this.paymentType = paymentType;
        this.user = user;
        this.bookingDetails = bookingDetails;
        this.status = status;
    }

    private Booking(Builder builder) {
        this.id = builder.id;
        this.bookingCode = builder.bookingCode;
        this.checkIn = builder.checkIn;
        this.checkOut = builder.checkOut;
        this.totalPrice = builder.totalPrice;
        this.specialRequest = builder.specialRequest;
        this.paymentType = builder.paymentType;
        this.user = builder.user;
        this.bookingDetails = builder.bookingDetails;
        this.status = builder.status;
    }

    public static class Builder {

        private Long id;
        private String bookingCode;
        private String checkIn;
        private String checkOut;
        private BigDecimal totalPrice;
        private String specialRequest;
        private Payment paymentType;
        private User user;
        private List<BookingDetail> bookingDetails;
        private String status;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder bookingCode(String bookingCode) {
            this.bookingCode = bookingCode;
            return this;
        }

        public Builder checkIn(String checkIn) {
            this.checkIn = checkIn;
            return this;
        }

        public Builder checkOut(String checkOut) {
            this.checkOut = checkOut;
            return this;
        }

        public Builder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Builder specialRequest(String specialRequest) {
            this.specialRequest = specialRequest;
            return this;
        }

        public Builder paymentType(Payment paymentType) {
            this.paymentType = paymentType;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder bookingDetails(List<BookingDetail> bookingDetails) {
            this.bookingDetails = bookingDetails;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", checkIn='" + checkIn + '\'' +
                ", checkOut='" + checkOut + '\'' +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                '}';
    }


}

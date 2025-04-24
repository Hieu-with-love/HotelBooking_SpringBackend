package hcmute.edu.vn.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BookingRequest {
    private String checkInDate;
    private String checkOutDate;
    private int numberOfAdults;
    private int numberOfChildren;
    private String voucherCode;
    private String specialRequests;
    private BigDecimal price;
    private List<RoomRequest> rooms;
    private PaymentRequest paymentMethod;
    private String status;
}

package hcmute.edu.vn.dto.response;

import hcmute.edu.vn.dto.request.RoomRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BookingResponse {
    private Long bookingId;
    private String selectedPaymentMethod;
    private BigDecimal totalPrice;
    private List<RoomResponse> selectedRooms;
    private String checkInDate;
    private String checkOutDate;
}

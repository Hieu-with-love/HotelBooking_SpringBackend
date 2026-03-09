package hcmute.edu.vn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO để tạo phòng sử dụng Factory Pattern
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomFactoryRequest {
    
    /**
     * Loại phòng: STANDARD, DELUXE, SUITE
     */
    private String roomType;
    
    /**
     * Tên phòng
     */
    private String roomName;
    
    /**
     * ID khách sạn
     */
    private Long hotelId;
    
    /**
     * Số lượng phòng cần tạo
     */
    private int quantity = 1;
}

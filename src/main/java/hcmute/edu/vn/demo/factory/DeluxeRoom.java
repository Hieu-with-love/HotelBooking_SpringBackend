package hcmute.edu.vn.demo.factory;

import hcmute.edu.vn.enums.EROOMSTATUS;
import hcmute.edu.vn.enums.EROOMTYPE;
import hcmute.edu.vn.enums.ESERVICE;
import hcmute.edu.vn.model.Room;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Phòng Deluxe - Phòng cao cấp
 * Deluxe Room - Premium room type
 * Concrete Product Builder trong Factory Pattern
 * 
 * !! KHÔNG extends Room vì JPA chỉ nhận diện Room entity !!
 * Thay vào đó, class này tạo và trả về Room entity thuần
 */
public class DeluxeRoom {
    
    private final Room room;
    
    public DeluxeRoom() {
        // Tạo Room entity với các thông tin Deluxe
        this.room = new Room();
        room.setName("Phòng Deluxe");
        room.setType(EROOMTYPE.KING);
        room.setPrice(new BigDecimal("1000000")); // 1M VND/night
        room.setNumberOfAdults(3);
        room.setNumberOfChildren(1);
        room.setNumberOfBeds(1);
        room.setStatus(EROOMSTATUS.AVAILABLE);
        room.setDescription("Phòng cao cấp với đầy đủ tiện nghi hiện đại và không gian rộng rãi");
        room.setServices(Arrays.asList(
            ESERVICE.WIFI,
            ESERVICE.TV,
            ESERVICE.AIR_CONDITIONING,
            ESERVICE.MINIBAR,
            ESERVICE.BATHTUB
        ));
    }
    
    /**
     * Lấy Room entity đã được khởi tạo
     */
    public Room getRoom() {
        return room;
    }
    
    /**
     * Hiển thị thông tin phòng
     */
    public void displayInfo() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║         PHÒNG DELUXE 🌟                ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ Giá cơ bản: " + room.getPrice() + " VND/đêm   ║");
        System.out.println("║ Sức chứa: " + room.getNumberOfAdults() + " người                    ║");
        System.out.println("║ Tiện nghi:                             ║");
        System.out.println("║   - Wi-Fi tốc độ cao                   ║");
        System.out.println("║   - TV Smart 50 inch                   ║");
        System.out.println("║   - Điều hòa 2 chiều                   ║");
        System.out.println("║   - Phòng tắm cao cấp với bồn tắm      ║");
        System.out.println("║   - Mini bar                           ║");
        System.out.println("║   - Ban công riêng                     ║");
        System.out.println("║   - Két an toàn                        ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
    
    /**
     * Tính giá phòng theo số đêm
     */
    public BigDecimal calculatePrice(int nights) {
        // Deluxe: Giảm 5% nếu đặt từ 3 đêm trở lên
        BigDecimal totalPrice = room.getPrice().multiply(new BigDecimal(nights));
        if (nights >= 3) {
            totalPrice = totalPrice.multiply(new BigDecimal("0.95")); // -5%
        }
        return totalPrice;
    }
}

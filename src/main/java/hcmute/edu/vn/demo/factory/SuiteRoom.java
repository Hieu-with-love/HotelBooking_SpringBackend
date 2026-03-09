package hcmute.edu.vn.demo.factory;

import hcmute.edu.vn.enums.EROOMSTATUS;
import hcmute.edu.vn.enums.EROOMTYPE;
import hcmute.edu.vn.enums.ESERVICE;
import hcmute.edu.vn.model.Room;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Phòng Suite - Phòng hạng sang
 * Suite Room - Luxury room type
 * Concrete Product Builder trong Factory Pattern
 * 
 * !! KHÔNG extends Room vì JPA chỉ nhận diện Room entity !!
 * Thay vào đó, class này tạo và trả về Room entity thuần
 */
public class SuiteRoom {
    
    private final Room room;
    
    public SuiteRoom() {
        // Tạo Room entity với các thông tin Suite
        this.room = new Room();
        room.setName("Phòng Suite");
        room.setType(EROOMTYPE.SUITE);
        room.setPrice(new BigDecimal("2500000")); // 2.5M VND/night
        room.setNumberOfAdults(4);
        room.setNumberOfChildren(2);
        room.setNumberOfBeds(2);
        room.setStatus(EROOMSTATUS.AVAILABLE);
        room.setDescription("Phòng hạng sang với không gian rộng rãi, tiện nghi 5 sao và dịch vụ cao cấp");
        room.setServices(Arrays.asList(
            ESERVICE.WIFI,
            ESERVICE.TV,
            ESERVICE.AIR_CONDITIONING,
            ESERVICE.MINIBAR,
            ESERVICE.BATHTUB,
            ESERVICE.BALCONY,
            ESERVICE.SAFE_BOX,
            ESERVICE.ROOM_SERVICE
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
        System.out.println("║         PHÒNG SUITE 👑👑               ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ Giá cơ bản: " + room.getPrice() + " VND/đêm  ║");
        System.out.println("║ Sức chứa: " + room.getNumberOfAdults() + " người                    ║");
        System.out.println("║ Tiện nghi:                             ║");
        System.out.println("║   - Wi-Fi tốc độ cao không giới hạn    ║");
        System.out.println("║   - TV Smart 65 inch                   ║");
        System.out.println("║   - Hệ thống âm thanh cao cấp          ║");
        System.out.println("║   - Điều hòa trung tâm                 ║");
        System.out.println("║   - Phòng tắm sang trọng với jacuzzi   ║");
        System.out.println("║   - Phòng khách riêng biệt             ║");
        System.out.println("║   - Bếp nhỏ                            ║");
        System.out.println("║   - Mini bar cao cấp                   ║");
        System.out.println("║   - Ban công rộng với view đẹp         ║");
        System.out.println("║   - Két an toàn điện tử                ║");
        System.out.println("║   - Dịch vụ phòng 24/7                 ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
    
    /**
     * Tính giá phòng theo số đêm
     */
    public BigDecimal calculatePrice(int nights) {
        // Suite: Giảm 10% nếu đặt từ 5 đêm trở lên, 5% nếu từ 3 đêm
        BigDecimal totalPrice = room.getPrice().multiply(new BigDecimal(nights));
        if (nights >= 5) {
            totalPrice = totalPrice.multiply(new BigDecimal("0.90")); // -10%
        } else if (nights >= 3) {
            totalPrice = totalPrice.multiply(new BigDecimal("0.95")); // -5%
        }
        return totalPrice;
    }
}

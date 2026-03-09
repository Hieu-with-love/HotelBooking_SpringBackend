package hcmute.edu.vn.demo.factory;

import hcmute.edu.vn.enums.EROOMSTATUS;
import hcmute.edu.vn.enums.EROOMTYPE;
import hcmute.edu.vn.enums.ESERVICE;
import hcmute.edu.vn.model.Room;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Phòng Standard - Phòng tiêu chuẩn
 * Standard Room - Basic room type
 * Concrete Product Builder trong Factory Pattern
 * 
 * !! KHÔNG extends Room vì JPA chỉ nhận diện Room entity !!
 * Thay vào đó, class này tạo và trả về Room entity thuần
 */
public class StandardRoom {
    
    private final Room room;
    
    public StandardRoom() {
        // Tạo Room entity với các thông tin Standard
        this.room = new Room();
        room.setName("Phòng Standard");
        room.setType(EROOMTYPE.DOUBLE);
        room.setPrice(new BigDecimal("500000")); // 500k VND/night
        room.setNumberOfAdults(2);
        room.setNumberOfChildren(0);
        room.setNumberOfBeds(1);
        room.setStatus(EROOMSTATUS.AVAILABLE);
        room.setDescription("Phòng tiêu chuẩn với đầy đủ tiện nghi cơ bản");
        room.setServices(Arrays.asList(
            ESERVICE.WIFI,
            ESERVICE.TV,
            ESERVICE.AIR_CONDITIONING
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
        System.out.println("║         PHÒNG STANDARD                 ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ Giá cơ bản: " + room.getPrice() + " VND/đêm    ║");
        System.out.println("║ Sức chứa: " + room.getNumberOfAdults() + " người                    ║");
        System.out.println("║ Tiện nghi:                             ║");
        System.out.println("║   - Wi-Fi miễn phí                     ║");
        System.out.println("║   - TV màn hình phẳng                  ║");
        System.out.println("║   - Điều hòa                           ║");
        System.out.println("║   - Phòng tắm riêng                    ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
    
    /**
     * Tính giá phòng theo số đêm
     */
    public BigDecimal calculatePrice(int nights) {
        // Standard: Không có giảm giá đặc biệt
        return room.getPrice().multiply(new BigDecimal(nights));
    }
}

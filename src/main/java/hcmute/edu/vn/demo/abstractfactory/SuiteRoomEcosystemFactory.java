package hcmute.edu.vn.demo.abstractfactory;

import hcmute.edu.vn.enums.EROOMSTATUS;
import hcmute.edu.vn.enums.EROOMTYPE;
import hcmute.edu.vn.enums.ESERVICE;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Factory tạo hệ sinh thái HOÀN CHỈNH cho phòng Suite
 * Factory creating COMPLETE ecosystem for Suite room
 */
public class SuiteRoomEcosystemFactory implements RoomEcosystemFactory {
    
    @Override
    public RoomBundle createRoomBundle() {
        // Tạo RoomBundle (extends Room)
        RoomBundle bundle = new RoomBundle();
        
        // Set các properties của Room entity
        bundle.setName("Phòng Suite 👑👑");
        bundle.setType(EROOMTYPE.SUITE);
        bundle.setPrice(new BigDecimal("2500000")); // 2.5M VND/night
        bundle.setNumberOfAdults(4);
        bundle.setNumberOfChildren(2);
        bundle.setNumberOfBeds(2);
        bundle.setStatus(EROOMSTATUS.AVAILABLE);
        bundle.setDescription("Phòng hạng sang với không gian rộng rãi, tiện nghi 5 sao và dịch vụ cao cấp");
        bundle.setServices(Arrays.asList(
            ESERVICE.WIFI,
            ESERVICE.TV,
            ESERVICE.AIR_CONDITIONING,
            ESERVICE.MINIBAR,
            ESERVICE.BATHTUB,
            ESERVICE.BALCONY,
            ESERVICE.SAFE_BOX,
            ESERVICE.ROOM_SERVICE
        ));
        
        // Set các policies (Abstract Factory Pattern)
        bundle.setPricingPolicy(createPricingPolicy());
        bundle.setCancellationPolicy(createCancellationPolicy());
        
        return bundle;
    }
    
    @Override
    public PricingPolicy createPricingPolicy() {
        return new SuitePricingPolicy();
    }
    
    @Override
    public CancellationPolicy createCancellationPolicy() {
        return new SuiteCancellationPolicy();
    }
}

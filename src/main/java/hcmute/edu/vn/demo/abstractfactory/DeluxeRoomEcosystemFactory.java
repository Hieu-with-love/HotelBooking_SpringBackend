package hcmute.edu.vn.demo.abstractfactory;

import hcmute.edu.vn.enums.EROOMSTATUS;
import hcmute.edu.vn.enums.EROOMTYPE;
import hcmute.edu.vn.enums.ESERVICE;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Factory tạo hệ sinh thái HOÀN CHỈNH cho phòng Deluxe
 * Factory creating COMPLETE ecosystem for Deluxe room
 */
public class DeluxeRoomEcosystemFactory implements RoomEcosystemFactory {
    
    @Override
    public RoomBundle createRoomBundle() {
        // Tạo RoomBundle (extends Room)
        RoomBundle bundle = new RoomBundle();
        
        // Set các properties của Room entity
        bundle.setName("Phòng Deluxe 🌟");
        bundle.setType(EROOMTYPE.KING);
        bundle.setPrice(new BigDecimal("1000000")); // 1M VND/night
        bundle.setNumberOfAdults(3);
        bundle.setNumberOfChildren(1);
        bundle.setNumberOfBeds(1);
        bundle.setStatus(EROOMSTATUS.AVAILABLE);
        bundle.setDescription("Phòng cao cấp với không gian rộng rãi và tiện nghi hiện đại");
        bundle.setServices(Arrays.asList(
            ESERVICE.WIFI,
            ESERVICE.TV,
            ESERVICE.AIR_CONDITIONING,
            ESERVICE.MINIBAR,
            ESERVICE.BATHTUB
        ));
        
        // Set các policies (Abstract Factory Pattern)
        bundle.setPricingPolicy(createPricingPolicy());
        bundle.setCancellationPolicy(createCancellationPolicy());
        
        return bundle;
    }
    
    @Override
    public PricingPolicy createPricingPolicy() {
        return new DeluxePricingPolicy();
    }
    
    @Override
    public CancellationPolicy createCancellationPolicy() {
        return new DeluxeCancellationPolicy();
    }
}

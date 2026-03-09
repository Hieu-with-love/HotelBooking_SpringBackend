package hcmute.edu.vn.demo.abstractfactory;

import hcmute.edu.vn.enums.EROOMSTATUS;
import hcmute.edu.vn.enums.EROOMTYPE;
import hcmute.edu.vn.enums.ESERVICE;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Factory tạo hệ sinh thái HOÀN CHỈNH cho phòng Standard
 * Factory creating COMPLETE ecosystem for Standard room
 */
public class StandardRoomEcosystemFactory implements RoomEcosystemFactory {
    
    @Override
    public RoomBundle createRoomBundle() {
        // Tạo RoomBundle (extends Room)
        RoomBundle bundle = new RoomBundle();
        
        // Set các properties của Room entity
        bundle.setName("Phòng Standard");
        bundle.setType(EROOMTYPE.DOUBLE);
        bundle.setPrice(new BigDecimal("500000")); // 500k VND/night
        bundle.setNumberOfAdults(2);
        bundle.setNumberOfChildren(0);
        bundle.setNumberOfBeds(1);
        bundle.setStatus(EROOMSTATUS.AVAILABLE);
        bundle.setDescription("Phòng tiêu chuẩn với đầy đủ tiện nghi cơ bản");
        bundle.setServices(Arrays.asList(
            ESERVICE.WIFI,
            ESERVICE.TV,
            ESERVICE.AIR_CONDITIONING
        ));
        
        // Set các policies (Abstract Factory Pattern)
        bundle.setPricingPolicy(createPricingPolicy());
        bundle.setCancellationPolicy(createCancellationPolicy());
        
        return bundle;
    }
    
    @Override
    public PricingPolicy createPricingPolicy() {
        return new StandardPricingPolicy();
    }
    
    @Override
    public CancellationPolicy createCancellationPolicy() {
        return new StandardCancellationPolicy();
    }
}

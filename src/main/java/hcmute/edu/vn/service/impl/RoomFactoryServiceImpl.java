package hcmute.edu.vn.service.impl;

import hcmute.edu.vn.demo.abstractfactory.*;
import hcmute.edu.vn.demo.factory.*;
import hcmute.edu.vn.dto.request.RoomRequest;
import hcmute.edu.vn.dto.response.BatchRoomCreationResponse;
import hcmute.edu.vn.dto.response.RoomFactoryResponse;
import hcmute.edu.vn.enums.EROOMSTATUS;
import hcmute.edu.vn.enums.EROOMTYPE;
import hcmute.edu.vn.enums.ESERVICE;
import hcmute.edu.vn.model.Hotel;
import hcmute.edu.vn.model.Room;
import hcmute.edu.vn.repository.HotelRepository;
import hcmute.edu.vn.repository.RoomRepository;
import hcmute.edu.vn.service.RoomFactoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation của RoomFactoryService
 * Tích hợp Factory Pattern với Database
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RoomFactoryServiceImpl implements RoomFactoryService {
    
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    
    @Override
    @Transactional
    public RoomFactoryResponse createRoomWithFactoryMethod(RoomRequest request) {
        log.info("🏭 [FACTORY METHOD] Creating room of type: {}", request.getRoomType());
        
        // Sử dụng Factory Method Pattern để tạo template
        Room roomTemplate = RoomFactory.createRoom(request.getRoomType());
        
        // Override với data từ request nếu có
        if (request.getName() != null) {
            roomTemplate.setName(request.getName());
        }
        if (request.getDescription() != null) {
            roomTemplate.setDescription(request.getDescription());
        }
        if (request.getPrice() != null) {
            roomTemplate.setPrice(request.getPrice());
        }
        if (request.getNumberOfAdults() > 0) {
            roomTemplate.setNumberOfAdults(request.getNumberOfAdults());
        }
        if (request.getNumberOfChildren() > 0) {
            roomTemplate.setNumberOfChildren(request.getNumberOfChildren());
        }
        if (request.getNumberOfBeds() > 0) {
            roomTemplate.setNumberOfBeds(request.getNumberOfBeds());
        }
        if (request.getServices() != null && !request.getServices().isEmpty()) {
            roomTemplate.setServices(request.getServices());
        }
        
        // Gán hotel nếu có
        if (request.getHotelId() != null) {
            Hotel hotel = hotelRepository.findById(request.getHotelId())
                    .orElseThrow(() -> new RuntimeException("Hotel not found with ID: " + request.getHotelId()));
            roomTemplate.setHotel(hotel);
        }
        
        roomTemplate.setStatus(EROOMSTATUS.AVAILABLE);
        
        // Lưu vào database
        Room savedRoom = roomRepository.save(roomTemplate);

        return RoomFactoryResponse.builder()
                .roomId(savedRoom.getId())
                .roomName(savedRoom.getName())
                .roomType(savedRoom.getType().toString())
                .basePrice(savedRoom.getPrice())
                .capacity(savedRoom.getNumberOfAdults())
                .numberOfBeds(savedRoom.getNumberOfBeds())
                .amenities(convertServicesToAmenities(savedRoom.getServices()))
                .factoryUsed("FACTORY METHOD PATTERN")
                .message("✅ Phòng được tạo bằng Factory Method và lưu vào DB")
                .build();
    }
    
    @Override
    @Transactional
    public RoomFactoryResponse createRoomWithAbstractFactory(RoomRequest request) {
        log.info("🏭 [ABSTRACT FACTORY] Creating room ecosystem of type: {}", request.getRoomType());
        
        // Sử dụng Abstract Factory Pattern để tạo hệ sinh thái hoàn chỉnh
        RoomEcosystemFactory factory = getEcosystemFactory(request.getRoomType());
        RoomBundle bundle = factory.createRoomBundle(); // RoomBundle đã extends Room
        
        // Override với data từ request nếu có
        overrideRoomDataFromRequest(bundle, request);
        
        // Thêm thông tin về policies vào description
        String policyInfo = String.format(
            "\n\n🏷️ Chính sách giá: %s" +
            "\n❌ Chính sách hủy: %s",
            bundle.getPricingPolicy().getDescription(),
            bundle.getCancellationPolicy().getDescription()
        );
        String currentDesc = bundle.getDescription() != null ? bundle.getDescription() : "";
        bundle.setDescription(currentDesc + policyInfo);
        
        // Convert RoomBundle sang Room entity để lưu vào DB
        // RoomBundle không phải @Entity nên cần convert
        Room roomEntity = convertBundleToRoom(bundle);
        
        // Lưu vào database
        Room savedRoom = roomRepository.save(roomEntity);
        
        return RoomFactoryResponse.builder()
                .roomId(savedRoom.getId())
                .roomName(savedRoom.getName())
                .roomType(savedRoom.getType().toString())
                .basePrice(savedRoom.getPrice())
                .capacity(savedRoom.getNumberOfAdults())
                .numberOfBeds(savedRoom.getNumberOfBeds())
                .amenities(convertServicesToAmenities(savedRoom.getServices()))
                .pricingPolicy(bundle.getPricingPolicy().getDescription())
                .cancellationPolicy(bundle.getCancellationPolicy().getDescription())
                .factoryUsed("ABSTRACT FACTORY PATTERN")
                .message("✅ Hệ sinh thái phòng hoàn chỉnh được tạo và lưu vào DB")
                .build();
    }
    
    @Override
    @Transactional
    public BatchRoomCreationResponse createMultipleRooms(RoomRequest request) {
        log.info("🏭 [BATCH] Creating {} rooms of type: {}", request.getQuantity(), request.getRoomType());
        
        List<RoomFactoryResponse> createdRooms = new ArrayList<>();
        int quantity = request.getQuantity() != null ? request.getQuantity() : 1;
        
        for (int i = 1; i <= quantity; i++) {
            RoomRequest singleRequest = new RoomRequest();
            singleRequest.setRoomType(request.getRoomType());
            singleRequest.setName(request.getName() + " " + i);
            singleRequest.setDescription(request.getDescription());
            singleRequest.setNumberOfAdults(request.getNumberOfAdults());
            singleRequest.setNumberOfChildren(request.getNumberOfChildren());
            singleRequest.setNumberOfBeds(request.getNumberOfBeds());
            singleRequest.setPrice(request.getPrice());
            singleRequest.setServices(request.getServices());
            singleRequest.setHotelId(request.getHotelId());
            
            RoomFactoryResponse room = createRoomWithAbstractFactory(singleRequest);
            createdRooms.add(room);
        }
        
        log.info("✅ [BATCH] Successfully created {} rooms", createdRooms.size());
        
        return BatchRoomCreationResponse.builder()
                .totalCreated(createdRooms.size())
                .factoryPattern("ABSTRACT FACTORY PATTERN (BATCH)")
                .rooms(createdRooms)
                .message(String.format("✅ Đã tạo thành công %d phòng và lưu vào DB", createdRooms.size()))
                .build();
    }
    
    @Override
    public String comparePatterns() {
        StringBuilder comparison = new StringBuilder();
        comparison.append("╔═══════════════════════════════════════════════════════╗\n");
        comparison.append("║       SO SÁNH 2 DESIGN PATTERN                        ║\n");
        comparison.append("╠═══════════════════════════════════════════════════════╣\n");
        comparison.append("║ FACTORY METHOD:                                       ║\n");
        comparison.append("║   ✓ Đơn giản, tạo nhanh                               ║\n");
        comparison.append("║   ✗ Chỉ có thông tin cơ bản                           ║\n");
        comparison.append("║   ✗ Không có pricing/cancellation policy              ║\n");
        comparison.append("╠═══════════════════════════════════════════════════════╣\n");
        comparison.append("║ ABSTRACT FACTORY:                                     ║\n");
        comparison.append("║   ✓ Tạo hệ sinh thái hoàn chỉnh                       ║\n");
        comparison.append("║   ✓ Có đầy đủ pricing policy                          ║\n");
        comparison.append("║   ✓ Có đầy đủ cancellation policy                     ║\n");
        comparison.append("║   ✓ Đảm bảo tính đồng bộ                              ║\n");
        comparison.append("╚═══════════════════════════════════════════════════════╝\n");
        return comparison.toString();
    }
    
    // ==================== HELPER METHODS ====================
    
    /**
     * Convert RoomBundle sang Room entity
     * RoomBundle không phải @Entity nên cần copy data sang Room để lưu DB
     */
    private Room convertBundleToRoom(RoomBundle bundle) {
        Room room = new Room();
        room.setName(bundle.getName());
        room.setDescription(bundle.getDescription());
        room.setPrice(bundle.getPrice());
        room.setNumberOfAdults(bundle.getNumberOfAdults());
        room.setNumberOfChildren(bundle.getNumberOfChildren());
        room.setNumberOfBeds(bundle.getNumberOfBeds());
        room.setType(bundle.getType());
        room.setStatus(bundle.getStatus());
        room.setServices(bundle.getServices());
        room.setHotel(bundle.getHotel());
        return room;
    }
    
    /**
     * Override Room data từ request vào RoomBundle (nếu có)
     * RoomBundle đã có template data từ factory, chỉ override những gì user cung cấp
     */
    private void overrideRoomDataFromRequest(RoomBundle bundle, RoomRequest request) {
        // Override data từ request nếu có
        if (request.getName() != null) {
            bundle.setName(request.getName());
        }
        if (request.getDescription() != null) {
            bundle.setDescription(request.getDescription());
        }
        if (request.getPrice() != null) {
            bundle.setPrice(request.getPrice());
        }
        if (request.getNumberOfAdults() > 0) {
            bundle.setNumberOfAdults(request.getNumberOfAdults());
        }
        if (request.getNumberOfChildren() > 0) {
            bundle.setNumberOfChildren(request.getNumberOfChildren());
        }
        if (request.getNumberOfBeds() > 0) {
            bundle.setNumberOfBeds(request.getNumberOfBeds());
        }
        if (request.getServices() != null && !request.getServices().isEmpty()) {
            bundle.setServices(request.getServices());
        }
        
        // Gán hotel nếu có
        if (request.getHotelId() != null) {
            Hotel hotel = hotelRepository.findById(request.getHotelId())
                    .orElseThrow(() -> new RuntimeException("Hotel not found with ID: " + request.getHotelId()));
            bundle.setHotel(hotel);
        }
        
        // Đảm bảo status là AVAILABLE
        bundle.setStatus(EROOMSTATUS.AVAILABLE);
    }
    
    private RoomEcosystemFactory getEcosystemFactory(String roomType) {
        return switch (roomType.toUpperCase()) {
            case "STANDARD" -> new StandardRoomEcosystemFactory();
            case "DELUXE" -> new DeluxeRoomEcosystemFactory();
            case "SUITE" -> new SuiteRoomEcosystemFactory();
            default -> throw new IllegalArgumentException("Invalid room type: " + roomType);
        };
    }
    
    private EROOMTYPE mapRoomType(String roomType) {
        return switch (roomType.toUpperCase()) {
            case "STANDARD" -> EROOMTYPE.DOUBLE;
            case "DELUXE" -> EROOMTYPE.KING;
            case "SUITE" -> EROOMTYPE.SUITE;
            default -> EROOMTYPE.SINGLE;
        };
    }
    
    /**
     * Lấy danh sách services mặc định theo loại phòng
     */
    private List<ESERVICE> getDefaultServices(String roomType) {
        return switch (roomType.toUpperCase()) {
            case "STANDARD" -> Arrays.asList(
                ESERVICE.WIFI,
                ESERVICE.TV,
                ESERVICE.AIR_CONDITIONING
            );
            case "DELUXE" -> Arrays.asList(
                ESERVICE.WIFI,
                ESERVICE.TV,
                ESERVICE.AIR_CONDITIONING,
                ESERVICE.MINIBAR,
                ESERVICE.BATHTUB
            );
            case "SUITE" -> Arrays.asList(
                ESERVICE.WIFI,
                ESERVICE.TV,
                ESERVICE.AIR_CONDITIONING,
                ESERVICE.MINIBAR,
                ESERVICE.BATHTUB,
                ESERVICE.BALCONY,
                ESERVICE.SAFE_BOX,
                ESERVICE.ROOM_SERVICE
            );
            default -> Arrays.asList(ESERVICE.WIFI, ESERVICE.TV, ESERVICE.AIR_CONDITIONING);
        };
    }
    
    private List<ESERVICE> mapAmenities(List<String> amenities) {
        // Map amenities to ESERVICE enum
        List<ESERVICE> services = new ArrayList<>();
        services.add(ESERVICE.WIFI);
        services.add(ESERVICE.BREAKFAST);
        services.add(ESERVICE.PARKING);
        return services;
    }
    
    private List<String> convertServicesToAmenities(List<ESERVICE> services) {
        if (services == null) return new ArrayList<>();
        return services.stream()
                .map(Enum::toString)
                .toList();
    }
    
    private int calculateBeds(int capacity) {
        return (int) Math.ceil(capacity / 2.0);
    }
}

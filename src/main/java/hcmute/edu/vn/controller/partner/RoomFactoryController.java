package hcmute.edu.vn.controller.partner;

import hcmute.edu.vn.dto.request.RoomRequest;
import hcmute.edu.vn.dto.response.BatchRoomCreationResponse;
import hcmute.edu.vn.dto.response.RoomFactoryResponse;
import hcmute.edu.vn.service.RoomFactoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST API Controller cho Factory Pattern Demo
 * Tạo phòng sử dụng Factory Method và Abstract Factory Pattern
 */
@RestController
@RequestMapping("/api/v1/factory-demo")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Factory Pattern Demo", description = "API demo Factory Method vs Abstract Factory Pattern")
@CrossOrigin(origins = "*")
public class RoomFactoryController {
    
    private final RoomFactoryService roomFactoryService;
    
    /**
     * Tạo phòng sử dụng Factory Method Pattern
     */
    @PostMapping("/factory-method")
    @Operation(summary = "Tạo phòng bằng Factory Method Pattern", 
               description = "Tạo phòng đơn giản, chỉ có thông tin cơ bản")
    public ResponseEntity<RoomFactoryResponse> createWithFactoryMethod(
            @RequestBody RoomRequest request) {
        
        log.info("API Request: Create room with Factory Method - Type: {}", request.getRoomType());
        
        RoomFactoryResponse response = roomFactoryService.createRoomWithFactoryMethod(request);
        
        log.info("API Response: Room created with ID: {}", response.getRoomId());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Tạo phòng sử dụng Abstract Factory Pattern
     */
    @PostMapping("/abstract-factory")
    @Operation(summary = "Tạo phòng bằng Abstract Factory Pattern",
               description = "Tạo hệ sinh thái phòng hoàn chỉnh với pricing và cancellation policy")
    public ResponseEntity<RoomFactoryResponse> createWithAbstractFactory(
            @RequestBody RoomRequest request) {
        
        log.info("API Request: Create room with Abstract Factory - Type: {}", request.getRoomType());
        
        RoomFactoryResponse response = roomFactoryService.createRoomWithAbstractFactory(request);
        
        log.info("API Response: Room ecosystem created with ID: {}", response.getRoomId());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Tạo nhiều phòng cùng lúc
     */
    @PostMapping("/batch")
    @Operation(summary = "Tạo nhiều phòng cùng lúc",
               description = "Sử dụng Abstract Factory để tạo nhiều phòng")
    public ResponseEntity<BatchRoomCreationResponse> createBatch(
            @RequestBody RoomRequest request) {
        
        log.info("API Request: Batch create {} rooms - Type: {}",
                request.getQuantity(), request.getRoomType());
        
        BatchRoomCreationResponse response = roomFactoryService.createMultipleRooms(request);
        
        log.info("API Response: Created {} rooms", response.getTotalCreated());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * So sánh 2 pattern
     */
    @GetMapping("/compare")
    @Operation(summary = "So sánh 2 Design Pattern",
               description = "Hiển thị so sánh giữa Factory Method và Abstract Factory")
    public ResponseEntity<String> comparePatterns() {
        
        log.info("API Request: Compare patterns");
        
        String comparison = roomFactoryService.comparePatterns();
        
        return ResponseEntity.ok(comparison);
    }
}
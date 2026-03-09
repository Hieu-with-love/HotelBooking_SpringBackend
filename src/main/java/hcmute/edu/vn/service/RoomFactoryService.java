package hcmute.edu.vn.service;

import hcmute.edu.vn.dto.request.RoomRequest;
import hcmute.edu.vn.dto.response.BatchRoomCreationResponse;
import hcmute.edu.vn.dto.response.RoomFactoryResponse;

/**
 * Service sử dụng Factory Pattern để tạo phòng
 */
public interface RoomFactoryService {
    
    /**
     * Tạo phòng sử dụng Factory Method Pattern
     * Lưu vào database với data từ RoomRequest
     */
    RoomFactoryResponse createRoomWithFactoryMethod(RoomRequest request);
    
    /**
     * Tạo phòng sử dụng Abstract Factory Pattern  
     * Lưu vào database với đầy đủ policies và data từ RoomRequest
     */
    RoomFactoryResponse createRoomWithAbstractFactory(RoomRequest request);
    
    /**
     * Tạo nhiều phòng cùng lúc
     */
    BatchRoomCreationResponse createMultipleRooms(RoomRequest request);
    
    /**
     * So sánh 2 pattern
     */
    String comparePatterns();
}

package hcmute.edu.vn.service;

import hcmute.edu.vn.dto.RoomDto;
import hcmute.edu.vn.dto.request.RoomRequest;
import hcmute.edu.vn.dto.response.PageResponse;
import hcmute.edu.vn.dto.response.RoomResponse;
import hcmute.edu.vn.model.Room;

import java.util.List;

public interface RoomService {
    PageResponse<RoomResponse> getAllRooms(int page, int size);
    Room getRoomById(Long id);
    void createRoom(RoomRequest req);
    void uploadRoomImages(Long roomId, List<String> imageUrls);
    void updateRoom(Long roomId, RoomRequest roomReq);
    void deleteRoom(Long id);
}

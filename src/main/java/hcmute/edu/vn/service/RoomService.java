package hcmute.edu.vn.service;

import hcmute.edu.vn.dto.RoomDto;
import hcmute.edu.vn.model.Room;

import java.util.List;

public interface RoomService {
    List<RoomDto> getAllRooms();
    Room getRoomById(Long id);
    void saveRoom(RoomDto roomDto);
    void updateRoom(Room room);
    void deleteRoom(Long id);
}

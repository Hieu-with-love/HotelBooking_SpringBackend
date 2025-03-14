package hcmute.edu.vn.service;

import hcmute.edu.vn.model.Room;

import java.util.List;

public interface RoomService {
    List<Room> getAllRooms();
    Room getRoomById(Long id);
    void saveRoom(Room room);
    void updateRoom(Room room);
    void deleteRoom(Long id);
}

package hcmute.edu.vn.service.impl;

import hcmute.edu.vn.model.Room;
import hcmute.edu.vn.repository.RoomRepository;
import hcmute.edu.vn.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    @Override
    public List<Room> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms;
    }

    @Override
    public Room getRoomById(Long id) {
        return null;
    }

    @Override
    public void saveRoom(Room room) {

    }

    @Override
    public void updateRoom(Room room) {

    }

    @Override
    public void deleteRoom(Long id) {

    }
}

package hcmute.edu.vn.service.impl;

import hcmute.edu.vn.dto.RoomDto;
import hcmute.edu.vn.model.Room;
import hcmute.edu.vn.repository.RoomRepository;
import hcmute.edu.vn.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    @Override
    public List<RoomDto> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        List<RoomDto> roomDtos = rooms.stream().map(room -> {
            RoomDto roomDto = new RoomDto();
            roomDto.setId(room.getId());
            roomDto.setName(room.getName());
            roomDto.setDescription(room.getDescription());
            roomDto.setPrice(room.getPrice());
            roomDto.setNumberOfBeds(room.getNumberOfBeds());
            return roomDto;
        }).collect(Collectors.toList());

        return roomDtos;
    }

    @Override
    public Room getRoomById(Long id) {
        return null;
    }

    @Override
    public void saveRoom(RoomDto roomDto) {
        Room room = new Room();
        room.setName(roomDto.getName());
        room.setDescription(roomDto.getDescription());
        room.setPrice(roomDto.getPrice());
        room.setNumberOfBeds(roomDto.getNumberOfBeds());

        roomRepository.save(room);
    }

    @Override
    public void updateRoom(Room room) {

    }

    @Override
    public void deleteRoom(Long id) {

    }
}

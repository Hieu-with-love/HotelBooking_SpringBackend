package hcmute.edu.vn.controller.customer;

import hcmute.edu.vn.dto.RoomDto;
import hcmute.edu.vn.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping("")
    public ResponseEntity<?> getAllRooms(){
        try{
            return ResponseEntity.ok(roomService.getAllRooms());
        }catch (Exception e){
            // currently, just demo cause just return error message. with production, should return error code and message
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRoom(@RequestBody RoomDto roomDto){
        try{
            roomService.saveRoom(roomDto);
            return ResponseEntity.ok("Room created successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

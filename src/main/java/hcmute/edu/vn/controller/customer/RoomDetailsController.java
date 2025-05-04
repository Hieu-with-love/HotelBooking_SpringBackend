package hcmute.edu.vn.controller.customer;

import hcmute.edu.vn.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://zotels-booking.id.vn/")
//@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/customer/rooms")
@RequiredArgsConstructor
public class RoomDetailsController {
    private final RoomService roomService;

//    @GetMapping("/{roomId}")
//    public ResponseEntity<?> getRoomById(@PathVariable("roomId") Long roomId){
//        return ResponseEntity.ok(roomService.getRoomById(roomId));
//    }

    @GetMapping("/{roomId}")
    public ResponseEntity<?> getRoomDetails(@PathVariable("roomId") Long roomId){
        return ResponseEntity.ok(roomService.getRoomDetailsById(roomId));
    }

}

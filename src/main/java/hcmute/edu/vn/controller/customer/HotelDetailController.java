package hcmute.edu.vn.controller.customer;

import hcmute.edu.vn.dto.request.SearchRoomsCriteria;
import hcmute.edu.vn.service.HotelService;
import hcmute.edu.vn.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer/hotels")
@RequiredArgsConstructor
public class HotelDetailController {
    private final HotelService hotelService;
    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<?> loadHotels(@RequestParam("page") int page,
                                        @RequestParam("size") int size){
        return ResponseEntity.ok(hotelService.getHotels(page, size));
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<?> loadHotelDetails(@PathVariable("hotelId") Long hotelId){
        return ResponseEntity.ok(hotelService.getHotelDetailsById(hotelId));
    }

    @GetMapping("/popular")
    public ResponseEntity<?> getPopularHotels(){
        return ResponseEntity.ok(hotelService.getPopularHotels());
    }

    @GetMapping("/search-rooms")
    public ResponseEntity<?> searchRoomsByCriteria(@RequestBody SearchRoomsCriteria criteria){
        return ResponseEntity.ok(roomService.getRoomsByCriteria(criteria));
    }

}

package hcmute.edu.vn.controller.partner;

import hcmute.edu.vn.dto.HotelDto;
import hcmute.edu.vn.model.Hotel;
import hcmute.edu.vn.service.HotelService;
import hcmute.edu.vn.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/partner/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @GetMapping
    public ResponseEntity<?> getAllHotels(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "1") int size) {
        return ResponseEntity.ok(hotelService.getHotels(page, size).getContent());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createHotel(@RequestParam("file") MultipartFile file,
                                         @ModelAttribute HotelDto hotelDto){
        try{
            String urlPicture = ImageUtils.getSavedUrl(file);
            hotelDto.setPicture(urlPicture);

            Hotel hotel = hotelService.createHotel(hotelDto);
            return ResponseEntity.ok(hotel);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/update/{hotelId}")
    public ResponseEntity<?> updateHotel(@PathVariable("hotelId") Long hotelId,
                                         @RequestBody HotelDto hotelDto){
        try{
            Hotel hotel = hotelService.updateHotel(hotelId, hotelDto);
            return ResponseEntity.ok(hotel);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{hotelId}")
    public ResponseEntity<?> deleteHotel(@PathVariable("hotelId") Long hotelId){
        try{
            hotelService.deleteHotel(hotelId);
            return ResponseEntity.ok("Hotel deleted successfully");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}

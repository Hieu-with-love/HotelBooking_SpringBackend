package hcmute.edu.vn.controller.partner;

import hcmute.edu.vn.dto.HotelDto;
import hcmute.edu.vn.dto.request.HotelRequest;
import hcmute.edu.vn.dto.response.CloudinaryResponse;
import hcmute.edu.vn.dto.response.HotelResponse;
import hcmute.edu.vn.dto.response.PageResponse;
import hcmute.edu.vn.model.Hotel;
import hcmute.edu.vn.service.CloudinaryService;
import hcmute.edu.vn.service.HotelService;
import hcmute.edu.vn.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("https://zotels-booking.id.vn")
@RestController
@RequestMapping("/api/partner/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;
    private final CloudinaryService cloudinaryService;

    @GetMapping
    public ResponseEntity<?> getAllHotels(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        PageResponse<HotelResponse> hotelsData = hotelService.getHotels(page, size);
        return ResponseEntity.ok(hotelsData);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<?> getHotelById(@PathVariable("hotelId") Long hotelId) {
        HotelResponse hotel = hotelService.getHotelDetailsById(hotelId);
        return ResponseEntity.ok(hotel);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createHotel(@RequestBody HotelRequest hotelRequest){
        try{
            Hotel hotel = hotelService.createHotel(hotelRequest);
            return ResponseEntity.ok(hotel);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping(value = "/create/{hotelId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadHotelImages(@PathVariable Long hotelId,
                                               @RequestParam("images") List<MultipartFile> images) {
        try {
            if (images.isEmpty()) {
                return ResponseEntity.badRequest().body("No images provided");
            }else {
                List<String> imageUrls = new ArrayList<>();
                for (MultipartFile image : images) {
                    CloudinaryResponse imageUrl = cloudinaryService.uploadFile(image, image.getOriginalFilename(), "hotel-images");
                    imageUrls.add(imageUrl.getUrl());
                }
                hotelService.saveImages(hotelId, imageUrls);
                return ResponseEntity.ok("Images uploaded successfully");
            }
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/update/{hotelId}")
    public ResponseEntity<?> updateHotel(@PathVariable("hotelId") Long hotelId,
                                         @RequestBody HotelRequest hotelRequest){
        try{
            Hotel hotel = hotelService.updateHotel(hotelId, hotelRequest);
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

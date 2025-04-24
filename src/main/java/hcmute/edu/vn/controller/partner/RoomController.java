package hcmute.edu.vn.controller.partner;

import hcmute.edu.vn.dto.request.RoomRequest;
import hcmute.edu.vn.service.RoomService;
import hcmute.edu.vn.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/partner/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<?> loadRooms(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(roomService.getAllRooms(page, size));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRoom(@RequestBody RoomRequest req) {
        try{
            roomService.createRoom(req);
            return ResponseEntity.ok("Room created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating room: " + e.getMessage());
        }
    }

    @PostMapping("/create/{roomId}/images")
    public ResponseEntity<?> uploadRoomImages(@PathVariable("roomId") Long roomId,
                                              @RequestParam("files")List<MultipartFile> files) {
        try{
            List<String> roomImages = new ArrayList<>();
            for (MultipartFile file : files) {
                String roomImage = ImageUtils.getSavedUrl(file);
                roomImages.add(roomImage);
            }

            roomService.uploadRoomImages(roomId, roomImages);
            return ResponseEntity.ok("Room images uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error uploading room images: " + e.getMessage());
        }
    }

    @PutMapping("/update/{roomId}")
    public ResponseEntity<?> updateRoom(@PathVariable("roomId") Long roomId,
                                        @RequestBody RoomRequest req) {
        return null;
    }

    @DeleteMapping("/delete/{roomId}")
    public ResponseEntity<?> deleteRoom(@PathVariable("roomId") Long roomId) {
        return null;
    }
}

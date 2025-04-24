package hcmute.edu.vn.service.impl;

import hcmute.edu.vn.converter.RoomConverter;
import hcmute.edu.vn.dto.RoomDto;
import hcmute.edu.vn.dto.request.RoomRequest;
import hcmute.edu.vn.dto.request.SearchRoomsCriteria;
import hcmute.edu.vn.dto.response.HotelResponse;
import hcmute.edu.vn.dto.response.PageResponse;
import hcmute.edu.vn.dto.response.RoomDetailsResponse;
import hcmute.edu.vn.dto.response.RoomResponse;
import hcmute.edu.vn.model.Hotel;
import hcmute.edu.vn.model.Review;
import hcmute.edu.vn.model.Room;
import hcmute.edu.vn.model.RoomImage;
import hcmute.edu.vn.repository.HotelRepository;
import hcmute.edu.vn.repository.ReviewRepository;
import hcmute.edu.vn.repository.RoomImageRepository;
import hcmute.edu.vn.repository.RoomRepository;
import hcmute.edu.vn.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomConverter roomConverter;
    private final HotelRepository hotelRepository;
    private final RoomImageRepository roomImageRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public PageResponse<RoomResponse> getAllRooms(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> roomPage = roomRepository.findAll(pageable);

        return roomConverter.toRoomResponsePage(roomPage);
    }

    @Override
    public Room getRoomById(Long id) {

        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    @Override
    public RoomDetailsResponse getRoomDetailsById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        RoomDetailsResponse roomDetailsResponse = roomConverter.toRoomDetailsResponse(room);

        List<Review> reviews = reviewRepository.findReviewsByRoomId(room.getId());
        double ratingSum = reviews.stream()
                .mapToDouble(Review::getRatings)
                .sum();
        int count = reviews.size();
        double totalRating = count == 0 ? 0 : ratingSum / count;
        roomDetailsResponse.setTotalRating(totalRating);
        roomDetailsResponse.setTotalReviews(count);

        return roomDetailsResponse;
    }

    @Override
    public void createRoom(RoomRequest req) {
        Hotel existingHotel = hotelRepository.findById(req.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        Room room = roomConverter.toRoom(req);
        room.setServices(req.getServices());
        existingHotel.addRoom(room);

        // save the room to database
        roomRepository.save(room);
    }

    @Override
    public void uploadRoomImages(Long roomId, List<String> imageUrls) {
        try{
            Room exstingRoom = roomRepository.findById(roomId)
                    .orElseThrow(() -> new RuntimeException("Room not found"));
            List<RoomImage> roomImagesList = new ArrayList<>();

            imageUrls.forEach(imageUrl -> {
                RoomImage roomImage = new RoomImage();
                roomImage.setUrl(imageUrl);
                exstingRoom.addImage(roomImage);
                // Set the room reference in RoomImage
                roomRepository.save(exstingRoom);
                // save the roomImage to database
                roomImageRepository.save(roomImage);
            });
        }
        catch (Exception e){
            // If have error when image handle, delete the saved room
            roomRepository.deleteById(roomId);
        }
    }


    @Override
    public void updateRoom(Long roomId, RoomRequest req) {

    }

    @Override
    public void deleteRoom(Long id) {

    }

    @Override
    public List<RoomDetailsResponse> getRoomsByCriteria(SearchRoomsCriteria criteria) {
        List<Room> searchedRoom = roomRepository.findRoomsByCriteria(criteria);

        List<Room> cloningSearchedRoom =  searchedRoom.stream()
                .map(room -> {
                    Room clone = new Room();
                    clone.setId(room.getId());
                    clone.setName(room.getName());
                    clone.setNumberOfAdults(room.getNumberOfAdults());
                    clone.setNumberOfChildren(room.getNumberOfChildren());
                    clone.setNumberOfBeds(room.getNumberOfBeds());
                    clone.setPrice(room.getPrice());
                    clone.setDescription(room.getDescription());
                    clone.setHotel(room.getHotel());
                    clone.setServices(room.getServices());

                    List<RoomImage> roomImage = roomImageRepository.findRoomImagesByRoomId(room.getId());
                    clone.setImages(roomImage);
                    return clone;
                }).toList();

        return roomConverter.toRoomDetailsResponseList(cloningSearchedRoom);
    }
}

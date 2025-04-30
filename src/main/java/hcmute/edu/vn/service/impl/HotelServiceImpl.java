package hcmute.edu.vn.service.impl;

import hcmute.edu.vn.converter.HotelConverter;
import hcmute.edu.vn.dto.HotelDto;
import hcmute.edu.vn.dto.request.HotelRequest;
import hcmute.edu.vn.dto.response.HotelBasicResponse;
import hcmute.edu.vn.dto.response.HotelDetailsResponse;
import hcmute.edu.vn.dto.response.HotelResponse;
import hcmute.edu.vn.dto.response.PageResponse;
import hcmute.edu.vn.enums.ESERVICE;
import hcmute.edu.vn.model.*;
import hcmute.edu.vn.repository.*;
import hcmute.edu.vn.service.CloudinaryService;
import hcmute.edu.vn.service.HotelService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final AddressRepository addressRepository;
    private final HotelConverter hotelConverter;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private CloudinaryService cloudinaryService;

    @Override
    public PageResponse<HotelResponse> getHotels(int offset, int limit) {
        // we need to crate a PageRequest object to represent the page needed and the number of page.
        // pageable is an interface that represents for page request.
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Hotel> hotels = hotelRepository.findAll(pageable);
        // if we want to sort the result, we can use Sort object to pass to PageRequest.of(offset, limit, sort)
        return hotelConverter.toPageResponse(hotels);
    }

    @Override
    public List<HotelBasicResponse> getPopularHotels() {
        // Get the top 5 hotels with the most rooms
        List<Hotel> hotels = hotelRepository.findAll();

        if (!hotels.isEmpty() && hotels.size() > 5){
            List<Hotel> subHotels = hotels.subList(0, 5);
            return hotelConverter.toBasicHotelsList(subHotels);
        }
        return hotelConverter.toBasicHotelsList(hotels);
    }

    @Override
    @Transactional
    public Hotel createHotel(HotelRequest hotelRequest) {
        Hotel hotel = new Hotel();

        String partnerEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User partner = userRepository.findByEmail(partnerEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email " + partnerEmail));

        // Get address and save it to the database
        addressRepository.save(hotelRequest.getAddress());
        
        hotel.setName(hotelRequest.getName());
        hotel.setBusinessName(hotelRequest.getBusinessName());
        hotel.setDescription(hotelRequest.getDescription());
        hotel.setAddress(hotelRequest.getAddress());
        hotel.setPhone(hotelRequest.getPhone());
        hotel.setEmail(hotelRequest.getEmail());
        hotel.setWebsite(hotelRequest.getSocialMedia().getWebsite());
        hotel.setFacebook(hotelRequest.getSocialMedia().getFacebook());
        hotel.setTwitter(hotelRequest.getSocialMedia().getTwitter());
        hotel.setInstagram(hotelRequest.getSocialMedia().getInstagram());
        hotel.setLinkedin(hotelRequest.getSocialMedia().getLinkedin());
        hotel.setTiktok(hotelRequest.getSocialMedia().getTiktok());
        hotel.setMinPriceRange(hotelRequest.getPriceRange().getMin());
        hotel.setMaxPriceRange(hotelRequest.getPriceRange().getMax());
        hotel.setServices(
                hotelRequest.getServices()
        );
        hotel.setPartner(partner);

        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel updateHotel(Long hotelId, HotelRequest hotelRequest) {
        Hotel existingHotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id " + hotelId));

        existingHotel.setName(hotelRequest.getName());
        existingHotel.setAddress(hotelRequest.getAddress());
        existingHotel.setPhone(hotelRequest.getPhone());
        existingHotel.setEmail(hotelRequest.getEmail());
        existingHotel.setWebsite(hotelRequest.getSocialMedia().getWebsite());
        existingHotel.setFacebook(hotelRequest.getSocialMedia().getFacebook());
        existingHotel.setTwitter(hotelRequest.getSocialMedia().getTwitter());
        existingHotel.setInstagram(hotelRequest.getSocialMedia().getInstagram());
        existingHotel.setBusinessName(hotelRequest.getBusinessName());
        existingHotel.setDescription(hotelRequest.getDescription());
        existingHotel.setMinPriceRange(hotelRequest.getPriceRange().getMin());
        existingHotel.setMaxPriceRange(hotelRequest.getPriceRange().getMax());
        existingHotel.setServices(
                hotelRequest.getServices()
        );

        return hotelRepository.save(existingHotel);
    }


    @Override
    public void deleteHotel(Long id) {
        Hotel exstingHotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id " + id));

        hotelRepository.delete(exstingHotel);
    }

    @Override
    @Transactional
    public void saveImages(Long hotelId, List<String> images) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id " + hotelId));
        List<HotelImage> hotelImages = new ArrayList<>();

        // Save each image to the database and associate it with the hotel
        for (String imageUrl : images) {
            HotelImage hotelImage = new HotelImage();
            hotelImage.setUrl(imageUrl);
            hotelImage.setHotel(hotel);
            hotelImages.add(hotelImage);
        }

        hotel.getImages().addAll(hotelImages);
        hotelRepository.save(hotel);
    }

    @Override
    public HotelDetailsResponse getHotelDetailsById(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id " + hotelId));

        HotelDetailsResponse hotelDetailsResponse = hotelConverter.toHotelDetailsResponse(hotel);

        List<List<Review>> roomsReviews = new ArrayList<>();
        for (Room room : hotel.getRooms()) {
            List<Review> roomReviews = reviewRepository.findReviewsByRoomId(room.getId());
            roomsReviews.add(roomReviews);
        }
        hotelDetailsResponse.setReviews(roomsReviews);

        double ratings = 0;
        int count = 0;
        // Tính tổng rating và số lượng đánh giá
                for (List<Review> roomReviews : roomsReviews) {
                    for (Review review : roomReviews) {
                        ratings += review.getRatings(); // Giả sử Review có phương thức getRating()
                        count++;
                    }
                }
        // Tính rating trung bình
        double averageRating = count > 0 ? ratings / count : 0;
        // Thiết lập rating trung bình cho khách sạn
        hotelDetailsResponse.setTotalRatings(averageRating);
        hotelDetailsResponse.setTotalReviews(count);

        return hotelDetailsResponse;
    }
}

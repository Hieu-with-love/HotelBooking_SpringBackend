package hcmute.edu.vn.service;

import hcmute.edu.vn.dto.HotelDto;
import hcmute.edu.vn.dto.request.HotelRequest;
import hcmute.edu.vn.dto.response.HotelBasicResponse;
import hcmute.edu.vn.dto.response.HotelResponse;
import hcmute.edu.vn.dto.response.PageResponse;
import hcmute.edu.vn.model.Hotel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HotelService {
    PageResponse<HotelResponse> getHotels(int offset, int limit);
    List<HotelBasicResponse> getPopularHotels();
    Hotel createHotel(HotelRequest hotelRequest);
    Hotel updateHotel(Long hotelId, HotelDto hotel);
    void deleteHotel(Long id);
    void saveImages(Long hotelId, List<String> images);

    HotelResponse getHotelDetailsById(Long hotelId);
}

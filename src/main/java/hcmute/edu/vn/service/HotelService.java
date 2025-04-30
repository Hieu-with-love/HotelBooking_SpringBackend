package hcmute.edu.vn.service;

import hcmute.edu.vn.dto.request.HotelRequest;
import hcmute.edu.vn.dto.response.HotelBasicResponse;
import hcmute.edu.vn.dto.response.HotelDetailsResponse;
import hcmute.edu.vn.dto.response.HotelResponse;
import hcmute.edu.vn.dto.response.PageResponse;
import hcmute.edu.vn.model.Hotel;

import java.util.List;

public interface HotelService {
    PageResponse<HotelResponse> getHotels(int offset, int limit);
    List<HotelBasicResponse> getPopularHotels();
    Hotel createHotel(HotelRequest hotelRequest);
    Hotel updateHotel(Long hotelId, HotelRequest hotelRequest);
    void deleteHotel(Long id);
    void saveImages(Long hotelId, List<String> images);

    HotelDetailsResponse getHotelDetailsById(Long hotelId);

}

package hcmute.edu.vn.service;

import hcmute.edu.vn.dto.HotelDto;
import hcmute.edu.vn.model.Hotel;
import org.springframework.data.domain.Page;

public interface HotelService {
    Page<Hotel> getHotels(int offset, int limit);
    Hotel createHotel(HotelDto hotelDto);
    Hotel updateHotel(Long hotelId, HotelDto hotel);
    void deleteHotel(Long id);
}

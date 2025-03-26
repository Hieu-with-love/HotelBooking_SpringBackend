package hcmute.edu.vn.service.impl;

import hcmute.edu.vn.dto.HotelDto;
import hcmute.edu.vn.model.Address;
import hcmute.edu.vn.model.Hotel;
import hcmute.edu.vn.repository.AddressRepository;
import hcmute.edu.vn.repository.HotelRepository;
import hcmute.edu.vn.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final AddressRepository addressRepository;

    @Override
    public Page<Hotel> getHotels(int offset, int limit) {
        // we need to crate a PageRequest object to represent the page needed and the number of page.
        // pageable is an interface that represents for page request.
        Pageable pageable = PageRequest.of(offset, limit);
        // if we want to sort the result, we can use Sort object to pass to PageRequest.of(offset, limit, sort)
        return hotelRepository.findAll(pageable);
    }

    @Override
    public Hotel createHotel(HotelDto hotelDto) {
        Hotel hotel = new Hotel();

        Address address = Address.builder()
                .number(hotelDto.getNumber())
                .street(hotelDto.getStreet())
                .district(hotelDto.getDistrict())
                .city(hotelDto.getCity())
                .build();
        addressRepository.save(address);

        hotel.setName(hotelDto.getName());
        hotel.setPicture(hotelDto.getPicture());
        hotel.setAddress(address);

        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel updateHotel(Long hotelId, HotelDto hotelDto) {
        Hotel existingHotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id " + hotelId));

        existingHotel.setName(hotelDto.getName());
        existingHotel.setPicture(hotelDto.getPicture());
        existingHotel.getAddress().setNumber(hotelDto.getNumber());

        return hotelRepository.save(existingHotel);
    }


    @Override
    public void deleteHotel(Long id) {
        Hotel exstingHotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id " + id));

        hotelRepository.delete(exstingHotel);
    }
}

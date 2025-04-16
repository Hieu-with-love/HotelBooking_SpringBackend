package hcmute.edu.vn.converter;

import hcmute.edu.vn.controller.partner.HotelController;
import hcmute.edu.vn.dto.request.HotelRequest;
import hcmute.edu.vn.dto.response.HotelBasicResponse;
import hcmute.edu.vn.dto.response.HotelResponse;
import hcmute.edu.vn.dto.response.PageResponse;
import hcmute.edu.vn.model.Hotel;
import hcmute.edu.vn.model.Voucher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HotelConverter {
    HotelConverter INSTANCE = Mappers.getMapper(HotelConverter.class);

    @Mapping(target = "id", ignore = true)
    Voucher toVoucher(HotelRequest hotelRequest);

    HotelResponse toResponse(Hotel hotel);

    default List<HotelResponse> toResponseList(List<Hotel> hotels){
        return hotels.stream()
                .map(hotel -> {
                    HotelResponse hotelResponse = toResponse(hotel);
                    hotelResponse.setId(hotel.getId());
                    hotel.setImages(hotel.getImages());
                    return hotelResponse;
                })
                .toList();
    }

    default PageResponse<HotelResponse> toPageResponse(Page<Hotel> page){
        return PageResponse.<HotelResponse>builder()
                .content(toResponseList(page.getContent()))
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .numberOfElements(page.getNumberOfElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .first(page.isFirst())
                .build();
    }

    default List<HotelBasicResponse> toBasicHotelsList(List<Hotel> hotels){
        return hotels.stream()
                .map(hotel -> {
                    HotelBasicResponse hotelBasicResponse = new HotelBasicResponse();
                    hotelBasicResponse.setName(hotel.getName());
                    hotelBasicResponse.setAddress(hotel.getAddress());
                    return hotelBasicResponse;
                }).toList();
    }
}

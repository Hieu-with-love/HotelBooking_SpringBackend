package hcmute.edu.vn.converter;

import hcmute.edu.vn.dto.request.HotelRequest;
import hcmute.edu.vn.dto.request.RoomRequest;
import hcmute.edu.vn.dto.response.HotelResponse;
import hcmute.edu.vn.dto.response.PageResponse;
import hcmute.edu.vn.dto.response.RoomResponse;
import hcmute.edu.vn.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomConverter {
    RoomConverter INSTANCE = Mappers.getMapper(RoomConverter.class);

    @Mapping(target = "id", ignore = true)
    Room toRoom(RoomRequest roomRequest);

    RoomResponse toRoomResponse(Room room);

    default List<RoomResponse> toRoomResponseList(List<Room> rooms){
        return rooms.stream()
                .map(this::toRoomResponse)
                .toList();
    }

    default PageResponse<RoomResponse> toRoomResponsePage(Page<Room> rooms){
        return PageResponse.<RoomResponse>builder()
                .content(toRoomResponseList(rooms.getContent()))
                .pageNumber(rooms.getNumber())
                .pageSize(rooms.getSize())
                .totalElements(rooms.getTotalElements())
                .numberOfElements(rooms.getNumberOfElements())
                .totalPages(rooms.getTotalPages())
                .last(rooms.isLast())
                .first(rooms.isFirst())
                .build();
    }
}

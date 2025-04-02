package hcmute.edu.vn.converter;

import hcmute.edu.vn.dto.request.SignupRequest;
import hcmute.edu.vn.dto.response.UserResponse;
import hcmute.edu.vn.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

//    @Mapping(target = "password", ignore = true)
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "isVerified", constant = "false")
//    @Mapping(target = "status", constant = "true")
//    User toEntity(SignupRequest signupRequest);
//
//    @Mapping(target = "password", ignore = true)
//    UserResponse toResponse(User user);
} 
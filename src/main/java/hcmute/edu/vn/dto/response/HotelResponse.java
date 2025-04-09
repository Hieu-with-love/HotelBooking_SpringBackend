package hcmute.edu.vn.dto.response;

import hcmute.edu.vn.model.Address;
import hcmute.edu.vn.model.HotelImage;
import hcmute.edu.vn.model.Room;
import lombok.Data;

import java.util.List;

@Data
public class HotelResponse {
    private Long id;
    private String name;
    private String businessName;
    private Address address;
    private String description;
    private String phone;
    private String email;
    private String website;
    private String facebook;
    private String instagram;
    private String twitter;
    private String linkedin;
    private String tiktok;

    private List<HotelImage> images;
    private List<Room> rooms;
    private List<String> services;
}

package hcmute.edu.vn.dto.response;

import hcmute.edu.vn.model.Address;
import hcmute.edu.vn.model.HotelImage;
import hcmute.edu.vn.model.Room;
import lombok.Data;

import java.util.List;

@Data
public class HotelResponse {
    protected Long id;
    protected String name;
    protected String businessName;
    protected Address address;
    protected String description;
    protected String phone;
    protected String email;
    protected String website;
    protected String facebook;
    protected String instagram;
    protected String twitter;
    protected String linkedin;
    protected String tiktok;

    protected List<HotelImage> images;
    protected List<Room> rooms;
    protected List<String> services;
}

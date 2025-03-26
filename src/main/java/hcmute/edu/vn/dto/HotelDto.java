package hcmute.edu.vn.dto;

import lombok.Data;

@Data
public class HotelDto {
    private Long id;
    private String name;
    private String picture;
    private String number;
    private String street;
    private String district;
    private String city;
}

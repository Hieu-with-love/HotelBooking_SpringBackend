package hcmute.edu.vn.dto.response;

import hcmute.edu.vn.model.Address;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class HotelBasicResponse {
    private String name;
    private Address address;
    private String ratings;
    private String reviews;
    private BigDecimal price;
}

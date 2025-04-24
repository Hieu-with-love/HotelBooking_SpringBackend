package hcmute.edu.vn.dto.request;

import hcmute.edu.vn.enums.ESERVICE;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RoomRequest {
    private Long id;
    private String name;
    private String description;
    private int numberOfAdults;
    private int numberOfChildren;
    private int numberOfBeds;
    private BigDecimal price;
    private List<ESERVICE> services;
    private Long hotelId;
}

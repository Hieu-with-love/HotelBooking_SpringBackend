package hcmute.edu.vn.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomResponse {
    private Long id;
    private String name;
    private String description;
    private int numberOfAdults;
    private int numberOfChildren;
    private int numberOfBeds;
    private BigDecimal price;

}

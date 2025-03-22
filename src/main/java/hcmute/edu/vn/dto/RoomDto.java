package hcmute.edu.vn.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int numberOfBeds;
}

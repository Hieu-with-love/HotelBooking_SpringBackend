package hcmute.edu.vn.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomResponse {
    protected Long id;
    protected String name;
    protected String description;
    protected int numberOfAdults;
    protected int numberOfChildren;
    protected int numberOfBeds;
    protected String price;

}

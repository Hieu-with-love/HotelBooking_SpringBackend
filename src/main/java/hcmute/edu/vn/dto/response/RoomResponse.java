package hcmute.edu.vn.dto.response;

import hcmute.edu.vn.model.RoomImage;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RoomResponse {
    protected Long id;
    protected String name;
    protected String description;
    protected int numberOfAdults;
    protected int numberOfChildren;
    protected int numberOfBeds;
    protected String price;
    protected String typeBed;
    private List<RoomImage> roomImages;
}

package hcmute.edu.vn.dto.request;

import hcmute.edu.vn.enums.EROOMTYPE;
import lombok.Data;

@Data
public class SearchRoomsCriteria {
    private String checkIn;
    private String checkOut;
    private int adults;
    private int children;
    private EROOMTYPE bedType;
}

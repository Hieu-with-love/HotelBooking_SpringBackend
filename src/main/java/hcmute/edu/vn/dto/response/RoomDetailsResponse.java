package hcmute.edu.vn.dto.response;

import hcmute.edu.vn.enums.ESERVICE;
import hcmute.edu.vn.model.Review;
import hcmute.edu.vn.model.RoomImage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoomDetailsResponse extends RoomResponse{
    private List<RoomImage> roomImages;
    private List<ESERVICE> services;
    private List<Review> reviews;
    private double totalRating;
    private int totalReviews;
}

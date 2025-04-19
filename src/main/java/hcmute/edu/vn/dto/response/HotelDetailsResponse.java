package hcmute.edu.vn.dto.response;

import hcmute.edu.vn.model.Review;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class HotelDetailsResponse extends HotelResponse{
    private List<List<Review>> reviews;
    private double totalRatings;
    private double serviceRatings;
    private double locationRatings;
    private double valueOfMoneyRatings;
    private double cleanlinessRatings;
    private double facilityRatings;
    private int totalReviews;
}

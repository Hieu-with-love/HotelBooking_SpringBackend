package hcmute.edu.vn.dto.request;

import hcmute.edu.vn.enums.ESERVICE;
import hcmute.edu.vn.model.Address;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class HotelRequest {
    private String name;
    private String businessName;
    private String description;
    private Address address;

    // Contact Information
    private String phone;
    private String email;
    private SocialMedia socialMedia;
    private PriceRange priceRange;
    private List<ESERVICE> services;

    @Data
    public static class SocialMedia{
        private String website;
        private String facebook;
        private String instagram;
        private String twitter;
        private String linkedin;
        private String tiktok;
    }

    @Data
    public static class PriceRange {
        private BigDecimal min;
        private BigDecimal max;
    }

}

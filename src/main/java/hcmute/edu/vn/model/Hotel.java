package hcmute.edu.vn.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import hcmute.edu.vn.enums.ESERVICE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotels")
@ToString(exclude = {"rooms", "partner", "address"})
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "business_name")
    private String businessName;

    private String description;

    // Contact Infomation
    private String phone;
    private String email;
    private String website;
    private String facebook;
    private String instagram;
    private String twitter;
    private String linkedin;
    private String tiktok;

    private BigDecimal minPriceRange;
    private BigDecimal maxPriceRange;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<HotelImage> images = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @JsonManagedReference
    private Address address;

    @OneToMany(mappedBy = "hotel",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Room> rooms = new ArrayList<>();

    @ManyToOne
    private User partner;

    @ElementCollection(targetClass = ESERVICE.class)
    @Enumerated(EnumType.STRING)
    private List<ESERVICE> services = new ArrayList<>();

    public void addRoom(Room room){
        this.rooms.add(room);
        room.setHotel(this);
    }
}

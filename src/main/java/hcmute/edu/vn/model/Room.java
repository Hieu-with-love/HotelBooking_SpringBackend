package hcmute.edu.vn.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import hcmute.edu.vn.enums.EROOMTYPE;
import hcmute.edu.vn.enums.ESERVICE;
import hcmute.edu.vn.enums.EROOMSTATUS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int numberOfAdults;

    private int numberOfChildren;

    private int numberOfBeds;

    private BigDecimal price;

    private String description;

    private EROOMSTATUS status = EROOMSTATUS.AVAILABLE;

    private EROOMTYPE type = EROOMTYPE.SINGLE;

    @ElementCollection(targetClass = ESERVICE.class)
    @Enumerated(EnumType.STRING)
    private List<ESERVICE> services;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomImage> images = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    @JsonBackReference
    private Hotel hotel;

    public void addImage(RoomImage image){
        images.add(image);
        image.setRoom(this);
    }
}

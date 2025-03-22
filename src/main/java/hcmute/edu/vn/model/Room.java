package hcmute.edu.vn.model;

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
    private EROOMSTATUS status = EROOMSTATUS.AVAILABLE;
    private String description;
    private EROOMTYPE type = EROOMTYPE.SINGLE;

    @ElementCollection
    private List<ESERVICE> services;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomImage> images = new ArrayList<>();
}

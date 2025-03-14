package hcmute.edu.vn.model;

import hcmute.edu.vn.enums.EROOMTYPE;
import hcmute.edu.vn.enums.ESERVICE;
import hcmute.edu.vn.enums.ROOMSTATUS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numberOfAdults;
    private int numberOfChildren;
    private int numberOfBeds;
    private BigDecimal price;
    private ROOMSTATUS status = ROOMSTATUS.AVAILABLE;
    private String description;
    private EROOMTYPE type;

    @ElementCollection
    private List<ESERVICE> services;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomImage> images;
}

package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class Room {
    @Id
    private String roomTypeId;
    private String type;
    private Double keyMoney;
    private Integer qty;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Reservation> reservations;

    public Room(){}
}

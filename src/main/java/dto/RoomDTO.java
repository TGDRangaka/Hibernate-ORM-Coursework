package dto;

import entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RoomDTO {
    private String roomTypeId;
    private String type;
    private Double keyMoney;
    private Integer qty;
    private List<Reservation> reservations;

    public RoomDTO(){}
}

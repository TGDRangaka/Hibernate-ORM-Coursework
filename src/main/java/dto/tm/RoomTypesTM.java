package dto.tm;

import entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RoomTypesTM {
    private String roomTypeId;
    private String type;
    private Double keyMoney;
    private Integer qty;

    public RoomTypesTM(){}
}

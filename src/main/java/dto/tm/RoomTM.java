package dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomTM {
    private String roomTypeId;
    private String roomType;
    private Double keyMoney;
    private Integer totQty;
    private Integer availableRooms;
    private Integer reservedRooms;

    public RoomTM(){}
}

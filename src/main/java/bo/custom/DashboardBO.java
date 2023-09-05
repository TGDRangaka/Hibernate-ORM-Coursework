package bo.custom;

import bo.SuperBO;
import dto.ReservationDTO;
import dto.RoomDTO;
import entity.Room;

import java.util.List;

public interface DashboardBO extends SuperBO {
    public int getAllReservationCount() throws Exception;
    public int getTotOfAvailableRoomsCount() throws Exception;
}

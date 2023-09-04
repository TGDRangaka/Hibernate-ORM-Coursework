package bo.custom;

import bo.SuperBO;
import dto.ReservationDTO;
import dto.RoomDTO;

import java.util.List;

public interface RegistrationBO extends SuperBO {
    public List<RoomDTO> getAllRooms() throws Exception;
    public RoomDTO getRoom(String roomTypeId) throws Exception;
    public boolean registerStudent(ReservationDTO reservation) throws  Exception;
    public String getNewReservationId() throws Exception;
}

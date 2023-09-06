package bo.custom;

import bo.SuperBO;
import dto.RoomDTO;

import java.util.List;

public interface RoomManageBO extends SuperBO {
    public List<RoomDTO> getAllRooms() throws Exception;
    public Integer getTotQtyOfRoom(String roomTypeId) throws Exception;
    public String getNewId() throws Exception;
    public boolean saveRoom(RoomDTO dto) throws Exception;
    public boolean updateRoom(RoomDTO dto) throws Exception;
    public boolean deleteRoom(String id) throws Exception;
}

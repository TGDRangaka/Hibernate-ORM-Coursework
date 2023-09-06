package dao.custom;

import dao.CrudDAO;
import entity.Room;

public interface RoomDAO extends CrudDAO<Room> {
    public Integer getTotQtyOfRooms(String roomTypeId) throws Exception;
}

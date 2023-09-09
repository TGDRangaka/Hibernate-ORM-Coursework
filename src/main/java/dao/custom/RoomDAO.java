package dao.custom;

import dao.CrudDAO;
import entity.Room;
import org.hibernate.Session;

public interface RoomDAO extends CrudDAO<Room> {
    public Integer getTotQtyOfRooms(String roomTypeId) throws Exception;
    public boolean setRoomQty(Integer count, String roomId, Session session) throws Exception;
}

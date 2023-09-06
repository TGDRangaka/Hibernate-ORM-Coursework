package bo.custom.impl;

import bo.custom.RoomManageBO;
import dao.DAOFactory;
import dao.custom.RoomDAO;
import dto.RoomDTO;
import entity.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomManageBOImpl implements RoomManageBO {
    private RoomDAO roomDAO = (RoomDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ROOM);

    @Override
    public List<RoomDTO> getAllRooms() throws Exception {
        List<Room> all = roomDAO.getAll();
        List<RoomDTO> rooms = new ArrayList<>();

        for (Room room : all) {
            rooms.add(new RoomDTO(
                    room.getRoomTypeId(),
                    room.getType(),
                    room.getKeyMoney(),
                    room.getQty(),
                    new ArrayList<>()
            ));
        }

        return rooms;
    }

    @Override
    public Integer getTotQtyOfRoom(String roomTypeId) throws Exception{
        return roomDAO.getTotQtyOfRooms(roomTypeId);
    }

    @Override
    public String getNewId() throws Exception {
        return roomDAO.generateNewID();
    }

    @Override
    public boolean saveRoom(RoomDTO dto) throws Exception {
        return roomDAO.add(new Room(
                dto.getRoomTypeId(),
                dto.getType(),
                dto.getKeyMoney(),
                dto.getQty(),
                new ArrayList<>()
        ));
    }

    @Override
    public boolean updateRoom(RoomDTO dto) throws Exception {
        return roomDAO.update(new Room(
                dto.getRoomTypeId(),
                dto.getType(),
                dto.getKeyMoney(),
                dto.getQty(),
                new ArrayList<>()
        ));
    }

    @Override
    public boolean deleteRoom(String id) throws Exception {
        return roomDAO.delete(id);
    }
}

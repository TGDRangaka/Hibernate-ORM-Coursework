package bo.custom.impl;

import bo.custom.DashboardBO;
import dao.DAOFactory;
import dao.custom.ReservationDAO;
import dao.custom.RoomDAO;
import dto.ReservationDTO;
import dto.RoomDTO;
import entity.Room;

import java.util.List;

public class DashboardBOImpl implements DashboardBO {
    private ReservationDAO reservationDAO = (ReservationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.RESERVATION);
    private RoomDAO roomDAO = (RoomDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ROOM);

    @Override
    public int getAllReservationCount() throws Exception {
        return reservationDAO.getAll().size();
    }

    @Override
    public int getTotOfAvailableRoomsCount() throws Exception {
        List<Room> all = roomDAO.getAll();

        int totRoomsAvailable = 0;
        for (Room room : all) {
            totRoomsAvailable += room.getQty();
        }

        return totRoomsAvailable;
    }
}

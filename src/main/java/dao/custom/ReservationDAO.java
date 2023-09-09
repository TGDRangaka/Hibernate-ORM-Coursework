package dao.custom;

import dao.CrudDAO;
import entity.Reservation;
import entity.Student;
import org.hibernate.Session;

public interface ReservationDAO extends CrudDAO<Reservation> {
    Reservation getReservationBySId(String sId) throws Exception;

    public boolean delete(String id, Session session) throws Exception;
}

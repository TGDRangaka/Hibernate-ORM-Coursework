package dao.custom;

import dao.CrudDAO;
import entity.Reservation;
import entity.Student;

public interface ReservationDAO extends CrudDAO<Reservation> {
    Reservation getReservationBySId(String sId) throws Exception;
}

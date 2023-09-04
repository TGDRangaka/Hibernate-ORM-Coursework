package dao;

import dao.custom.impl.ReservationDAOImpl;
import dao.custom.impl.RoomDAOImpl;
import dao.custom.impl.StudentDAOImpl;
import dao.custom.impl.UserDAOImpl;

public class DAOFactory {
    public static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getInstance(){
        return daoFactory == null ? (daoFactory = new DAOFactory()) : daoFactory;
    }

    public enum DAOTypes{
        USER, STUDENT, ROOM, RESERVATION;
    }

    public SuperDAO getDAO(DAOTypes type){
        switch (type){
            case USER: return new UserDAOImpl();
            case STUDENT: return new StudentDAOImpl();
            case ROOM: return new RoomDAOImpl();
            case RESERVATION: return new ReservationDAOImpl();
            default: return null;
        }
    }
}

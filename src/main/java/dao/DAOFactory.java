package dao;

import dao.custom.impl.*;

public class DAOFactory {
    public static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getInstance(){
        return daoFactory == null ? (daoFactory = new DAOFactory()) : daoFactory;
    }

    public enum DAOTypes{
        USER, STUDENT, ROOM, RESERVATION, JOIN;
    }

    public SuperDAO getDAO(DAOTypes type){
        switch (type){
            case USER: return new UserDAOImpl();
            case STUDENT: return new StudentDAOImpl();
            case ROOM: return new RoomDAOImpl();
            case RESERVATION: return new ReservationDAOImpl();
            case JOIN: return new JoinDAOImpl();
            default: return null;
        }
    }
}

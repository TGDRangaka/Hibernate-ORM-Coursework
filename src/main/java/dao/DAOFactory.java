package dao;

import dao.custom.impl.UserDAOImpl;

public class DAOFactory {
    public static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getInstance(){
        return daoFactory == null ? (daoFactory = new DAOFactory()) : daoFactory;
    }

    public enum DAOTypes{
        USER;
    }

    public SuperDAO getDAO(DAOTypes type){
        switch (type){
            case USER: return new UserDAOImpl();
            default: return null;
        }
    }
}

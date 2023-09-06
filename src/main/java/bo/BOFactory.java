package bo;

import bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){}

    public static BOFactory getInstance(){
        return boFactory == null ? (boFactory = new BOFactory()) : boFactory;
    }

    public enum BOTypes {
        LOGIN, REGISTRATION, DASHBOARD, STUDENT_MANAGE, ROOM_MANAGE, FIND, MAIN;
    }

    public SuperBO getBO(BOTypes type){
        switch (type){
            case LOGIN: return new LoginBOImpl();
            case REGISTRATION: return new RegistrationBOImpl();
            case DASHBOARD: return new DashboardBOImpl();
            case STUDENT_MANAGE: return new StudentManageBOImpl();
            case ROOM_MANAGE: return new RoomManageBOImpl();
            case FIND: return new FindBOImpl();
            case MAIN: return new MainBOImpl();
            default: return null;
        }
    }

}

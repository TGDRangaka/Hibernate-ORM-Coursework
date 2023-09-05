package bo;

import bo.custom.impl.DashboardBOImpl;
import bo.custom.impl.LoginBOImpl;
import bo.custom.impl.RegistrationBOImpl;
import bo.custom.impl.StudentManageBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){}

    public static BOFactory getInstance(){
        return boFactory == null ? (boFactory = new BOFactory()) : boFactory;
    }

    public enum BOTypes {
        LOGIN, REGISTRATION, DASHBOARD, STUDENT_MANAGE;
    }

    public SuperBO getBO(BOTypes type){
        switch (type){
            case LOGIN: return new LoginBOImpl();
            case REGISTRATION: return new RegistrationBOImpl();
            case DASHBOARD: return new DashboardBOImpl();
            case STUDENT_MANAGE: return new StudentManageBOImpl();
            default: return null;
        }
    }

}

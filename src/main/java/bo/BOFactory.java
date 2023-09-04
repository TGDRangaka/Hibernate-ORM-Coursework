package bo;

import bo.custom.impl.LoginBOImpl;
import bo.custom.impl.RegistrationBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){}

    public static BOFactory getInstance(){
        return boFactory == null ? (boFactory = new BOFactory()) : boFactory;
    }

    public enum BOTypes {
        LOGIN, REGISTRATION;
    }

    public SuperBO getBO(BOTypes type){
        switch (type){
            case LOGIN: return new LoginBOImpl();
            case REGISTRATION: return new RegistrationBOImpl();
            default: return null;
        }
    }

}

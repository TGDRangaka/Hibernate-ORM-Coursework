package bo;

import bo.custom.impl.LoginBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){}

    public static BOFactory getInstance(){
        return boFactory == null ? (boFactory = new BOFactory()) : boFactory;
    }

    public enum BOTypes {
        LOGIN;
    }

    public SuperBO getBO(BOTypes type){
        switch (type){
            case LOGIN: return new LoginBOImpl();
            default: return null;
        }
    }

}

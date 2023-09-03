package smart.warehouse.bo;

import smart.warehouse.bo.custom.impl.OperatorBOImpl;
import smart.warehouse.bo.custom.impl.StorageBOImpl;
import smart.warehouse.bo.custom.impl.UserBOImpl;

public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory() {
    }
    
    public static BoFactory getInstance(){
        return boFactory == null?
                boFactory = new BoFactory() : boFactory;
    }
    
    public <T extends SuperBO>T getBO(BoTypes boTypes){
        switch(boTypes){
            case OPERATOR:
                return (T) new OperatorBOImpl();
            case STORAGE:
                return (T) new StorageBOImpl();
            case USER:
                return (T) new UserBOImpl();
            default:
                return null;
        }
    }
}

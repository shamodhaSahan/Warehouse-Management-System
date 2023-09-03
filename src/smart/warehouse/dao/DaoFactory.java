package smart.warehouse.dao;

import static smart.warehouse.dao.DaoTypes.WAREHOUSESTATUS;
import smart.warehouse.dao.custom.impl.OperatorDAOImpl;
import smart.warehouse.dao.custom.impl.QuaryDAOImpl;
import smart.warehouse.dao.custom.impl.ReturnDAOImpl;
import smart.warehouse.dao.custom.impl.UserDAOImpl;
import smart.warehouse.dao.custom.impl.WarehouseDAOImpl;
import smart.warehouse.dao.custom.impl.WarehouseStatusDAOImpl;

public class DaoFactory {
    private static DaoFactory daoFactory;
     
    private DaoFactory() {
    }
    
    public static DaoFactory getInstance(){
        return daoFactory == null ?
                daoFactory = new DaoFactory() : daoFactory;
    }
    
    public <T extends SuperDAO>T getDAO(DaoTypes daoTypes){
        switch (daoTypes) {
            case OPERATOR:
                return (T) new OperatorDAOImpl();
            case RETURN:
                return (T) new ReturnDAOImpl();
            case USER:
                return (T) new UserDAOImpl();
            case WAREHOUSE:
                return (T) new WarehouseDAOImpl();
            case WAREHOUSESTATUS:
                return (T) new WarehouseStatusDAOImpl();
            case QUERY:
                return (T) new QuaryDAOImpl();
            default:
                return null;
        }
    }
}

package edu.icet.crm.dao;

import edu.icet.crm.dao.custom.impl.PlaceOrderDaoImpl;

public class DaoFactory {
    private DaoFactory(){}
    private static DaoFactory daoFactory;
    public static DaoFactory getInstance(){
        return daoFactory!=null? daoFactory:(daoFactory=new DaoFactory());
    }
    public <T extends SuperDao>T getDao(DaoType type){
        switch (type){
            case PLACE_ORDER_DAO:return (T)new PlaceOrderDaoImpl();
        }
        return null;
    }
}

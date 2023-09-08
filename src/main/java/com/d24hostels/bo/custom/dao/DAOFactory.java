package com.d24hostels.bo.custom.dao;


public class DAOFactory {
    private DAOFactory(){}
    private static DAOFactory daoFactory;

    public static DAOFactory getDaoFactory() {
        return (daoFactory==null)?daoFactory=new DAOFactory():daoFactory;
    }
    public enum DAOTypes{
        STUDENT,ROOMS,RESERVATION,USER,PRICE,UNIVERSITY
    }
    public static SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case STUDENT:return new StudentDaoImpl();
            case ROOMS:return new RoomDaoImpl();
            case RESERVATION:return new ReservationDaoImpl();
            case USER:return new UserDaoImpl();
            case PRICE:return new PriceDaoImpl();
            case UNIVERSITY:return new UniversityDaoImpl();
            default: return null;
        }
    }
}

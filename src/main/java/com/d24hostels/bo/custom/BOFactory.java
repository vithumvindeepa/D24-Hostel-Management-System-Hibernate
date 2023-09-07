package com.d24hostels.bo.custom;


public class BOFactory {
    private BOFactory(){}
    private static BOFactory BoFactory;

    public static BOFactory getBoFactory() {
        return (BoFactory==null)?BoFactory=new BOFactory():BoFactory;
    }
    public enum BOTypes{
        STUDENT,ROOMS,RESERVATION,USER,PRICE,UNIVERSITY
    }
    public static SuperBo getBO(BOTypes boTypes){
        switch (boTypes){
            case STUDENT:return new StudentBoImpl();
            case ROOMS:return new RoomBoImpl();
            case RESERVATION:return new ReservationBoImpl();
            case USER:return new UserBoImpl();
            case PRICE:return new PriceBoImpl();
            case UNIVERSITY:return new UniversityBoImpl();
            default: return null;
        }
    }
}

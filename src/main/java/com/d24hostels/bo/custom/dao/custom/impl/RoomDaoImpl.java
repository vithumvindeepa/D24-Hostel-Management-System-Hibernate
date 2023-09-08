package com.d24hostels.bo.custom.dao.custom.impl;

import com.d24hostels.dao.custom.RoomDao;
import com.d24hostels.entity.Price;
import com.d24hostels.entity.Room;
import com.d24hostels.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class RoomDaoImpl implements RoomDao {
    @Override
    public List<Room> getAll() throws Exception {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        Query query = session.createQuery("SELECT r.roomNo,p.TypeId,p.roomType,p.keyMoney,r.availability FROM Room r INNER JOIN Price p ON r.price.TypeId=p.TypeId");
        List<Object[]> list = query.list();
        List<Room> rooms=new ArrayList<>();
        System.out.println(list.size());
        for (Object[] o : list) {
            Room room = new Room();
            room.setRoomNo(String.valueOf(o[0]));
            room.setPrice(new Price(
                    String.valueOf(o[1]),
                    String.valueOf(o[2]),
                    Double.parseDouble(String.valueOf(o[3]))
            ));
            room.setAvailability(String.valueOf(o[4]));

            rooms.add(room);
        }

        transaction.commit();
        session.close();

        return rooms;
    }

    @Override
    public void save(Room entity) throws Exception {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        session.persist(entity);

        transaction.commit();
        session.close();
    }

    @Override
    public void update(Room entity) throws Exception {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        session.update(entity);

        transaction.commit();
        session.close();
    }

    @Override
    public void delete(String s) throws Exception {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        Room room = session.get(Room.class, s);
        session.remove(room);

        transaction.commit();
        session.close();
    }

    @Override
    public Room search(String s) throws Exception {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        Room room = session.get(Room.class, s);

        transaction.commit();
        session.close();
        return room;
    }

    @Override
    public List<Room> getAvailableRooms() throws Exception {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        Query query = session.createQuery("SELECT r.roomNo,p.TypeId,p.roomType,p.keyMoney,r.availability FROM Room r INNER JOIN Price p ON r.price.TypeId=p.TypeId WHERE r.availability=:paramValue");
        query.setParameter("paramValue", "Available");
        List<Object[]> list = query.list();
        List<Room> rooms=new ArrayList<>();
        System.out.println(list.size());
        for (Object[] o : list) {
            Room room = new Room();
            room.setRoomNo(String.valueOf(o[0]));
            room.setPrice(new Price(
                    String.valueOf(o[1]),
                    String.valueOf(o[2]),
                    Double.parseDouble(String.valueOf(o[3]))
            ));
            room.setAvailability(String.valueOf(o[4]));

            rooms.add(room);
        }
        transaction.commit();
        session.close();

        return rooms;
    }

    @Override
    public List<Room> getFilledRooms() throws Exception {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        Query query = session.createQuery("SELECT r.roomNo,p.TypeId,p.roomType,p.keyMoney,r.availability FROM Room r INNER JOIN Price p ON r.price.TypeId=p.TypeId WHERE r.availability=:paramValue");
        query.setParameter("paramValue", "Filled");
        List<Object[]> list = query.list();
        List<Room> rooms=new ArrayList<>();
        System.out.println(list.size());
        for (Object[] o : list) {
            Room room = new Room();
            room.setRoomNo(String.valueOf(o[0]));
            room.setPrice(new Price(
                    String.valueOf(o[1]),
                    String.valueOf(o[2]),
                    Double.parseDouble(String.valueOf(o[3]))
            ));
            room.setAvailability(String.valueOf(o[4]));

            rooms.add(room);
        }

        transaction.commit();
        session.close();

        return rooms;
    }
}

package com.d24hostels.bo.custom.dao.custom.impl;


import com.d24hostels.dao.custom.ReservationDao;
import com.d24hostels.entity.*;
import com.d24hostels.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationDaoImpl implements ReservationDao {

    @Override
    public List<Reservation> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery(
                "SELECT r.payId,r.amount,r.paidDate,r.status,r.room.roomNo,r.room.availability,r.room.price.TypeId," +
                        "r.room.price.roomType,r.room.price.keyMoney,r.student.sid,r.student.name FROM Reservation r"
        );

        List<Object[]> list = query.list();
        System.out.println("ob :"+list.size());
        List<Reservation> reservations = new ArrayList<>();

        for (Object[] o : list) {
            Reservation reservation = new Reservation();
            reservation.setPayId((Integer) o[0]);
            reservation.setAmount((Double) o[1]);
            reservation.setPaidDate((LocalDate) o[2]);
            reservation.setStatus((Boolean) o[3]);
            Room room = new Room();
            room.setRoomNo(String.valueOf(o[4]));
            room.setPrice(new Price(
                    String.valueOf(o[6]),
                    String.valueOf(o[7]),
                    Double.parseDouble(String.valueOf(o[8]))
            ));
            room.setAvailability(String.valueOf(o[5]));
            reservation.setRoom(room);
            Student student = new Student();
            student.setSid(String.valueOf(o[9]));
            student.setName(String.valueOf(o[10]));
            reservation.setStudent(student);

            reservations.add(reservation);
        }

        transaction.commit();
        session.close();

        return reservations;
    }

    @Override
    public void save(Reservation entity) throws Exception {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        session.persist(entity);

        transaction.commit();
        session.close();
    }

    @Override
    public void update(Reservation entity) throws Exception {
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

        Reservation reservation = session.get(Reservation.class, s);
        session.remove(reservation);

        transaction.commit();
        session.close();
    }

    @Override
    public Reservation search(String s) throws Exception {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        Reservation reservation = session.get(Reservation.class, s);

        transaction.commit();
        session.close();
        return reservation;
    }
}


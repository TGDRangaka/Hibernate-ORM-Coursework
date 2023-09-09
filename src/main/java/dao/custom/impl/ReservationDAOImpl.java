package dao.custom.impl;

import dao.custom.ReservationDAO;
import entity.Reservation;
import entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateFactoryConfig;
import util.NewId;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {
    @Override
    public List<Reservation> getAll() throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Reservation");
        List<Reservation> list = query.list();

        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public boolean add(Reservation entity) throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.persist(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Reservation entity) throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean exist(String id) throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        boolean isExist = session.get(Reservation.class, id) != null;

        transaction.commit();
        session.close();
        return isExist;
    }

    @Override
    public String generateNewID() throws Exception {
        String id = "RS001";

        if (exist(id)) {
            Session session = HibernateFactoryConfig.getInstance().getSession();
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("from Reservation order by id desc limit 1");
            List<Reservation> list = query.list();
            id = list.get(0).getId();

            transaction.commit();
            session.close();
            return NewId.getNewId(id);
        }

        return id;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String id, Session session) throws Exception {
        session.remove(session.get(Reservation.class, id));
        return true;
    }

    @Override
    public Reservation search(String id) throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Reservation reservation = session.get(Reservation.class, id);

        transaction.commit();
        session.close();

        return reservation;
    }

    @Override
    public Reservation getReservationBySId(String sId) throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Reservation where studentId =?1");
        query.setParameter(1, sId);
        List<Reservation> list = query.list();

        transaction.commit();
        session.close();

        return list.get(0);
    }
}

package dao.custom.impl;

import dao.custom.RoomDAO;
import entity.Reservation;
import entity.Room;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import util.HibernateFactoryConfig;
import util.NewId;

import java.util.List;

public class RoomDAOImpl implements RoomDAO {
    @Override
    public List<Room> getAll() throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Room");
        List<Room> list = query.list();

        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public boolean add(Room entity) throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.persist(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Room entity) throws Exception {
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

        boolean isExist = session.get(Room.class, id) != null;

        transaction.commit();
        session.close();
        return isExist;
    }

    @Override
    public String generateNewID() throws Exception {
        String id = "RM-001";

        if (exist(id)) {
            Session session = HibernateFactoryConfig.getInstance().getSession();
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("from Room order by id desc limit 1");
            List<Room> list = query.list();
            id = list.get(0).getRoomTypeId();

            transaction.commit();
            session.close();
            return NewId.getNewId(id);
        }

        return id;
    }

    @Override
    public boolean delete(String id) throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.remove(session.get(Room.class, id));

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Room search(String id) throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Room room = session.get(Room.class, id);

        transaction.commit();
        session.close();

        return room;
    }

    @Override
    public Integer getTotQtyOfRooms(String roomTypeId) throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        NativeQuery nativeQuery = session.createNativeQuery("SELECT COUNT(*) FROM reservation WHERE room_roomTypeId = ?1");
        nativeQuery.setParameter(1, roomTypeId);
        Long result = (Long) nativeQuery.uniqueResult();

        transaction.commit();
        session.close();

        return Math.toIntExact(result);
    }

    @Override
    public boolean setRoomQty(Integer count, String roomId, Session session) throws Exception {
        Query query = session.createQuery("from Room where type=?1");
        query.setParameter(1, roomId);
        List<Room> list = query.list();
        Room room = list.get(0);
        room.setQty(room.getQty() + count);
        session.update(room);

        return true;
    }
}

package dao.custom.impl;

import dao.custom.UserDAO;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateFactoryConfig;
import util.NewId;

import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public ArrayList<User> getAll() throws Exception {
        return null;
    }

    @Override
    public boolean add(User entity) throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.persist(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(User entity) throws Exception {
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

        boolean isExist = session.get(User.class, id) != null;

        transaction.commit();
        session.close();
        return isExist;
    }

    @Override
    public String generateNewID() throws Exception {
        String id = "U001";

        if (exist(id)) {
            Session session = HibernateFactoryConfig.getInstance().getSession();
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("from User order by id desc limit 1");
            List<User> list = query.list();
            id = list.get(0).getId();

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

        session.remove(session.get(User.class, id));

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public User search(String id) throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        User user = session.get(User.class, id);

        transaction.commit();
        session.close();

        return user;
    }

    @Override
    public boolean verifyUser(String username, String password) throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from User where username =?1 and password =?2");
        query.setParameter(1, username);
        query.setParameter(2, password);
        List list = query.list();

        transaction.commit();
        session.close();

        for (Object o : list) {return true;}
        return false;
    }
}

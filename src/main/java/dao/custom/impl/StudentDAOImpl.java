package dao.custom.impl;

import dao.custom.StudentDAO;
import entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateFactoryConfig;
import util.NewId;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public List<Student> getAll() throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("from Student");
        List<Student> list = query.list();

        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public boolean add(Student entity) throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.persist(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Student entity) throws Exception {
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

        boolean isExist = session.get(Student.class, id) != null;

        transaction.commit();
        session.close();
        return isExist;
    }

    @Override
    public String generateNewID() throws Exception {
        String id = "S001";

        if (exist(id)) {
            Session session = HibernateFactoryConfig.getInstance().getSession();
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("from Student order by id desc limit 1");
            List<Student> list = query.list();
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

        session.remove(session.get(Student.class, id));

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Student search(String id) throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Student student = session.get(Student.class, id);

        transaction.commit();
        session.close();

        return student;
    }
}

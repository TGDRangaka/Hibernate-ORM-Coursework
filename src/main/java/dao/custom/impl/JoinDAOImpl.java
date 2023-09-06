package dao.custom.impl;

import dao.custom.JoinDAO;
import entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateFactoryConfig;

import java.util.List;

public class JoinDAOImpl implements JoinDAO {
    @Override
    public List<Object[]> nonPayedKeyMoneyStd() throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery(
                "select s.id, s.name, s.address, s.contactNo, rm.keyMoney, rs.id, rm.roomTypeId from Student s " +
                        "inner join s.reservations rs " +
                        "inner join rs.room rm where rs.status = 'non payed'");
        List<Object[]> list = query.list();

        transaction.commit();
        session.close();

        return list;
    }
}

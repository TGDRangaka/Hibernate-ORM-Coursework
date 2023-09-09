package dao.custom;

import dao.CrudDAO;
import entity.Student;
import org.hibernate.Session;

public interface StudentDAO extends CrudDAO<Student> {
    public boolean add(Student student, Session session) throws Exception;
    public boolean delete(String id, Session session) throws Exception;
}

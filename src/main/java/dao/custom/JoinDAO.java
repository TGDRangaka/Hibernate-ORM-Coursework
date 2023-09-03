package dao.custom;

import dao.SuperDAO;
import entity.Student;

import java.util.List;

public interface JoinDAO extends SuperDAO {
    public List<Student> nonPayedKeyMoneyStd() throws Exception;
}

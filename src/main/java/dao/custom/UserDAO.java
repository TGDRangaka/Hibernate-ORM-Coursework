package dao.custom;

import dao.CrudDAO;
import entity.User;

public interface UserDAO extends CrudDAO<User> {
    public boolean verifyUser(String username, String password) throws Exception;
}

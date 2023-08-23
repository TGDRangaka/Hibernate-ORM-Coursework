package bo.custom;

import bo.SuperBO;
import dto.UserDTO;

public interface LoginBO extends SuperBO {
    public boolean addUser(UserDTO userDTO) throws Exception;
    public boolean updateUser(UserDTO userDTO) throws Exception;
    public boolean removeUser(String id) throws Exception;
    public boolean isExist(String id) throws Exception;
    public UserDTO search(String id) throws Exception;
    public boolean userVerify(String username, String password) throws Exception;
}

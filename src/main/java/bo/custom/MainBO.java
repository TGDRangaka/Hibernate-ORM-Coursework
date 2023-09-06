package bo.custom;

import bo.SuperBO;
import dto.UserDTO;

public interface MainBO extends SuperBO {
    public UserDTO getUser(UserDTO dto) throws Exception;
    public boolean updateUser(UserDTO dto) throws Exception;
}

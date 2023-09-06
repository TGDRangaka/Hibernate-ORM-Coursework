package bo.custom.impl;

import bo.custom.MainBO;
import dao.DAOFactory;
import dao.custom.UserDAO;
import dto.UserDTO;
import entity.User;

public class MainBOImpl implements MainBO {
    private UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public UserDTO getUser(UserDTO dto) throws Exception {
        User user = userDAO.search(dto.getUsername(), dto.getPassword());
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword()
        );
    }

    @Override
    public boolean updateUser(UserDTO dto) throws Exception {
        return userDAO.update(new User(dto.getId(), dto.getUsername(), dto.getPassword()));
    }
}

package bo.custom.impl;

import bo.custom.FindBO;
import dao.DAOFactory;
import dao.custom.JoinDAO;

import java.util.List;

public class FindBOImpl implements FindBO {
    private JoinDAO joinDAO = (JoinDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.JOIN);
    @Override
    public List<Object[]> getNonPayedStudents() throws Exception {
        return joinDAO.nonPayedKeyMoneyStd();
    }
}

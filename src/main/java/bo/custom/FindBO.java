package bo.custom;

import bo.SuperBO;

import java.util.List;

public interface FindBO extends SuperBO {
    public List<Object[]> getNonPayedStudents() throws Exception;
}

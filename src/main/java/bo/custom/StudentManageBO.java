package bo.custom;

import bo.SuperBO;
import dto.ReservationDTO;
import dto.StudentDTO;

import java.util.List;

public interface StudentManageBO extends SuperBO {
    List<ReservationDTO> getAllStudents() throws Exception;
    boolean updateStudent(StudentDTO dto, ReservationDTO reservation) throws Exception;
    boolean removeStudent(String sId, String resId, String roomTypeId) throws Exception;
}

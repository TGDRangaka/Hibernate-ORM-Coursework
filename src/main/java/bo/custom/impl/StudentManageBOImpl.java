package bo.custom.impl;

import bo.custom.StudentManageBO;
import dao.DAOFactory;
import dao.custom.ReservationDAO;
import dao.custom.StudentDAO;
import dto.ReservationDTO;
import dto.RoomDTO;
import dto.StudentDTO;
import entity.Reservation;
import entity.Room;
import entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateFactoryConfig;

import java.util.ArrayList;
import java.util.List;

public class StudentManageBOImpl implements StudentManageBO {
    private StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.STUDENT);
    private ReservationDAO reservationDAO = (ReservationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.RESERVATION);

    @Override
    public List<ReservationDTO> getAllStudents() throws Exception {
        List<Reservation> all = reservationDAO.getAll();
        List<ReservationDTO> reservationDTOS = new ArrayList<>();
        for (Reservation reservation : all) {
            ReservationDTO reservationDTO = new ReservationDTO(
                    reservation.getId(),
                    reservation.getDate(),
                    reservation.getStatus(),
                    null,
                    null
            );

            Student student = reservation.getStudent();
            reservationDTO.setStudent(new StudentDTO(
                    student.getId(),
                    student.getName(),
                    student.getAddress(),
                    student.getContactNo(),
                    student.getDob(),
                    student.getGender(),
                    null
            ));

            Room room = reservation.getRoom();
            reservationDTO.setRoom(new RoomDTO(
                    room.getRoomTypeId(),
                    room.getType(),
                    room.getKeyMoney(),
                    room.getQty(),
                    null
            ));

            reservationDTOS.add(reservationDTO);
        }

        return reservationDTOS;
    }

    @Override
    public boolean updateStudent(StudentDTO dto, ReservationDTO reservation) throws Exception {
        boolean isUpdated = studentDAO.update(new Student(
                dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getContactNo(),
                dto.getDob(),
                dto.getGender(),
                new ArrayList<>()
        ));

        if (isUpdated){
            Reservation res = reservationDAO.search(reservation.getId());
            res.setStatus(reservation.getStatus());
            reservationDAO.update(res);
        }

        return isUpdated;
    }

    @Override
    public boolean removeStudent(String sId, String resId, String roomType) throws Exception {
        Session session = null;
        Transaction transaction = null;

        try{
            session = HibernateFactoryConfig.getInstance().getSession();
            transaction = session.beginTransaction();

            System.out.println("remove ---------------------------1");

            session.remove(session.get(Reservation.class, resId));
            session.remove(session.get(Student.class, sId));

            Query query = session.createQuery("from Room where type=?1");
            query.setParameter(1, roomType);
            List<Room> list = query.list();
            Room room = list.get(0);
            room.setQty(room.getQty() + 1);
            session.update(room);

            transaction.commit();

            System.out.println("remove ---------------------------2");

            return true;
        }catch (Exception e){
            transaction.rollback();
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }
    }
}

package bo.custom.impl;

import bo.custom.RegistrationBO;
import dao.DAOFactory;
import dao.custom.ReservationDAO;
import dao.custom.RoomDAO;
import dao.custom.StudentDAO;
import dto.ReservationDTO;
import dto.RoomDTO;
import dto.StudentDTO;
import entity.Reservation;
import entity.Room;
import entity.Student;

import java.util.ArrayList;
import java.util.List;

public class RegistrationBOImpl implements RegistrationBO {
    private RoomDAO roomDAO = (RoomDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ROOM);
    private ReservationDAO reservationDAO = (ReservationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.RESERVATION);
    private StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.STUDENT);

    @Override
    public List<RoomDTO> getAllRooms() throws Exception {
        List<Room> all = roomDAO.getAll();
        List<RoomDTO> roomList = new ArrayList<>();
        for (Room room : all) {

            roomList.add(new RoomDTO(room.getRoomTypeId(), room.getType(), room.getKeyMoney(), room.getQty(), null));
        }
        return roomList;
    }

    @Override
    public RoomDTO getRoom(String roomTypeId) throws Exception {
        Room room = roomDAO.search(roomTypeId);
        return new RoomDTO(room.getRoomTypeId(), room.getType(), room.getKeyMoney(), room.getQty(), new ArrayList<>());
    }

    @Override
    public boolean registerStudent(ReservationDTO reservationDTO) throws Exception {
        StudentDTO studentDTO = reservationDTO.getStudent();
        Student student = new Student(
                studentDTO.getId(),
                studentDTO.getName(),
                studentDTO.getAddress(),
                studentDTO.getContactNo(),
                studentDTO.getDob(),
                studentDTO.getGender(),
                new ArrayList<>()
        );

        RoomDTO roomDTO = reservationDTO.getRoom();
        Room room = roomDAO.search(roomDTO.getRoomTypeId());

        Reservation reservation = new Reservation(
                reservationDTO.getId(),
                reservationDTO.getDate(),
                reservationDTO.getStatus(),
                student,
                room
        );
        student.getReservations().add(reservation);
        room.getReservations().add(reservation);


        boolean isRegistered = studentDAO.add(student);

        room.setQty(room.getQty() - 1);
        roomDAO.update(room);

        return isRegistered;
    }

    @Override
    public String getNewReservationId() throws Exception {
        return reservationDAO.generateNewID();
    }
}

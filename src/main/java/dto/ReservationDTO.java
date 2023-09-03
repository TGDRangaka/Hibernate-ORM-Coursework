package dto;

import entity.Room;
import entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class ReservationDTO {
    private String id;
    private LocalDate date;
    private String status;
    private Student student;
    private Room room;

    public ReservationDTO(){}
}

package dto;

import entity.Reservation;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class StudentDTO {
    private String id;
    private String name;
    private String address;
    private String contactNo;
    private LocalDate dob;
    private String gender;
    private List<ReservationDTO> reservations;

    public StudentDTO(){}
}

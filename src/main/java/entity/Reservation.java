package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
public class Reservation {
    @Id
    private String id;
    private LocalDate date;
    private String status;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Room room;

    public Reservation(){}
}

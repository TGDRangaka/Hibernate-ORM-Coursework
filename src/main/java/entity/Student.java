package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class Student {
    @Id
    private String id;
    private String name;
    private String address;
    private String contactNo;
    private LocalDate dob;
    private String gender;

    public Student(String id, String name, String address, String contactNo, LocalDate dob, String gender) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contactNo = contactNo;
        this.dob = dob;
        this.gender = gender;
    }

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reservation> reservations;

    public Student(){}
}

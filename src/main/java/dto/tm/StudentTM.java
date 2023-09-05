package dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class StudentTM {
    private String sId;
    private String sName;
    private String address;
    private String contactNo;
    private LocalDate dob;
    private String gender;
    private String resId;
    private LocalDate regDate;
    private String roomId;
    private String status;

    public StudentTM(){}
}

package controller;

import bo.BOFactory;
import bo.custom.StudentManageBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import dto.ReservationDTO;
import dto.RoomDTO;
import dto.StudentDTO;
import dto.tm.StudentTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentManageFormController implements Initializable {

    public JFXRadioButton rBtnOther;
    public JFXRadioButton rBtnPayed;
    public JFXRadioButton rBtnNonPayed;
    @FXML
    private TableView<StudentTM> tblStudent;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableColumn<?, ?> colStudentName;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContactNo;

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colResId;

    @FXML
    private TableColumn<?, ?> colRegDate;

    @FXML
    private TableColumn<?, ?> colRoomId;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private JFXTextField tfSearch;

    @FXML
    private JFXTextField tfStudentId;

    @FXML
    private JFXTextField tfStudentName;

    @FXML
    private JFXTextField tfAddress;

    @FXML
    private JFXTextField tfContactNo;

    @FXML
    private DatePicker dpDob;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXRadioButton rBtnMale;

    @FXML
    private JFXRadioButton rBtnFemale;

    private ObservableList<StudentTM> studentTMS;

    private StudentManageBO studentManageBO = (StudentManageBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.STUDENT_MANAGE);

    private String resId;
    private String roomTypeId;
    private StudentTM selectedStudent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadStudentTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadCellValueFactories();

    }

    private void loadStudentTable() throws Exception {
        studentTMS = FXCollections.observableArrayList();
        List<ReservationDTO> reservations = studentManageBO.getAllStudents();
        for (ReservationDTO reservation : reservations) {
            StudentDTO student = reservation.getStudent();
            RoomDTO room = reservation.getRoom();

            studentTMS.add(new StudentTM(
                    student.getId(),
                    student.getName(),
                    student.getAddress(),
                    student.getContactNo(),
                    student.getDob(),
                    student.getGender(),
                    reservation.getId(),
                    reservation.getDate(),
                    room.getType(),
                    reservation.getStatus()
            ));
        }

        tblStudent.setItems(studentTMS);
    }

    private void loadCellValueFactories() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("sId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("sName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colResId.setCellValueFactory(new PropertyValueFactory<>("resId"));
        colRegDate.setCellValueFactory(new PropertyValueFactory<>("regDate"));
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    @FXML
    void btnRemoveOnAction(ActionEvent event) {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are sure want to remove this student", yes, no).showAndWait();

        if (result.orElse(no) == yes){
            try {
                boolean isRemoved = studentManageBO.removeStudent(tfStudentId.getText(), resId, roomTypeId);
                if (isRemoved){
                    new Alert(Alert.AlertType.CONFIRMATION, "Student Removed!").show();
                    clearFields();
                }else {
                    new Alert(Alert.AlertType.WARNING, "Student hasn't removed!").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Oops..Something wend wrong!!!").show();
                e.printStackTrace();
            }
        }

        try {
            loadStudentTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String text = tfSearch.getText();
        if (text.equals("")){
            try {
                loadStudentTable();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        ObservableList<StudentTM> results = FXCollections.observableArrayList();
        for (StudentTM student : studentTMS){
            if (
                    text.equals(student.getSId()) ||
                    text.equals(student.getSName()) ||
                    text.equals(student.getResId()) ||
                    text.equals(student.getStatus()) ||
                    text.equals(student.getAddress()) ||
                    text.equals(student.getGender()) ||
                    text.equals(student.getRoomId()))
            {

                results.add(student);
            }
        }

        tblStudent.setItems(results);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        boolean correctValues = checkStudentValues();
        if (!correctValues){
            return;
        }

        StudentDTO student = new StudentDTO(
                tfStudentId.getText(),
                tfStudentName.getText(),
                tfAddress.getText(),
                tfContactNo.getText(),
                dpDob.getValue(),
                getGender(),
                new ArrayList<>()
        );

        ReservationDTO reservation = new ReservationDTO(selectedStudent.getResId(),null,getPayment(),null,null);
        try {
            boolean isStudentUpdated = studentManageBO.updateStudent(student, reservation);
            if (isStudentUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Student Updated!").show();
                clearFields();
            }else {
                new Alert(Alert.AlertType.WARNING, "Student hasn't Updated!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Oops..Something wend wrong!!!").show();
            e.printStackTrace();
        }

        try {
            loadStudentTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPayment() {
        if (rBtnPayed.isSelected()){
            return "payed";
        }

        return "non payed";
    }

    @FXML
    void rBtnFemaleOnMouseClicked(MouseEvent event) {
        if (rBtnMale.isSelected()){
            rBtnMale.setSelected(false);
        }
        if (rBtnOther.isSelected()){
            rBtnOther.setSelected(false);
        }
    }

    @FXML
    void rBtnMaleOnMouseClicked(MouseEvent event) {
        if (rBtnFemale.isSelected()){
            rBtnFemale.setSelected(false);
        }
        if (rBtnOther.isSelected()){
            rBtnOther.setSelected(false);
        }
    }

    public void rBtnOtherOnMouseClicked(MouseEvent mouseEvent) {
        if (rBtnMale.isSelected()){
            rBtnMale.setSelected(false);
        }
        if (rBtnFemale.isSelected()){
            rBtnFemale.setSelected(false);
        }
    }

    private String getGender() {
        if (rBtnMale.isSelected()){
            return "Male";
        }else if(rBtnFemale.isSelected()){
            return "Female";
        }else {
            return "Other";
        }
    }

    public void tblStudentOnMouseClicked(MouseEvent mouseEvent) {
        selectedStudent = tblStudent.getSelectionModel().getSelectedItem();

        tfStudentId.setText(selectedStudent.getSId());
        tfStudentName.setText(selectedStudent.getSName());
        tfAddress.setText(selectedStudent.getAddress());
        tfContactNo.setText(selectedStudent.getContactNo());
        dpDob.setValue(selectedStudent.getDob());
        setGender(selectedStudent.getGender());
        setPayment(selectedStudent.getStatus());

        resId = selectedStudent.getResId();
        roomTypeId = selectedStudent.getRoomId();
    }

    private boolean checkStudentValues() {
        if (!tfStudentId.getText().matches("^S\\d{3}$") || tfStudentId.getText() == null){
            new Alert(Alert.AlertType.WARNING,"Please enter the valid ID").show();
            return false;
        }
        if (!tfStudentName.getText().matches("^[A-Za-z]+(?: [A-Za-z]+)*$")){
            new Alert(Alert.AlertType.WARNING,"Please enter the valid name").show();
            return false;
        }
        if (!tfAddress.getText().matches("^[0-9A-Za-z ,.-]+$")){
            new Alert(Alert.AlertType.WARNING,"Please enter the valid address").show();
            return false;
        }
        if (!tfContactNo.getText().matches("^(?:\\+94)?0[1-9]\\d{8}$")){
            new Alert(Alert.AlertType.WARNING,"Please enter the valid contact No").show();
            return false;
        }
        if(!(rBtnFemale.isSelected() || rBtnMale.isSelected() || rBtnOther.isSelected())){
            new Alert(Alert.AlertType.WARNING,"Please select the gender").show();
            return false;
        }
        if(!(rBtnPayed.isSelected() || rBtnNonPayed.isSelected() )){
            new Alert(Alert.AlertType.WARNING,"Please select the payment section").show();
            return false;
        }
        return true;
    }

    private void setGender(String gender) {
        rBtnMale.setSelected(false);
        rBtnFemale.setSelected(false);
        rBtnOther.setSelected(false);

        if (gender.equals("male") || gender.equals("Male")){
            rBtnMale.setSelected(true);
        }else if(gender.equals("female") || gender.equals("Female")){
            rBtnFemale.setSelected(true);
        }else if(gender.equals("other") || gender.equals("Other")) {
            rBtnOther.setSelected(true);
        }
    }

    private void setPayment(String payment){
        rBtnPayed.setSelected(false);
        rBtnNonPayed.setSelected(false);

        if (payment.equals("payed")){
            rBtnPayed.setSelected(true);
        }else{
            rBtnNonPayed.setSelected(true);
        }

    }

    private void clearFields(){
        tfStudentId.setText(null);
        tfStudentName.setText(null);
        tfAddress.setText(null);
        tfContactNo.setText(null);
        dpDob.setValue(null);

        setGender("male");
        rBtnMale.setSelected(false);
    }

    public void rBtnPayedOnMouseClicked(MouseEvent mouseEvent) {
        if (rBtnNonPayed.isSelected()){
            rBtnNonPayed.setSelected(false);
        }
    }

    public void rBtnNonPayedOnMouseClicked(MouseEvent mouseEvent) {
        if (rBtnPayed.isSelected()){
            rBtnPayed.setSelected(false);
        }
    }
}

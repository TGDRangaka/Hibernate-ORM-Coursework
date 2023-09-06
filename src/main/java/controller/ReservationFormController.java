package controller;

import bo.BOFactory;
import bo.custom.RegistrationBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.ReservationDTO;
import dto.RoomDTO;
import dto.StudentDTO;
import dto.tm.RoomTypesTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationFormController implements Initializable {

    public VBox studentPane;
    public Pane reservationPane;
    public Label lblKeyMoney;
    public RadioButton rBtnOther;
    @FXML
    private JFXTextField tfStudentId;

    @FXML
    private JFXTextField tfStudentName;

    @FXML
    private JFXTextField tfStudentAddress;

    @FXML
    private JFXTextField tfContactNo;

    @FXML
    private DatePicker dpDob;

    @FXML
    private RadioButton rBtnMale;

    @FXML
    private RadioButton rBtnFemale;

    @FXML
    private JFXButton btnNext;

    @FXML
    private Label lblResId;

    @FXML
    private Label lblDate;

    @FXML
    private JFXComboBox<String> cboxRoomType;

    @FXML
    private TableView<RoomTypesTM> tblRooms;

    @FXML
    private TableColumn<?, ?> colRoomTypeId;

    @FXML
    private TableColumn<?, ?> colRoomType;

    @FXML
    private TableColumn<?, ?> colKeyPrice;

    @FXML
    private TableColumn<?, ?> colAvailableRooms;

    @FXML
    private RadioButton rBtnPayNow;

    @FXML
    private RadioButton rBtnPayLater;

    private RegistrationBO registrationBO;

    private ObservableList<RoomTypesTM> roomTMS = FXCollections.observableArrayList();
    private ObservableList<String> roomTypes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        registrationBO = (RegistrationBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.REGISTRATION);

        try {

            lblDate.setText(String.valueOf(LocalDate.now()));
            lblResId.setText(registrationBO.getNewReservationId());
            setCellvalueFactories();
            loadRoomsTable();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCellvalueFactories() {
        colRoomTypeId.setCellValueFactory(new PropertyValueFactory<>("roomTypeId"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colKeyPrice.setCellValueFactory(new PropertyValueFactory<>("keyMoney"));
        colAvailableRooms.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    private void loadRoomsTable() throws Exception {
        roomTMS = FXCollections.observableArrayList();
        roomTypes = FXCollections.observableArrayList();

        List<RoomDTO> allRooms = registrationBO.getAllRooms();

        for (RoomDTO room : allRooms) {
            roomTMS.add(new RoomTypesTM(room.getRoomTypeId(), room.getType(), room.getKeyMoney(), room.getQty()));

            if (room.getQty() > 0){
                roomTypes.add(room.getType());
            }
        }

        cboxRoomType.setItems(roomTypes);
        tblRooms.setItems(roomTMS);
    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        studentPane.setDisable(false);
        reservationPane.setDisable(true);
    }

    @FXML
    void btnNextOnAction(ActionEvent event) {
        boolean isCorrectValues = checkStudentValues();
        if (isCorrectValues) {

            studentPane.setDisable(true);
            reservationPane.setDisable(false);
            cboxRoomType.requestFocus();

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
    private String getPayMethod(){
        if (rBtnPayNow.isSelected()){
            return "payed";
        }else{
            return "non payed";
        }
    }
    private RoomDTO getRoom(String roomType){
        try {

            List<RoomDTO> allRooms = registrationBO.getAllRooms();
            for (RoomDTO room : allRooms) {
                if (roomType.equals(room.getType())){
                    return room;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        if (!tfStudentAddress.getText().matches("^[0-9A-Za-z ,.-]+$")){
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
        return true;
    }
    private boolean checkReservationValues(){
        if(cboxRoomType.getValue() == null){
            new Alert(Alert.AlertType.WARNING,"Please select the room type").show();
            return false;
        }
        if(!(rBtnPayLater.isSelected() || rBtnPayNow.isSelected())){
            new Alert(Alert.AlertType.WARNING,"Please select the payment method").show();
            return false;
        }

        return true;
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        boolean isvaluesCorrect = checkReservationValues();
        if (isvaluesCorrect){
            try {
                StudentDTO student = new StudentDTO(
                        tfStudentId.getText(),
                        tfStudentName.getText(),
                        tfStudentAddress.getText(),
                        tfContactNo.getText(),
                        dpDob.getValue(),
                        getGender(),
                        new ArrayList<>()
                );

                RoomDTO room = getRoom(cboxRoomType.getValue());

                ReservationDTO reservation = new ReservationDTO(
                        lblResId.getText(),
                        LocalDate.now(),
                        getPayMethod(),
                        student,
                        room
                );

                student.getReservations().add(reservation);
                room.setReservations(new ArrayList<>());
                room.getReservations().add(reservation);

                boolean isRegistered = registrationBO.registerStudent(reservation);
                if (isRegistered){
                    new Alert(Alert.AlertType.CONFIRMATION, "Registration Completed!").show();
                    clearFields();
                }else {

                    new Alert(Alert.AlertType.WARNING, "Registration Failed!!!").show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Oops..Something Went Wrong...").show();
            }
        }
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

    @FXML
    void rBtnPayLaterOnMouseClicked(MouseEvent event) {
        if (rBtnPayNow.isSelected()){
            rBtnPayNow.setSelected(false);
        }
    }

    @FXML
    void rBtnPayNowOnMouseClicked(MouseEvent event) {
        if (rBtnPayLater.isSelected()){
            rBtnPayLater.setSelected(false);
        }
    }

    @FXML
    public void cboxRoomTypeOnAction(ActionEvent event) {
        RoomDTO room = getRoom(cboxRoomType.getValue());
        lblKeyMoney.setText("Rs: " + room.getKeyMoney());
    }

    private void clearFields(){
        tfStudentId.setText(null);
        tfStudentName.setText(null);
        tfStudentAddress.setText(null);
        tfContactNo.setText(null);

        rBtnMale.setSelected(false);
        rBtnFemale.setSelected(false);
        rBtnPayNow.setSelected(false);
        rBtnPayLater.setSelected(false);

        dpDob.setValue(null);
        lblKeyMoney.setText("");

        try {
            loadRoomsTable();
            lblResId.setText(registrationBO.getNewReservationId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
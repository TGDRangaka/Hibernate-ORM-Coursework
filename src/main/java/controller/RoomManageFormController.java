package controller;

import bo.BOFactory;
import bo.custom.RoomManageBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.custom.RoomDAO;
import dto.RoomDTO;
import dto.tm.RoomTM;
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

public class RoomManageFormController implements Initializable {

    public JFXButton btnUpdate;
    public JFXButton btnRemove;
    @FXML
    private TableView<RoomTM> tblRooms;

    @FXML
    private TableColumn<?, ?> colRoomTypeId;

    @FXML
    private TableColumn<?, ?> colRoomType;

    @FXML
    private TableColumn<?, ?> colKeyMoney;

    @FXML
    private TableColumn<?, ?> colTotalQty;

    @FXML
    private TableColumn<?, ?> colAvailableRooms;

    @FXML
    private TableColumn<?, ?> colReservedRooms;

    @FXML
    private JFXTextField tfRoomTypeId;

    @FXML
    private JFXTextField tfRoomType;

    @FXML
    private JFXTextField tfKeyMoney;

    @FXML
    private JFXTextField tfQty;

    @FXML
    private JFXButton btnSave;

    private ObservableList<RoomTM> roomTMS;

    private RoomManageBO roomManageBO = (RoomManageBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ROOM_MANAGE);
    private RoomTM selectedRoom;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadRoomstable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadCellValueFactories();
        setFieldsOnAction();
    }

    private void setFieldsOnAction() {
        tfRoomType.setOnAction((e) -> {
            tfKeyMoney.requestFocus();
        });
        tfKeyMoney.setOnAction((e) -> {
            tfRoomType.requestFocus();
        });

    }

    private void loadRoomstable() throws Exception {
        roomTMS = FXCollections.observableArrayList();
        List<RoomDTO> allRooms = roomManageBO.getAllRooms();


        for (RoomDTO room : allRooms) {

            Integer totQty = roomManageBO.getTotQtyOfRoom(room.getRoomTypeId()) + room.getQty();
            Integer reservedRooms = totQty - room.getQty();

            RoomTM roomTM = new RoomTM(
                    room.getRoomTypeId(),
                    room.getType(),
                    room.getKeyMoney(),
                    totQty,
                    room.getQty(),
                    reservedRooms
            );

            roomTMS.add(roomTM);
        }

        tblRooms.setItems(roomTMS);
    }

    private void loadCellValueFactories() {
        colRoomTypeId.setCellValueFactory(new PropertyValueFactory<>("roomTypeId"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("keyMoney"));
        colTotalQty.setCellValueFactory(new PropertyValueFactory<>("totQty"));
        colAvailableRooms.setCellValueFactory(new PropertyValueFactory<>("availableRooms"));
        colReservedRooms.setCellValueFactory(new PropertyValueFactory<>("reservedRooms"));
    }

    @FXML
    void btnAddNewTypeOnAction(ActionEvent event) {
        clearFields();
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnRemove.setDisable(true);

        tfRoomType.requestFocus();
        try {
            tfRoomTypeId.setText(roomManageBO.getNewId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnMaxOnAction(ActionEvent event) {
        int qty = Integer.parseInt(tfQty.getText());
        if (qty < 100) {
            tfQty.setText(String.valueOf(qty + 1));
        }
    }

    @FXML
    void btnMinOnAction(ActionEvent event) {
        int qty = Integer.parseInt(tfQty.getText());
        if (qty > selectedRoom.getReservedRooms()) {
            tfQty.setText(String.valueOf(qty - 1));
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        boolean isCorrectValues = verifyFiedsValues();
        if (!isCorrectValues){
            return;
        }

        try {
            boolean isSaved = roomManageBO.saveRoom(new RoomDTO(
                                    tfRoomTypeId.getText(),
                                    tfRoomType.getText(),
                                    Double.parseDouble(tfKeyMoney.getText()),
                                    Integer.parseInt(tfQty.getText()),
                                    new ArrayList<>()
            ));

            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Room saved!").show();

                btnSave.setDisable(true);
                btnUpdate.setDisable(false);
                btnRemove.setDisable(false);
                clearFields();
            }else {
                new Alert(Alert.AlertType.WARNING, "Room hasn't saved!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Oops..Something wend wrong!!!").show();
            e.printStackTrace();
        }

        try {
            loadRoomstable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnRemoveOnAction(ActionEvent event) {
        if (selectedRoom.getReservedRooms() != 0) {
            new Alert(Alert.AlertType.WARNING, "Room can't be remove because there are reserved rooms in this type!!!").show();
            return;
        }

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are sure want to remove this student", yes, no).showAndWait();

        if (result.orElse(no) == yes){
            try {
                boolean isRemoved = roomManageBO.deleteRoom(tfRoomTypeId.getText());

                if (isRemoved){
                    new Alert(Alert.AlertType.CONFIRMATION, "Room Removed!").show();

                    btnSave.setDisable(true);
                    btnUpdate.setDisable(false);
                    btnRemove.setDisable(false);
                    clearFields();
                }else {
                    new Alert(Alert.AlertType.WARNING, "Room hasn't removed!").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Oops..Something wend wrong!!!").show();
                e.printStackTrace();
            }

        }

        try {
            loadRoomstable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean verifyFiedsValues() {
        if (tfKeyMoney.getText() == null || !tfKeyMoney.getText().matches("^(?!0\\d)(?:\\d{1,3}(?:,\\d{3})*(?:\\.\\d{0,2})?|\\d*(?:\\.\\d{0,2})?)$")){
            new Alert(Alert.AlertType.WARNING, "Please enter a valid value for Key Money").show();
            return false;
        }
        if (tfRoomType.getText() == null || tfRoomType.getText().equals("")){
            new Alert(Alert.AlertType.WARNING, "Please enter a room type").show();
            return false;
        }
        if (Integer.parseInt(tfQty.getText()) <= 0){
            new Alert(Alert.AlertType.WARNING, "Qty should not be 00").show();
            return false;
        }

        return true;
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        boolean isCorrectValues = verifyFiedsValues();
        if (!isCorrectValues){
            return;
        }

        try {

            Integer qty = Integer.valueOf(tfQty.getText()) - selectedRoom.getReservedRooms();
            boolean isUpdated = roomManageBO.updateRoom(new RoomDTO(
                    tfRoomTypeId.getText(),
                    tfRoomType.getText(),
                    Double.parseDouble(tfKeyMoney.getText()),
                    qty,
                    new ArrayList<>()
            ));

            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Room Updated!").show();

                btnSave.setDisable(true);
                btnUpdate.setDisable(false);
                btnRemove.setDisable(false);
                clearFields();
            }else {
                new Alert(Alert.AlertType.WARNING, "Room hasn't Updated!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Oops..Something wend wrong!!!").show();
            e.printStackTrace();
        }

        try {
            loadRoomstable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void tblRoomsOnMouseClicked(MouseEvent event) {
        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        btnRemove.setDisable(false);

        selectedRoom = tblRooms.getSelectionModel().getSelectedItem();

        tfRoomTypeId.setText(selectedRoom.getRoomTypeId());
        tfRoomType.setText(selectedRoom.getRoomType());
        tfQty.setText(String.valueOf(selectedRoom.getTotQty()));
        tfKeyMoney.setText(String.valueOf(selectedRoom.getKeyMoney()));
    }

    private void clearFields() {
        tfRoomTypeId.setText("RM-");
        tfRoomType.setText(null);
        tfQty.setText("00");
        tfKeyMoney.setText(null);
    }

}

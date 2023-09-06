package controller;

import bo.BOFactory;
import bo.custom.MainBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.UserDTO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MainWindowFormController implements Initializable {
    @FXML
    private JFXButton btnUser;

    @FXML
    private Label lblTime;

    @FXML
    private AnchorPane root;

    @FXML
    private Pane userPane;

    @FXML
    private JFXTextField tfUserName;

    @FXML
    private JFXTextField tfPassword;

    @FXML
    private JFXPasswordField pfPassword;

    @FXML
    private Button btnEye;

    @FXML
    private ImageView imgEye;

    private UserDTO userDTO = new UserDTO();

    private int x = 1;

    private MainBO mainBO = (MainBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.MAIN);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnDashboardOnAction(new ActionEvent());
        setTime();

        pfPassword.textProperty().bindBidirectional(tfPassword.textProperty());
    }

    private void loadUserDetails() {
        userPane.setVisible(false);
        try {
            userDTO = mainBO.getUser(userDTO);

            tfUserName.setText(userDTO.getUsername());
            pfPassword.setText(userDTO.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTime() {
        new Thread() {
            @Override
            public void run() {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        LocalTime currentTime = LocalTime.now();
                        Platform.runLater(() -> {
                            lblTime.setText(currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss a")));
                        });
                    }
                }, 0, 1000);
            }
        }.start();
    }

    @FXML
    void btnFindOnAction(ActionEvent event) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/view/find_form.fxml"));
            root.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnRegistrationOnAction(ActionEvent event) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/view/reservation_form.fxml"));
            root.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnRoomManageOnAction(ActionEvent event) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/view/room_manage_form.fxml"));
            root.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnStudentManageOnAction(ActionEvent event) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/view/student_manage_form.fxml"));
            root.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnUserOnAction(ActionEvent event) {
        loadUserDetails();
        userPane.setVisible(true);
    }

    public void setUser(UserDTO user) {
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        btnUser.setText(user.getUsername());
    }

    public void btnDashboardOnAction(ActionEvent event) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
            root.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnEyeOnAction(ActionEvent event) {
        pfPassword.setVisible(!pfPassword.isVisible());
    }

    public void btnUpdateOnAction(ActionEvent event) {
        boolean isCorrectValues = checkUserValues();
        if (!isCorrectValues){
            return;
        }

        try {
            userDTO.setUsername(tfUserName.getText());
            userDTO.setPassword(pfPassword.getText());

            boolean isUpdated = mainBO.updateUser(userDTO);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION , "User Details Updated!").show();

                btnCancelOnAction(new ActionEvent());
            }else {
                new Alert(Alert.AlertType.WARNING , "User Details didn't Updated!!!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR , "Oops..Something went wrong!!!").show();
        }
    }

    private boolean checkUserValues() {
        if (!tfUserName.getText().matches("^[a-zA-Z0-9_]{3,20}$")){
            new Alert(Alert.AlertType.WARNING , "Please enter a valid username!").show();
            return false;
        }
        if (!pfPassword.getText().matches("^[A-Za-z\\d]{4,}$")){
            new Alert(Alert.AlertType.WARNING , "Please enter a minimum 4 character password!").show();
            return false;
        }
        return true;
    }

    public void btnCancelOnAction(ActionEvent event) {
        tfUserName.setText(null);
        pfPassword.setText(null);

        userPane.setVisible(false);
    }
}

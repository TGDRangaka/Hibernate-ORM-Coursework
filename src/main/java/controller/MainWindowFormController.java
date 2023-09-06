package controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MainWindowFormController implements Initializable {

    public JFXButton btnUser;
    public Label lblTime;
    @FXML
    private AnchorPane root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnDashboardOnAction(new ActionEvent());
        setTime();
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
        try {
            Node node = FXMLLoader.load(getClass().getResource("/view/find_form.fxml"));
            root.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUserName(String text) {
        btnUser.setText(text);
    }

    public void btnDashboardOnAction(ActionEvent event) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
            root.getChildren().setAll(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

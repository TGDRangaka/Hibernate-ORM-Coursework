package controller;

import bo.BOFactory;
import bo.custom.DashboardBO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    @FXML
    private Label lblTotResRoomsCount;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTotAvailableRoomsCount;

    private DashboardBO dashboardBO = (DashboardBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.DASHBOARD);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTime();
        setDate();
        try {
            setTotReservedRoomsCount();
            setTotAvailableRoomsCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTotReservedRoomsCount() throws Exception {
        lblTotResRoomsCount.setText(String.valueOf(dashboardBO.getAllReservationCount()));
    }

    private void setTotAvailableRoomsCount() throws Exception {
        lblTotAvailableRoomsCount.setText(String.valueOf(dashboardBO.getTotOfAvailableRoomsCount()));
    }

    private void setTime() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        lblTime.setText(currentTime.format(formatter));
    }

    private void setDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd EEEE", Locale.ENGLISH);
        lblDate.setText(currentDate.format(formatter));
    }
}

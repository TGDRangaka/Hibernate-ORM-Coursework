package controller;

import bo.BOFactory;
import bo.custom.FindBO;
import dto.tm.NonPayedStudentsTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FindFormController implements Initializable {

    @FXML
    private TableView<NonPayedStudentsTM> tblNonPayedStudents;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableColumn<?, ?> colStudentName;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContactNo;

    @FXML
    private TableColumn<?, ?> colKeyMoney;

    @FXML
    private TableColumn<?, ?> colResId;

    @FXML
    private TableColumn<?, ?> colRoomId;

    private ObservableList<NonPayedStudentsTM> list;

    private FindBO findBO = (FindBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.FIND);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCellValueFactories();
        try {
            loadTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCellValueFactories() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("keyMoney"));
        colResId.setCellValueFactory(new PropertyValueFactory<>("resId"));
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
    }

    private void loadTable() throws Exception {
        list = FXCollections.observableArrayList();

        List<Object[]> nonPayedStudents = findBO.getNonPayedStudents();
        for (Object[] student : nonPayedStudents) {
            list.add(new NonPayedStudentsTM(
                    (String)student[0],
                    (String)student[1],
                    (String)student[2],
                    (String)student[3],
                    (Double) student[4],
                    (String)student[5],
                    (String)student[6]
            ));
        }

        tblNonPayedStudents.setItems(list);
    }
}

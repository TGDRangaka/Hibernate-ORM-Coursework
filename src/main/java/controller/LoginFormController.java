package controller;

import bo.BOFactory;
import bo.custom.LoginBO;
import com.jfoenix.controls.*;
import dto.UserDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    public JFXPasswordField pfPassword;
    public JFXCheckBox cbShowPassword;
    public JFXTextArea taSignUpDescription;
    @FXML
    private JFXTextField tfUsername;

    @FXML
    private JFXTextField tfPassword;

    @FXML
    private JFXButton btnSignIn;

    private LoginBO loginBO = (LoginBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.LOGIN);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fieldsOnActions();
    }

    @FXML
    void btnSignInOnAction(ActionEvent event) {
        if(btnSignIn.getText().equals("Sign In")){

            isValidInputs();

            try {
                boolean isVerifiedUser = loginBO.userVerify(tfUsername.getText(), tfPassword.getText());
                if (isVerifiedUser){
                    loadMainWindow();
                }else {
                    new Alert(Alert.AlertType.WARNING, "Login informations are wrong...!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Opss.. Something went wrong!!!").show();
            }
        }else{
            UserDTO userDTO = new UserDTO(null, tfUsername.getText(), tfPassword.getText());

            try {
                boolean isUserSaved = loginBO.addUser(userDTO);
                if (isUserSaved){
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Opss.. Something went wrong!!!").show();
            }

            btnSignIn.setText("Sign In");
        }

        taSignUpDescription.setVisible(false);
        tfUsername.setText(null);
        tfPassword.setText(null);
    }

    private void isValidInputs() {
        // todo
    }

    private void loadMainWindow() throws IOException {
        Stage window = (Stage) btnSignIn.getScene().getWindow();
        window.close();

        Stage stage = new Stage();
        stage.setScene(new Scene(new FXMLLoader(getClass().getResource("/view/main_window_form.fxml")).load()));
        stage.setTitle("D24 Hostel System");
        stage.show();
    }

    @FXML
    void lblSignUpOnMouseClicked(MouseEvent event) {
        taSignUpDescription.setVisible(true);
        btnSignIn.setText("Sign Up");
    }


    private void fieldsOnActions() {
        tfUsername.setOnAction((e)-> {
            pfPassword.requestFocus();
        });

        tfPassword.setOnAction((e) -> {
            btnSignIn.fire();
        });

        pfPassword.setOnAction((e) -> {
            btnSignIn.fire();
        });

        pfPassword.textProperty().bindBidirectional(tfPassword.textProperty());
    }

    public void cbShowPasswordOnAction(ActionEvent event) {
        if (cbShowPassword.isSelected()){
            pfPassword.setVisible(false);
        }else {
            pfPassword.setVisible(true);
        }

    }
}

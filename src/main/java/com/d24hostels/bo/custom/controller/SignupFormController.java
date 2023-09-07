package com.d24hostels.bo.custom.controller;



import com.d24hostels.bo.BOFactory;
import com.d24hostels.bo.custom.UserBo;
import com.d24hostels.dto.*;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;

public class SignupFormController {
    public AnchorPane root;
    public TextField txtUserName;
    public TextField txtPassword;
    public Button btnCreateAccount;
    public Button btnAlreadyHaveAnAccount;
    public JFXCheckBox checkBxShowPwd;
    public PasswordField pwdFld;
    UserBo userBo= (UserBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    public void txtUserNameOnAction(ActionEvent actionEvent) {
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) {
    }

    public void btnCreateAccountOnAction(ActionEvent actionEvent) {
        try {
            userBo.saveUser(new UserDto(txtUserName.getText(),txtPassword.getText()));
            new Alert(Alert.AlertType.INFORMATION, "Create Account Successfully!").showAndWait();
            resetPage();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            e.printStackTrace();
        }
    }

    private void resetPage() {
        txtUserName.clear();
        txtPassword.clear();
        pwdFld.clear();
    }

    public void btnAlreadyHaveAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml")));
    }

    public void checkBxShowPwdOnAction(ActionEvent actionEvent) {
        if (checkBxShowPwd.isSelected()){
            String pwd=pwdFld.getText();
            pwdFld.setVisible(false);
            txtPassword.setVisible(true);
            txtPassword.setText(pwd);
        }else{
            String pwd=txtPassword.getText();
            pwdFld.setVisible(true);
            txtPassword.setVisible(false);
            pwdFld.setText(pwd);
        }
    }
}


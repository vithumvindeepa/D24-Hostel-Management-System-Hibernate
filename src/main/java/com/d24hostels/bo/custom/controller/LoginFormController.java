package com.d24hostels.bo.custom.controller;



import com.d24hostels.bo.BOFactory;
import com.d24hostels.bo.custom.UserBo;
import com.d24hostels.dto.UserDto;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {
    public AnchorPane root;
    public TextField txtUserName;
    public TextField txtPassword;
    public Button btnLogin;
    public Button btnSignup;
    public JFXCheckBox checkBxShowPwd;
    public PasswordField pwdFld;
    UserBo userBo= (UserBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void txtUserNameOnAction(ActionEvent actionEvent) {
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) {
    }

    public void btnLoginOnAction(ActionEvent actionEvent) {
        try {
            UserDto userDto = userBo.searchUser(txtUserName.getText());
            if (userDto.getPassword().equals(pwdFld.getText())){
                new Alert(Alert.AlertType.INFORMATION, "Login Successfully!").showAndWait();
                resetPage();
            }else{
                new Alert(Alert.AlertType.INFORMATION, "Password is incorrect!").showAndWait();
            }
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

    public void btnSignupOnAction(ActionEvent actionEvent) throws IOException {
        root.getChildren().clear();
        root.getChildren().add(FXMLLoader.load(getClass().getResource("/view/SignupForm.fxml")));
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


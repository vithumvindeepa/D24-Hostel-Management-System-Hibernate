package com.d24hostels.bo.custom.controller;


import com.d24hostels.bo.BOFactory;
import com.d24hostels.bo.custom.UserBo;
import com.d24hostels.dto.UserDto;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AccountFormController implements Initializable {
    public AnchorPane context;
    public Button btnUpdate;
    public JFXTextField txtOldUserName;
    public JFXTextField txtOldPwd;
    public JFXTextField txtNewPwd;
    public JFXPasswordField pwdFldNewPwd;
    public JFXTextField txtComNewPwd;
    public JFXPasswordField pwdFldComPwd;
    public JFXTextField txtNewUserName;
    public JFXCheckBox checkBxShowPwd;
    public JFXPasswordField pwdFldOldPwd;
    UserBo userBo= (UserBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private void resetPage() {
        txtOldUserName.clear();
        txtNewUserName.clear();
        txtOldPwd.clear();
        txtNewPwd.clear();
        txtComNewPwd.clear();
    }

    public void checkBxShowPwdOnAction(ActionEvent actionEvent) {
        if (checkBxShowPwd.isSelected()){
            String old=pwdFldOldPwd.getText();
            pwdFldOldPwd.setVisible(false);
            txtOldPwd.setVisible(true);
            txtOldPwd.setText(old);
            String newPwd=pwdFldNewPwd.getText();
            pwdFldNewPwd.setVisible(false);
            txtNewPwd.setVisible(true);
            txtNewPwd.setText(newPwd);
            String com=pwdFldComPwd.getText();
            pwdFldComPwd.setVisible(false);
            txtComNewPwd.setVisible(true);
            txtComNewPwd.setText(com);
        }else{
            String old=txtOldPwd.getText();
            pwdFldOldPwd.setVisible(true);
            txtOldPwd.setVisible(false);
            pwdFldOldPwd.setText(old);
            String newPwd=txtNewPwd.getText();
            pwdFldNewPwd.setVisible(true);
            txtNewPwd.setVisible(false);
            pwdFldNewPwd.setText(newPwd);
            String com=txtComNewPwd.getText();
            pwdFldComPwd.setVisible(true);
            txtComNewPwd.setVisible(false);
            pwdFldComPwd.setText(com);
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            if (txtNewPwd.getText().equals(txtComNewPwd.getText())){
                UserDto userDto = userBo.searchUser(txtOldUserName.getText());
                if (userDto.getPassword().equals(txtOldPwd.getText())){
                    userDto.setUsername(txtNewUserName.getText());
                    userDto.setPassword(txtNewPwd.getText());
                    userBo.updateUser(userDto);
                    new Alert(Alert.AlertType.INFORMATION, "Update Successfully!").showAndWait();
                    resetPage();
                }else{
                    new Alert(Alert.AlertType.INFORMATION, "Password is incorrect!").showAndWait();
                }
            }else{
                new Alert(Alert.AlertType.INFORMATION, "Doesn't match Confirmation and new password!").showAndWait();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            e.printStackTrace();
        }
    }
}


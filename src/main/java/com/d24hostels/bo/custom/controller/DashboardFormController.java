package com.d24hostels.bo.custom.controller;


import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {
    public AnchorPane root;
    public Label lblTitle;
    public AnchorPane context;
    public JFXButton btnHome;
    public JFXButton btnStudent;
    public JFXButton btnRooms;
    public JFXButton btnPayments;
    public JFXButton btnAccessories;
    public JFXButton btnAccount;
    public Label lblDate;
    public Label lblTime;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        context.getChildren().clear();
        context.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomeForm.fxml")));
        loadDateAndTime();
    }

    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        lblTitle.setText("Home");
        context.getChildren().clear();
        context.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomeForm.fxml")));
    }

    public void btnStudentOnAction(ActionEvent actionEvent) throws IOException {
        lblTitle.setText("Students");
        context.getChildren().clear();
        context.getChildren().add(FXMLLoader.load(getClass().getResource("/view/StudentForm.fxml")));
    }

    public void btnRoomsOnAction(ActionEvent actionEvent) throws IOException {
        lblTitle.setText("Rooms");
        context.getChildren().clear();
        context.getChildren().add(FXMLLoader.load(getClass().getResource("/view/RoomForm.fxml")));
    }

    public void btnPaymentsOnAction(ActionEvent actionEvent) throws IOException {
        lblTitle.setText("Reservations");
        context.getChildren().clear();
        context.getChildren().add(FXMLLoader.load(getClass().getResource("/view/ReservationForm.fxml")));
    }

    public void btnAccessoriesOnAction(ActionEvent actionEvent) throws IOException {
        lblTitle.setText("Accessories");
        context.getChildren().clear();
        context.getChildren().add(FXMLLoader.load(getClass().getResource("/view/AccessoriesForm.fxml")));
    }

    public void btnAccountOnAction(ActionEvent actionEvent) throws IOException {
        lblTitle.setText("Account");
        context.getChildren().clear();
        context.getChildren().add(FXMLLoader.load(getClass().getResource("/view/AccountForm.fxml")));
    }
    private void loadDateAndTime() {
        lblDate.setText(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
        Timeline clock=new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime=LocalTime.now();
            lblTime.setText(currentTime.getHour()+" : "+ currentTime.getMinute()+" : "+ currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}


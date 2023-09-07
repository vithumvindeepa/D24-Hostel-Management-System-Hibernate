package com.d24hostels.bo.custom.controller;



import com.d24hostels.bo.BOFactory;
import com.d24hostels.bo.custom.ReservationBo;
import com.d24hostels.bo.custom.PriceBo;
import com.d24hostels.bo.custom.RoomBo;
import com.d24hostels.bo.custom.StudentBo;
import com.d24hostels.dto.*;
import com.d24hostels.dto.tm.ReservationTM;
import com.d24hostels.dto.tm.StudentTM;
import com.d24hostels.util.RegExPatterns;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationFormController implements Initializable {
    public JFXComboBox cmbSId;
    public TableView<ReservationTM> tblPayment;
    public TableColumn colPayId;
    public TableColumn colStudentId;
    public TableColumn colStudentName;
    public TableColumn colPaidDateTime;
    public TableColumn colPaidAmount;
    public TableColumn colRoomNo;
    public TableColumn colRoomType;
    public TableColumn colCompleted;
    public JFXComboBox cmbRoomType;
    public JFXComboBox cmbSelectedRoom;
    public JFXRadioButton rBtnPaid;
    public ToggleGroup keymoney;
    public JFXRadioButton rBtnNotPaid;
    public JFXTextField txtAmount;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    ReservationBo reservationBo= (ReservationBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVATION);
    PriceBo priceBo= (PriceBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRICE);
    RoomBo roomBo= (RoomBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOMS);
    StudentBo studentBo= (StudentBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);
    ObservableList<ReservationTM> observableList= FXCollections.observableArrayList();
    ObservableList<String> obList= FXCollections.observableArrayList();
    String payId="";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAllStudents();
        getAllRoomTypes();
        getAllReservations();
        setCellValueFactory();

        tblPayment.getSelectionModel().selectedItemProperty()
                .addListener((observableValue, oldValue, newValue) -> {
                    if(newValue!=null){
                        setData(newValue);
                    }
                });
    }

    private void setData(ReservationTM newValue) {
        payId= newValue.getPayId();
        cmbSId.setValue(newValue.getStId());
        cmbRoomType.setValue(newValue.getRoomType());
        cmbSelectedRoom.setValue(newValue.getRoomNo());
        if (newValue.getKeyMoneyCompleted().equals("Completed")){
            rBtnPaid.setSelected(true);
        }else{
            rBtnNotPaid.setSelected(true);
        }
        txtAmount.setText(newValue.getPayAmount());
    }

    private void getAllStudents() {
        try {
            obList.clear();
            List<StudentDto> studentDtos = studentBo.getAllStudents();
            if (studentDtos!=null){
                for (StudentDto studentDto : studentDtos) {
                    obList.add(
                            studentDto.getSid()
                    );
                }
                cmbSId.setItems(obList);
            }else{
                new Alert(Alert.AlertType.INFORMATION, "Students not found!").showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAllRoomTypes() {
        ObservableList<String> observableList= FXCollections.observableArrayList();
        try {
            List<PriceDto> priceDtos=priceBo.getAllPrices();
            for (PriceDto priceDto : priceDtos) {
                observableList.add(String.valueOf(priceDto.getRoomType()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            e.printStackTrace();
        }
        cmbRoomType.setItems(observableList);
    }

    private void setCellValueFactory() {
        colPayId.setCellValueFactory(new PropertyValueFactory<>("payId"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("stId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("stName"));
        colPaidDateTime.setCellValueFactory(new PropertyValueFactory<>("payDateTime"));
        colPaidAmount.setCellValueFactory(new PropertyValueFactory<>("payAmount"));
        colRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colCompleted.setCellValueFactory(new PropertyValueFactory<>("keyMoneyCompleted"));
    }

    private void getAllReservations() {
        try {
            observableList.clear();
            List<ReservationDto> allReservations = reservationBo.getAllReservations();
            if (allReservations!=null){
                for (ReservationDto reservationDto : allReservations) {
                    observableList.add(
                            new ReservationTM(
                                    String.valueOf(reservationDto.getPayId()),
                                    reservationDto.getStudentDto().getSid(),
                                    reservationDto.getStudentDto().getName(),
                                    String.valueOf(reservationDto.getPaidDate()),
                                    String.valueOf(reservationDto.getAmount()),
                                    reservationDto.getRoomDto().getRoomNo(),
                                    reservationDto.getRoomDto().getPriceDto().getRoomType(),
                                    reservationDto.isStatus()?"Complete":"Not yet"
                            )
                    );
                }
                tblPayment.setItems(observableList);
            }else{
                new Alert(Alert.AlertType.INFORMATION, "Payments not found!").showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cmbSIdOnAction(ActionEvent actionEvent) {
    }

    public void cmbRoomTypeOnAction(ActionEvent actionEvent) {
        ObservableList<String> roomNoList=FXCollections.observableArrayList();
        try {
            List<RoomDto> availableRooms = roomBo.getAvailableRooms();
            for (RoomDto availableRoom : availableRooms) {
                if (availableRoom.getPriceDto().getRoomType().equals(cmbRoomType.getSelectionModel().getSelectedItem())){
                    roomNoList.add(availableRoom.getRoomNo());
                }else{
                    continue;
                }
            }
            if (roomNoList.isEmpty()){
                new Alert(Alert.AlertType.INFORMATION, "No Available rooms in selected Type!").showAndWait();
            }else{
                cmbSelectedRoom.setItems(roomNoList);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            e.printStackTrace();
        }
    }

    public void cmbSelectedRoomOnAction(ActionEvent actionEvent) {
    }

    public void txtAmountOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        try {
            if (isCorrectPattern()){
                StudentDto studentDto = studentBo.searchStudents(String.valueOf(cmbSId.getSelectionModel().getSelectedItem()));
                RoomDto roomDto = roomBo.searchRoom(String.valueOf(cmbSelectedRoom.getSelectionModel().getSelectedItem()));
                roomDto.setAvailability("Filled");
                roomBo.updateRoom(roomDto);
                reservationBo.saveReservation(new ReservationDto(
                        0,
                        LocalDate.now(),
                        Double.parseDouble(txtAmount.getText()),
                        rBtnPaid.isSelected()?true:false,
                        roomDto,
                        studentDto
                ));
                new Alert(Alert.AlertType.INFORMATION, "Reservation Saved Successfully!").showAndWait();
                resetPage();
            }else{
                new Alert(Alert.AlertType.INFORMATION, "Recheck provide details!").showAndWait();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            e.printStackTrace();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            if (isCorrectPattern()){
                StudentDto studentDto = studentBo.searchStudents(String.valueOf(cmbSId.getSelectionModel().getSelectedItem()));
                RoomDto roomDto = roomBo.searchRoom(String.valueOf(cmbSelectedRoom.getSelectionModel().getSelectedItem()));
                roomDto.setAvailability("Filled");
                roomBo.updateRoom(roomDto);
                reservationBo.updateReservation(new ReservationDto(
                        0,
                        LocalDate.now(),
                        Double.parseDouble(txtAmount.getText()),
                        rBtnPaid.isSelected()?true:false,
                        roomDto,
                        studentDto
                ));
                new Alert(Alert.AlertType.INFORMATION, "Reservation Update Successfully!").showAndWait();
                resetPage();
            }else{
                new Alert(Alert.AlertType.INFORMATION, "Recheck provide details!").showAndWait();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            e.printStackTrace();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            RoomDto roomDto = roomBo.searchRoom(String.valueOf(cmbSelectedRoom.getSelectionModel().getSelectedItem()));
            roomDto.setAvailability("Available");
            roomBo.updateRoom(roomDto);
            reservationBo.deleteReservation(payId);
            new Alert(Alert.AlertType.INFORMATION, "Reservation Deleted Successfully!").showAndWait();
            resetPage();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            e.printStackTrace();
        }
    }

    private void resetPage() {
        cmbSId.setValue("Search Student");
        cmbRoomType.setValue("Room Type");
        cmbSelectedRoom.setValue("Selected Room");
        txtAmount.clear();
        getAllReservations();
        setCellValueFactory();
    }
    private boolean isCorrectPattern(){
        if ((RegExPatterns.getDoublePattern().matcher(txtAmount.getText()).matches())){
            return true;
        }
        return false;
    }
}

package com.d24hostels.bo.custom.controller;



import com.d24hostels.bo.BOFactory;
import com.d24hostels.bo.custom.PriceBo;
import com.d24hostels.bo.custom.RoomBo;
import com.d24hostels.dto.PriceDto;
import com.d24hostels.dto.RoomDto;
import com.d24hostels.dto.tm.RoomTM;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class RoomFormController implements Initializable {
    public RadioButton rBtnAvailable;
    public ToggleGroup rooms;
    public RadioButton rBtnNotAvailable;
    public RadioButton rBtnAll;
    public TableView tblRoom;
    public TableColumn colRoomNo;
    public TableColumn colRoomId;
    public TableColumn colRoomType;
    public TableColumn colKeyMoney;
    public TableColumn colAvailability;
    public JFXTextField txtRoomNo;
    public JFXTextField txtKeyMoney;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public JFXComboBox cmbRoomTypeId;
    public JFXTextField txtRoomType;
    RoomBo roomBo= (RoomBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOMS);
    PriceBo priceBo= (PriceBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRICE);
    ObservableList<RoomTM> observableList=FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> observableList= FXCollections.observableArrayList();
        List<String> strings=new ArrayList<>();
        List<PriceDto> priceDtos=new ArrayList<>();

        try {
            priceDtos=priceBo.getAllPrices();
            for (PriceDto priceDto : priceDtos) {
                strings.add(String.valueOf(priceDto.getTypeId()));
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            e.printStackTrace();
        }
        for (String s:strings) {
            observableList.add(s);
        }
        cmbRoomTypeId.setItems(observableList);


        getAvailableRooms();
        setCellValueFactory();
    }

    private void getAvailableRooms() {
        try {
            observableList.clear();
            List<RoomDto> availableRooms = roomBo.getAvailableRooms();
            if (availableRooms!=null){
                for (RoomDto availableRoom : availableRooms) {
                    observableList.add(
                            new RoomTM(
                                    availableRoom.getRoomNo(),
                                    availableRoom.getPriceDto().getTypeId(),
                                    availableRoom.getPriceDto().getRoomType(),
                                    String.valueOf(availableRoom.getPriceDto().getPrice()),
                                    availableRoom.getAvailability()
                            )
                    );
                }
                tblRoom.setItems(observableList);
            }else{
                new Alert(Alert.AlertType.INFORMATION, "Rooms not found!").showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rBtnAvailableOnAction(ActionEvent actionEvent) {
        getAvailableRooms();
    }

    void setCellValueFactory() {
        colRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("TypeID"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("keyMoney"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
    }

    public void rBtnNotAvailableOnAction(ActionEvent actionEvent) {
        try {
            observableList.clear();
            List<RoomDto> filled = roomBo.getFilledRooms();
            if (filled!=null){
                for (RoomDto filledRoom : filled) {
                    observableList.add(
                            new RoomTM(
                                    filledRoom.getRoomNo(),
                                    filledRoom.getPriceDto().getTypeId(),
                                    filledRoom.getPriceDto().getRoomType(),
                                    String.valueOf(filledRoom.getPriceDto().getPrice()),
                                    filledRoom.getAvailability()
                            )
                    );
                }
                tblRoom.setItems(observableList);
            }else{
                new Alert(Alert.AlertType.INFORMATION, "Rooms not found!").showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rBtnAllOnAction(ActionEvent actionEvent) {
        try {
            observableList.clear();
            List<RoomDto> allRooms = roomBo.getAllRooms();
            if (allRooms!=null){
                for (RoomDto all : allRooms) {
                    observableList.add(
                            new RoomTM(
                                    all.getRoomNo(),
                                    all.getPriceDto().getTypeId(),
                                    all.getPriceDto().getRoomType(),
                                    String.valueOf(all.getPriceDto().getPrice()),
                                    all.getAvailability()
                            )
                    );
                }
                tblRoom.setItems(observableList);
            }else{
                new Alert(Alert.AlertType.INFORMATION, "Rooms not found!").showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void txtRoomNoOnAction(ActionEvent actionEvent) {
        try {
            RoomDto roomDto = roomBo.searchRoom(txtRoomNo.getText());
            if (roomDto!=null) {
                cmbRoomTypeId.setValue(roomDto.getPriceDto().getTypeId());
                txtRoomType.setText(roomDto.getPriceDto().getRoomType());
                txtKeyMoney.setText(String.valueOf(roomDto.getPriceDto().getPrice()));
            }else {
                new Alert(Alert.AlertType.INFORMATION, "Room Not Found!").showAndWait();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            e.printStackTrace();
        }
    }

    public void txtKeyMoneyOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        try {
            roomBo.saveRoom(new RoomDto(txtRoomNo.getText(),new PriceDto(String.valueOf(cmbRoomTypeId.getSelectionModel().getSelectedItem()),txtRoomType.getText(),Double.parseDouble(txtKeyMoney.getText())),"Available"));
            new Alert(Alert.AlertType.INFORMATION, "Room Saved Successfully!").showAndWait();
            resetPage();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            e.printStackTrace();
        }
    }

    private void resetPage() {
        txtRoomNo.clear();
        txtRoomType.clear();
        txtKeyMoney.clear();
        txtRoomType.setDisable(false);
        txtKeyMoney.setDisable(false);
        getAvailableRooms();
        setCellValueFactory();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            roomBo.updateRoom(new RoomDto(txtRoomNo.getText(),new PriceDto(String.valueOf(cmbRoomTypeId.getSelectionModel().getSelectedItem()),txtRoomType.getText(),Double.parseDouble(txtKeyMoney.getText()))));
            new Alert(Alert.AlertType.INFORMATION, "Room Update Successfully!").showAndWait();
            resetPage();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            e.printStackTrace();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Optional<ButtonType> comfirm=new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove the room?").showAndWait();
        if (comfirm.isPresent()){
            try {
                roomBo.deleteRoom(txtRoomNo.getText());
                new Alert(Alert.AlertType.INFORMATION, "Room Deleted Successfully!").showAndWait();
                resetPage();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
                e.printStackTrace();
            }
        }
    }

    public void cmbRoomTypeIdOnAction(ActionEvent actionEvent) {
        try {
            PriceDto priceDto = priceBo.searchPrice(String.valueOf(cmbRoomTypeId.getSelectionModel().getSelectedItem()));
            txtRoomType.setText(priceDto.getRoomType());
            txtKeyMoney.setText(String.valueOf(priceDto.getPrice()));
            txtRoomType.setDisable(true);
            txtKeyMoney.setDisable(true);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            e.printStackTrace();
        }
    }

    public void txtRoomTypeOnAction(ActionEvent actionEvent) {
    }
}


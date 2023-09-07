package com.d24hostels.bo.custom.controller;



import com.d24hostels.bo.BOFactory;
import com.d24hostels.bo.custom.RoomBo;
import com.d24hostels.bo.custom.StudentBo;
import com.d24hostels.dto.RoomDto;
import com.d24hostels.dto.StudentDto;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeFormController implements Initializable {
    public AnchorPane context;
    public Label lblStudentCount;
    public Label lblRoomCount;
    public Label lblEmptyCount;
    public Label lblEmptyNoneAcCount;
    public Label lblEmptyNoneAcFoodCount;
    public Label lblEmptyAcCount;
    public Label lblEmptyAcFoodCount;
    RoomBo roomBo= (RoomBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOMS);
    StudentBo studentBo= (StudentBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<RoomDto> availableRooms = roomBo.getAvailableRooms();
            List<RoomDto> allRooms = roomBo.getAllRooms();
            List<StudentDto> allStudents = studentBo.getAllStudents();
            int emptyCount=availableRooms.size();
            int allRoom= allRooms.size();
            int allStu= allStudents.size();

            int emptyNoneAC=0;
            int emptyNoneACFood=0;
            int emptyAC=0;
            int emptyACFood=0;
            for (RoomDto available:availableRooms) {
                if (available.getPriceDto().getRoomType().equals("Non-AC")){
                    emptyNoneAC+=1;
                }else if (available.getPriceDto().getRoomType().equals("Non-AC/Food")){
                    emptyNoneACFood+=1;
                }else if (available.getPriceDto().getRoomType().equals("AC")){
                    emptyAC+=1;
                }else if (available.getPriceDto().getRoomType().equals("AC/Food")){
                    emptyACFood+=1;
                }
            }

            lblStudentCount.setText(String.valueOf(allStu));
            lblRoomCount.setText(String.valueOf(allRoom));
            lblEmptyCount.setText(String.valueOf(emptyCount));
            lblEmptyNoneAcCount.setText(String.valueOf(emptyNoneAC));
            lblEmptyNoneAcFoodCount.setText(String.valueOf(emptyNoneACFood));
            lblEmptyAcCount.setText(String.valueOf(emptyAC));
            lblEmptyAcFoodCount.setText(String.valueOf(emptyACFood));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

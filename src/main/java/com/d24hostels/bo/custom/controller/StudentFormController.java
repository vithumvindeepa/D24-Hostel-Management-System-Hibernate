package com.d24hostels.bo.custom.controller;



import com.d24hostels.bo.BOFactory;
import com.d24hostels.bo.custom.*;
import com.d24hostels.dto.*;
import com.d24hostels.dto.tm.RoomTM;
import com.d24hostels.dto.tm.StudentTM;
import com.d24hostels.util.RegExPatterns;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class StudentFormController implements Initializable {
    public AnchorPane context;
    public JFXTextField txtSid;
    public JFXTextField txtSName;
    public JFXTextField txtSNic;
    public JFXTextField txtGuardianName;
    public JFXTextField txtContact;
    public JFXTextField txtEmail;
    public JFXComboBox cmbUniversity;
    public JFXRadioButton rBtnMale;
    public ToggleGroup gender;
    public JFXRadioButton rBtnFeMale;
    public TableView tblStudent;
    public TableColumn colSid;
    public TableColumn colSName;
    public TableColumn colNic;
    public TableColumn colContact;
    public TableColumn colEmail;
    public TableColumn colGender;
    public TableColumn colUniversity;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    StudentBo studentBo= (StudentBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);
    UniversityBo universityBo= (UniversityBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.UNIVERSITY);
    ObservableList<StudentTM> observableList=FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<String> uniList= FXCollections.observableArrayList();
            List<UniversityDto> allUniversities = universityBo.getAllUniversities();
            for (UniversityDto allUniversity : allUniversities) {
                uniList.add(allUniversity.getUniName());
            }
            cmbUniversity.setItems(uniList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            e.printStackTrace();
        }

        getAllStudents();
        setCellValueFactory();
    }

    private void getAllStudents() {
        try {
            observableList.clear();
            List<StudentDto> studentDtos = studentBo.getAllStudents();
            if (studentDtos!=null){
                for (StudentDto studentDto : studentDtos) {
                    observableList.add(
                            new StudentTM(
                                    studentDto.getSid(),
                                    studentDto.getName(),
                                    studentDto.getNic(),
                                    studentDto.getContact(),
                                    studentDto.getEmail(),
                                    studentDto.getGender(),
                                    studentDto.getUniversityDto().getUniName()
                            )
                    );
                }
                System.out.println("stu: "+observableList.size());
                tblStudent.setItems(observableList);
            }else{
                new Alert(Alert.AlertType.INFORMATION, "Students not found!").showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colSid.setCellValueFactory(new PropertyValueFactory<>("sId"));
        colSName.setCellValueFactory(new PropertyValueFactory<>("sName"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("sNic"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colUniversity.setCellValueFactory(new PropertyValueFactory<>("university"));
    }

    public void txtSidOnAction(ActionEvent actionEvent) {
        try {
            StudentDto studentDto = studentBo.searchStudents(txtSid.getText());
            if (studentDto!=null) {
                txtSName.setText(studentDto.getName());
                txtSNic.setText(studentDto.getNic());
                txtGuardianName.setText(studentDto.getGuardian());
                txtContact.setText(studentDto.getContact());
                txtEmail.setText(studentDto.getEmail());
                if (studentDto.getGender().equals("Male")) {
                    rBtnMale.setSelected(true);
                } else {
                    rBtnFeMale.setSelected(true);
                }
                cmbUniversity.setValue(studentDto.getUniversityDto().getUniName());
            }else {
                new Alert(Alert.AlertType.INFORMATION, "Student Not Found!").showAndWait();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            e.printStackTrace();
        }
    }

    public void txtSNameOnAction(ActionEvent actionEvent) {
    }

    public void txtEmailOnAction(ActionEvent actionEvent) {
    }

    public void txtCantactOnAction(ActionEvent actionEvent) {
    }

    public void txtGuardianNameOnAction(ActionEvent actionEvent) {
    }

    public void txtSNicOnAction(ActionEvent actionEvent) {
    }

    public void cmbUniversityOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Optional<ButtonType> comfirm=new Alert(Alert.AlertType.CONFIRMATION, "Do you want to remove the student?").showAndWait();
        if (comfirm.isPresent()){
            try {
                studentBo.deleteStudent(txtSid.getText());
                new Alert(Alert.AlertType.INFORMATION, "Student Deleted Successfully!").showAndWait();
                resetPage();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
                e.printStackTrace();
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            if (isCorrectPattern()){
                studentBo.updateStudent(new StudentDto(txtSid.getText(),txtSName.getText(),txtSNic.getText(),
                        rBtnMale.isSelected()? "Male":"Female",txtGuardianName.getText(),txtContact.getText(),txtEmail.getText(), LocalDate.now(),
                        new UniversityDto(String.valueOf(cmbUniversity.getSelectionModel().getSelectedItem()))));
                new Alert(Alert.AlertType.INFORMATION, "Student Updated Successfully!").showAndWait();
            }else{
                new Alert(Alert.AlertType.INFORMATION, "Recheck provide details!").showAndWait();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            e.printStackTrace();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        try {
            if (isCorrectPattern()){
                studentBo.saveStudent(new StudentDto(txtSid.getText(),txtSName.getText(),txtSNic.getText(),
                        rBtnMale.isSelected()? "Male":"Female",txtGuardianName.getText(),txtContact.getText(),txtEmail.getText(), LocalDate.now(),
                        new UniversityDto(String.valueOf(cmbUniversity.getSelectionModel().getSelectedItem()))));
                new Alert(Alert.AlertType.INFORMATION, "Student Saved Successfully!").showAndWait();
                resetPage();
            }else{
                new Alert(Alert.AlertType.INFORMATION, "Recheck provide details!").showAndWait();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            e.printStackTrace();
        }
    }

    private void resetPage() {
        txtSName.clear();
        txtSNic.clear();
        txtGuardianName.clear();
        txtContact.clear();
        txtEmail.clear();
        rBtnMale.setSelected(true);
        rBtnFeMale.setSelected(false);
        getAllStudents();
        setCellValueFactory();
    }

    private boolean isCorrectPattern(){
        if ((RegExPatterns.getEmailPattern().matcher(txtEmail.getText()).matches()) &&
                RegExPatterns.getNamePattern().matcher(txtSName.getText()).matches() &&
                RegExPatterns.getMobilePattern().matcher(txtContact.getText()).matches() &&
                (RegExPatterns.getIdPattern().matcher(txtSNic.getText()).matches() ||
                        RegExPatterns.getOldIDPattern().matcher(txtSNic.getText()).matches() ) &&
                RegExPatterns.getNamePattern().matcher(txtGuardianName.getText()).matches()){
            return true;
        }
        return false;
    }
}

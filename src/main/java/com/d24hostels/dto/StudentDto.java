package com.d24hostels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentDto {
    private String sid;
    private String name;
    private String nic;
    private String gender;
    private String guardian;
    private String contact;
    private String email;
    private LocalDate regDate;
    private UniversityDto universityDto;
    private List<ReservationDto> reservationDtos;

    public StudentDto(String sid, String name) {
        this.sid = sid;
        this.name = name;
    }

    public StudentDto(String sid, String name, String nic, String gender, String guardian, String contact, String email, LocalDate regDate, UniversityDto universityDto) {
        this.sid = sid;
        this.name = name;
        this.nic = nic;
        this.gender = gender;
        this.guardian = guardian;
        this.contact = contact;
        this.email = email;
        this.regDate = regDate;
        this.universityDto = universityDto;
    }
}

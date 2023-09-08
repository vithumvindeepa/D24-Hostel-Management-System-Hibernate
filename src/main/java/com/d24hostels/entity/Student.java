package com.d24hostels.entity;

import jakarta.persistence.*;
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
@Entity
public class Student {
    @Id
    private String sid;
    private String name;
    private String nic;
    private String gender;
    private String guardian;
    private String contact;
    private String email;
    private LocalDate regDate;
    @OneToOne(cascade = CascadeType.MERGE)
    private University university;
    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reservation> reservations;

    public Student(String sid, String name, String nic, String gender, String guardian, String contact, String email, LocalDate regDate, University university) {
        this.sid = sid;
        this.name = name;
        this.nic = nic;
        this.gender = gender;
        this.guardian = guardian;
        this.contact = contact;
        this.email = email;
        this.regDate = regDate;
        this.university = university;
    }
}

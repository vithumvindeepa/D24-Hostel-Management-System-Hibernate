package com.d24hostels.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class StudentTM {
    private String sId;
    private String sName;
    private String sNic;
    private String contact;
    private String email;
    private String gender;
    private String university;
}

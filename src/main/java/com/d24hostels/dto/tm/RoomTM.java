package com.d24hostels.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RoomTM {
    private String roomNo;
    private String TypeID;
    private String roomType;
    private String keyMoney;
    private String availability;
}

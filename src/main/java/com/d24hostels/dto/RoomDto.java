package com.d24hostels.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomDto {
    private String roomNo;
    private String availability;
    private PriceDto priceDto;

    public RoomDto(String roomNo, PriceDto priceDto, String availability) {
        this.roomNo = roomNo;
        this.priceDto= priceDto;
        this.availability= availability;
    }
    public RoomDto(String roomNo, PriceDto priceDto) {
        this.roomNo = roomNo;
        this.priceDto= priceDto;
    }

    public RoomDto(String roomNo, String availability) {
        this.roomNo = roomNo;
        this.availability = availability;
    }

    public RoomDto(String roomNo) {
        this.roomNo = roomNo;
    }
}

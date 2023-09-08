package com.d24hostels.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Room {
    @Id
    private String roomNo;
    private String availability;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Price price;

    public Room(String roomNo, Price price, String availability) {
        this.roomNo = roomNo;
        this.price=price;
        this.availability= availability;
    }
    public Room(String roomNo, Price price) {
        this.roomNo = roomNo;
        this.price=price;
    }

    public Room(String roomNo, String availability) {
        this.roomNo = roomNo;
        this.availability = availability;
    }

    public Room(String roomNo) {
        this.roomNo = roomNo;
    }
}

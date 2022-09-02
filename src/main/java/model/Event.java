package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    private int id;
    private String name;

    private String place;
    private boolean isOnline;
    private double price;
    private EventType eventType;
    private Date eventDate;

    public Event(String name, String place, double price, EventType eventType) {
        this.name = name;
        this.place = place;
        this.price = price;
        this.eventType = eventType;
    }

}

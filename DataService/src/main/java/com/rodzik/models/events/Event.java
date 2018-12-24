package com.rodzik.models.events;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


import java.util.Date;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.AUTO;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long objectId;
    private String message;
    @Enumerated(STRING)
    private EventType eventType;
    private Date eventDate;

    public Long getObjectId() {
        return objectId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Event{" +
                "objectId=" + objectId +
                ", message='" + message + '\'' +
                '}';
    }
}


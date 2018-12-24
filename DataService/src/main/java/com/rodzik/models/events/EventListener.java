package com.rodzik.models.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class EventListener {
    private final EventRepository eventRepository;
    @Autowired
    public EventListener(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
    @JmsListener(destination = "events",
            containerFactory = "myFactory")
    public void receiveMessage(Event eventObject) {
        System.out.println("received "+eventObject);
        eventRepository.save(eventObject);
    }
}
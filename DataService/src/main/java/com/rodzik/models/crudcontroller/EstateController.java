package com.rodzik.models.crudcontroller;


import com.rodzik.models.domain.Estate;
import com.rodzik.models.domain.EstateRepository;
import com.rodzik.models.domain.EstateType;
import com.rodzik.models.events.Event;
import com.rodzik.models.events.EventType;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/estates")
public class EstateController {
    private final EstateRepository estateRepository;
    private final JmsTemplate jmsTemplate;
    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    public EstateController(EstateRepository estateRepository, JmsTemplate jmsTemplate) {

        this.estateRepository = estateRepository;
        this.jmsTemplate=jmsTemplate;
    }
    @GetMapping
    public List<Estate> getEstates()
    {
        Iterable<Estate> estateIterable = estateRepository.findAll();
        List<Estate> estates = Lists.newArrayList(estateIterable);
        return estates;
    }
    @GetMapping("/getbyid/{id}")
    public Estate findEstateById(@PathVariable long id)
    {
        Optional<Estate> estate = estateRepository.findById(id);
        if (estate.isPresent()) {
            Estate w=estate.get();
            return w;
        }
        return null;
    }
    @GetMapping("/flats")
    public ArrayList<Estate> findFlats()
    {
        ArrayList<Estate> result=new ArrayList<Estate>();
        List<Estate> e=getEstates();
        for(Estate d : e){
            if(d.getEstateType() != null && d.getEstateType().equals(EstateType.FLAT))
                result.add(d);

        }
        return result;
    }
    @GetMapping("/houses")
    public ArrayList<Estate> findHouses()
    {
        ArrayList<Estate> result=new ArrayList<Estate>();
        List<Estate> e=getEstates();
        for(Estate d : e){
            if(d.getEstateType() != null && d.getEstateType().equals(EstateType.HOUSE))
                result.add(d);

        }
        return result;
    }
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Estate addEstate(@RequestBody Estate estate)
    {
        Estate savedEstate = estateRepository.save(estate);
        Event e=new Event();
        e.setEventDate(new Date());
        e.setMessage("Saved estate with id "+estate.getId());
        e.setEventType(EventType.ESTATE_ADDED);
        jmsTemplate.convertAndSend("events", e);
        return savedEstate;
    }
    @DeleteMapping("/delete/{id}")
    public Boolean deleteEstateById(@PathVariable long id)
    {
        if(estateRepository.existsById(id)) {
            estateRepository.deleteById(id);
            Event e = new Event();
            e.setEventDate(new Date());
            e.setMessage("Deleted estate with id " + id);
            e.setEventType(EventType.ESTATE_DELETED);
            jmsTemplate.convertAndSend("events", e);
            return true;
        }
        return false;
    }

}

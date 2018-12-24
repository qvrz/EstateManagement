package com.rodzik;

import com.google.common.collect.Lists;
import com.rodzik.models.domain.Estate;
import com.rodzik.models.domain.EstateRepository;
import com.rodzik.models.domain.User;
import com.rodzik.models.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sales")
public class SalesController {
    private final UserRepository userRepository;
    private final EstateRepository estateRepository;
    private final ContractRepository contractRepository;
    private final JmsTemplate jmsTemplate;
    @Autowired
    public SalesController(UserRepository userRepository, EstateRepository estateRepository, JmsTemplate jmsTemplate, ContractRepository contractRepository) {
        this.userRepository = userRepository;
        this.estateRepository = estateRepository;
        this.jmsTemplate=jmsTemplate;
        this.contractRepository=contractRepository;
    }
    public void pushContract(Long userID, Long estateID, BigDecimal price)
    {
        Contract c=new Contract();
        c.setUserID(userID);
        c.setEstateID(estateID);
        c.setPrice(price);
        c.setContractDate(new Date());
        jmsTemplate.convertAndSend("contracts", c);
    }
    @PostMapping("{eid}/sellEstate/{uid}")
    public Boolean sellEstateToUser(@PathVariable long uid, @PathVariable long eid)
    {
        Optional<User> u=userRepository.findById(uid);
        Optional<Estate> e=estateRepository.findById(eid);
        if (u.isPresent() && e.isPresent()) {
            User user = u.get();
            Estate estate=e.get();
            user.getEstates().add(estate);
            if(user.getFunds().compareTo(estate.getPrice()) >= 0) {
                user.setFunds(user.getFunds().subtract(estate.getPrice()));
                estate.setUser(user);
                estateRepository.save(estate);
                userRepository.save(user);
                pushContract(user.getId(),estate.getId(),estate.getPrice());
                return true;
            } else return false;
        }
        return false;
    }
    @GetMapping("/contracts")
    public List<Contract> showContracts()
    {
        Iterable<Contract> contractIterable = contractRepository.findAll();
        List<Contract> contracts = Lists.newArrayList(contractIterable);
        return contracts;
    }
}

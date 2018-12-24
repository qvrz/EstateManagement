package com.rodzik;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ContractListener {
    private final ContractRepository contractRepository;
    @Autowired
    public ContractListener(ContractRepository eventRepository) {
        this.contractRepository = eventRepository;
    }
    @JmsListener(destination = "contracts",
            containerFactory = "myContractContainerFactory")
    public void receiveMessage(Contract contractObject) {
        System.out.println("Contract received "+contractObject);
        contractRepository.save(contractObject);
    }
}
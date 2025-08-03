package com.arfan.controller;

import com.arfan.dto.Employee;
import com.arfan.producer.KafkaAvroProducer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    private final KafkaAvroProducer producer;


    public EventController(KafkaAvroProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/events")
    public String sendMessage(@RequestBody Employee employee){
        producer.send(employee);
        return "Message published";
    }
}

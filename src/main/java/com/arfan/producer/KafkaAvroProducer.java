package com.arfan.producer;

import com.arfan.dto.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class KafkaAvroProducer {

    private final KafkaTemplate<String, Employee> template;

    @Value("${topic.name}")
    private String topicName;

    public KafkaAvroProducer(KafkaTemplate<String, Employee> template) {
        this.template = template;
    }

    public void send(Employee employee){
        CompletableFuture<SendResult<String, Employee>> future = template.send(topicName, UUID.randomUUID().toString(), employee);
        future.whenComplete((result, ex) -> {
            if(ex == null){
                System.out.println("Sent message = [" +employee+ "] with offset=["+result.getRecordMetadata()+"]");
            } else {
                System.out.println("Unable to send message=[" + employee+"] due to :" + ex.getMessage());
            }
        });
    }
}

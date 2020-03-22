package com.bk.producer;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bk.Customer;

@Component
public class ProducerApplication {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Scheduled(fixedRate = 10000)
	public void produce() {
		for (int i = 1; i < 3; i++) {
			String id = UUID.randomUUID().toString();
			String customer = new Customer(id, Math.random() > 0.5 ? "Manoj" : "Kumar").toString();
			System.out.println("Produced :: " + customer);
			this.kafkaTemplate.send("customer_topic", id, customer);
		}
	}
}

package com.bk.consumer;

import java.util.List;
import java.util.Properties;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
@EnableBatchProcessing
public class ConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final KafkaProperties properties;

	@Bean
	Job job() {
		return jobBuilderFactory.get("job")
				.incrementer(new RunIdIncrementer())
				.start(start())
				.build();
	}

	@Bean
	KafkaItemReader<String, String> kafkaItemReader() {
		Properties props = new Properties();
		props.putAll(this.properties.buildConsumerProperties());
		return new KafkaItemReaderBuilder<String, String>()
				.partitions(0)
				.consumerProperties(props)
				.name("customer-reader")
				.saveState(true)
				.topic("customer_topic")
				.build();
	}

	@Bean
	Step start() {
		ItemWriter writer = new ItemWriter<String>() {

			@Override
			public void write(List<? extends String> items) throws Exception {
				items.forEach(item -> System.out.println("new customer: " + item));
			}
		};

		return stepBuilderFactory.get("job")
				.chunk(0)
				.reader(kafkaItemReader())
				.writer(writer)
				.build();
	}

}
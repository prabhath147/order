package com.order.service.config;

import java.time.LocalDate;

import com.order.service.service.RepeatOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name="scheduler.enabled",matchIfMissing=true)
public class RepeatOrderScheduler {
	
	@Autowired
	RepeatOrderService repeatOrderService;
	
	@Scheduled(cron = "0/30 * * * * *")
	public void sendNotification() {
//		LocalDate date=LocalDate.now();
		LocalDate date= LocalDate.of(2022, 9, 4);
		repeatOrderService.getOptInToSendNotification(date);
	}

}

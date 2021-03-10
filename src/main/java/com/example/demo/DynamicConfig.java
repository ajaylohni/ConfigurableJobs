package com.example.demo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

@Configuration
@EnableScheduling
@Component
public class DynamicConfig  implements SchedulingConfigurer{
	 	
	@Autowired
	private SchedulerRepository schedulerRepo;
		@Override
	    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
	        taskRegistrar.addTriggerTask(new Runnable() {
	            @Override
	            public void run() {
	                // Do not put @Scheduled annotation above this method, we don't need it anymore.
	                System.out.println("Running Schedular..." + Calendar.getInstance().getTime());
	            }
	        }, new Trigger() {
	            @Override
	            public Date nextExecutionTime(TriggerContext triggerContext) {
	                Calendar nextExecutionTime = new GregorianCalendar();
	                Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
	                nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
	                nextExecutionTime.add(Calendar.MILLISECOND, getNewExecutionTime());
	                return nextExecutionTime.getTime();
	            }

			
	        });
	    }

	    private int getNewExecutionTime() {
	        //Load Your execution time from database or property file
	    	if(schedulerRepo.count() == 0 ) {
	    		System.out.println("Count is zero");
	    		return 1000;
	    	}
	    	else 
	    		return schedulerRepo.findAll().get(0).getSchedulerTime();
	    }

	    @Bean
	    public TaskScheduler poolScheduler() {
	        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
	        scheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
	        scheduler.setPoolSize(1);
	        scheduler.initialize();
	        return scheduler;
	    }

	

	
}

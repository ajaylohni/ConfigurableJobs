package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "jobconfig")
public class JobConfig {
	@Id
	@GeneratedValue(
	    strategy = GenerationType.SEQUENCE,
	    generator = "seq_post"
	)
	@SequenceGenerator(
	    name = "seq_post",
	    allocationSize = 50
	)
	private Long id;
	
	
	@Override
	public String toString() {
		return "JobConfig [schedulerTime=" + schedulerTime + "]";
	}

	public int getSchedulerTime() {
		return schedulerTime;
	}

	public void setSchedulerTime(int schedulerTime) {
		this.schedulerTime = schedulerTime;
	}

	private int schedulerTime;


}


package com.eureka.king.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString

public class King {
	
	@Id
	private int no;
	private String nickname;
	private long count;
	private int ranking;
	
	
}

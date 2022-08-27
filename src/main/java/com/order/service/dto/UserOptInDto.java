package com.order.service.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserOptInDto {
	
	private Long OptInId;
	private Long userId;
	private Double totalPrice;
	private Set<ItemDto> items = new HashSet<>();

}

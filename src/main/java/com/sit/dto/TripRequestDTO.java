package com.sit.dto;

import com.sit.enums.DestinationType;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TripRequestDTO {
	
	private Long userId;
    private String location;
    private int days;
    private DestinationType type;

   
}

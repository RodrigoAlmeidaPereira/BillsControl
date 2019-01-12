package br.com.billscontrol.exception;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class StandardError {
	
	private Integer errorCode;
	private String message;
	private Instant instant;

}

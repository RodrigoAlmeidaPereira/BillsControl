package br.com.billscontrol.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class StandardError {
	
	private Integer errorCode;
	private String status;
	private String message;
	private Instant instant;

}

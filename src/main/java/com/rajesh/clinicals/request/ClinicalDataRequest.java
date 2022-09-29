package com.rajesh.clinicals.request;

import java.sql.Timestamp;

import com.rajesh.clinicals.model.Patient;

import lombok.Data;

@Data
public class ClinicalDataRequest {

	private int id;
	private String componentName;
	private String componentValue;
	private Timestamp mesaureDateTime;
	private Patient patient;
}

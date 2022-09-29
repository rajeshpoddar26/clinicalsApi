package com.rajesh.clinicals.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rajesh.clinicals.model.Clinicaldata;
import com.rajesh.clinicals.model.Patient;
import com.rajesh.clinicals.repos.PatientRepository;
import com.rajesh.clinicals.util.BMICalculator;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
public class PatientController {

	private PatientRepository patientRepository;

	@Autowired
	public PatientController(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}

	Map<String, String> filter = new HashMap<>();

	@RequestMapping(value = "patients", method = RequestMethod.GET)
	public List<Patient> getPatients() {
		return patientRepository.findAll();
	}

	@RequestMapping(value = "patients/{id}", method = RequestMethod.GET)
	public Patient getPatient(@PathVariable(value = "id") int id) {
		return patientRepository.findById(id).get();
	}

	@RequestMapping(value = "/patients", method = RequestMethod.POST)
	public Patient savePatient(@RequestBody Patient patient) {
		return patientRepository.save(patient);
	}

	@RequestMapping(value = "/patients/analize/{id}", method = RequestMethod.POST)
	public Patient analize(@PathVariable("id") int id) {
		Patient patient = patientRepository.findById(id).get();
		List<Clinicaldata> clinicaldatas = patient.getClinicaldatas();

		ArrayList<Clinicaldata> dublicateClinicaldatas = new ArrayList<>(clinicaldatas);

		for (Clinicaldata eachEntry : dublicateClinicaldatas) {

			if (filter.containsKey(eachEntry.getComponentName())) {
				clinicaldatas.remove(eachEntry);
				continue;
			} else {
				filter.put(eachEntry.getComponentName(), null);
			}

			BMICalculator.calculateBMI(clinicaldatas, eachEntry);
		}
		filter.clear();
		return patient;
	}

}

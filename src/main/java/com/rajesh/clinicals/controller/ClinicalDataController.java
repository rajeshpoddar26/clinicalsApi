package com.rajesh.clinicals.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rajesh.clinicals.model.Clinicaldata;
import com.rajesh.clinicals.model.Patient;
import com.rajesh.clinicals.repos.ClinicalDataRepository;
import com.rajesh.clinicals.repos.PatientRepository;
import com.rajesh.clinicals.request.ClinicalDataRequest;
import com.rajesh.clinicals.util.BMICalculator;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
public class ClinicalDataController {

	private ClinicalDataRepository clinicalDataRepository;

	private PatientRepository patientRepository;

	@Autowired
	public ClinicalDataController(ClinicalDataRepository clinicalDataRepository, PatientRepository patientRepository) {
		this.clinicalDataRepository = clinicalDataRepository;
		this.patientRepository = patientRepository;
	}

	@RequestMapping(value = "clinicals", method = RequestMethod.POST)
	public Clinicaldata saveClinicaldata(@RequestBody ClinicalDataRequest request) {
		Patient patient = patientRepository.findById(request.getId()).get();

		Clinicaldata clinicaldata = new Clinicaldata();
		clinicaldata.setComponentName(request.getComponentName());
		clinicaldata.setComponentValue(request.getComponentValue());
		clinicaldata.setPatient(patient);

		return clinicalDataRepository.save(clinicaldata);
	}

	@RequestMapping(value = "clinicals/{patientId}/{componentName}", method = RequestMethod.GET)
	public List<Clinicaldata> getClinicaldatas(@PathVariable("patientId") int patientId,
			@PathVariable("componentName") String componentName) {

		if (componentName.equals("bmi")) {
			componentName = "hw";
		}
		List<Clinicaldata> clinicaldata = clinicalDataRepository
				.findByPatientIdAndComponentNameOrderByMesaureDateTime(patientId, componentName);
		ArrayList<Clinicaldata> dublicateClinicaldatas = new ArrayList<>(clinicaldata);
		for (Clinicaldata eachEntry : dublicateClinicaldatas) {

			BMICalculator.calculateBMI(clinicaldata, eachEntry);
		}

		return clinicaldata;

	}
}

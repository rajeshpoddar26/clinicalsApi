package com.rajesh.clinicals.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajesh.clinicals.model.Clinicaldata;

public interface ClinicalDataRepository extends JpaRepository<Clinicaldata, Integer> {

	List<Clinicaldata> findByPatientIdAndComponentNameOrderByMesaureDateTime(int patientId, String componentName);

}

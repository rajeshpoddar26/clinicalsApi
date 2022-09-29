package com.rajesh.clinicals.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajesh.clinicals.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

}

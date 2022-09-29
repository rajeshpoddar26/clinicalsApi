package com.rajesh.clinicals.util;

import java.util.List;

import com.rajesh.clinicals.model.Clinicaldata;

public class BMICalculator {

	public static void calculateBMI(List<Clinicaldata> clinicaldatas, Clinicaldata eachEntry) {
		if (eachEntry.getComponentName().equals("hw")) {
			String[] heightWeight = eachEntry.getComponentName().split("/");
			if (heightWeight != null && heightWeight.length > 1) {
				float heightInMeters = Float.parseFloat(heightWeight[0]) * 0.4536F;
				float bmi = Float.parseFloat(heightWeight[1]) / (heightInMeters * heightInMeters);
				Clinicaldata bmiData = new Clinicaldata();
				bmiData.setComponentName("bmi");
				bmiData.setComponentValue(Float.toString(bmi));
				clinicaldatas.add(bmiData);
			}
		}
	}
}

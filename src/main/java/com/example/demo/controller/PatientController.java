package com.example.demo.controller;

import com.example.demo.entity.Patient;
import com.example.demo.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("hospital")
public class PatientController {

    private PatientService patientService;

    @Autowired
    public void setPatientRepository(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patient")
    @RolesAllowed({"Practitioner", "Patient"})
    @Operation(summary = "Получение списка пациентов")
    public List<Patient> getPatients() {
        return patientService.findAll();
    }

    @RequestMapping(value = "/patient", method = {RequestMethod.PUT, RequestMethod.POST})
    @RolesAllowed({"Practitioner"})
    @Operation(summary = "Добавление пациента")
    public Patient addPatients(@RequestBody Patient patient) {
        return patientService.addPatient(patient);
    }

    @DeleteMapping("/patient")
    @RolesAllowed({"Practitioner"})
    @Operation(summary = "Удаление пациента")
    public ResponseEntity<String> deletePatients(@RequestParam @Parameter(description = "Идентификатор пациента") String id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok(id);
    }
}
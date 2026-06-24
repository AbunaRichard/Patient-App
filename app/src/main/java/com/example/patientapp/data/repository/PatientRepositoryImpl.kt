package com.example.patientapp.data.repository

import com.example.patientapp.data.local.PatientDao
import com.example.patientapp.data.remote.PatientApi
import com.example.patientapp.domain.model.Patient
import com.example.patientapp.domain.repository.PatientRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class PatientRepositoryImpl(
    private val dao: PatientDao,
    private val firestore: FirebaseFirestore,
    private val api: PatientApi
): PatientRepository {
    
    private val patientCollection = firestore.collection("patients")

    override suspend fun addOrUpDatePatient(patient: Patient) {
        // Save locally first
        dao.addOrUpdatePatient(patient)
        
        // Sync with Firebase
        try {
            val patientData = hashMapOf(
                "name" to patient.name,
                "age" to patient.age,
                "gender" to patient.gender,
                "doctorAssigned" to patient.doctorAssigned,
                "prescription" to patient.prescription
            )
            
            val documentId = patient.patientId?.toString() ?: patientCollection.document().id
            patientCollection.document(documentId).set(patientData).await()
            
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Sync with REST API
        try {
            if (patient.patientId == null) {
                api.addPatient(patient)
            } else {
                api.updatePatient(patient)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun deletePatient(patient: Patient) {
        dao.deletePatient(patient)
        
        // Firebase delete
        try {
            patient.patientId?.let { 
                patientCollection.document(it.toString()).delete().await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // REST API delete
        try {
            patient.patientId?.let {
                api.deletePatient(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getPatientBytId(patientId: Int): Patient? {
        return dao.getPatientBytId(patientId)
    }

    override fun getAllPatient(): Flow<List<Patient>> {
        return dao.getAllPatient()
    }
}

package com.example.patientapp.data.remote

import com.example.patientapp.domain.model.Patient
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PatientApi {

    @GET("get_patients.php")
    suspend fun getPatients(): List<Patient>

    @POST("add_patient.php")
    suspend fun addPatient(@Body patient: Patient): ApiResponse

    @POST("update_patient.php")
    suspend fun updatePatient(@Body patient: Patient): ApiResponse

    @DELETE("delete_patient.php")
    suspend fun deletePatient(@Query("id") id: Int): ApiResponse

    companion object {
        const val BASE_URL = "http://10.0.2.2/patient_api/" // 10.0.2.2 is localhost for Android Emulator
    }
}

data class ApiResponse(
    val success: Boolean,
    val message: String
)

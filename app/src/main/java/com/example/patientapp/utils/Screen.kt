package com.example.patientapp.utils

import com.example.patientapp.utils.Constants.PATIENT_DETAILS_ARG_KEY

sealed class Screen(val route: String) {

    object PatientList : Screen("PatientListScreen")

    object Notifications : Screen("notifications")

    object Profile : Screen("profile")

    object PatientDetails :
        Screen("patient_details_screen?$PATIENT_DETAILS_ARG_KEY={$PATIENT_DETAILS_ARG_KEY}") {

        fun passPatientId(patientId: Int? = null): String {
            return "patient_details_screen?$PATIENT_DETAILS_ARG_KEY=$patientId"
        }
    }
}
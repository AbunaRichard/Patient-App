package com.example.patientapp.utils

import android.R.attr.defaultValue
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.patientapp.presentation.login.LoginScreen
import com.example.patientapp.presentation.notification_screen.NotificationScreen
import com.example.patientapp.presentation.patient_detail.PatientDetailScreen
import com.example.patientapp.presentation.patient_list.PatientListScreen
import com.example.patientapp.presentation.profile_screen.ProfileScreen

@Composable
fun NavGraphSetup(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {

            LoginScreen(

                onLoginSuccess = {

                    navController.navigate("PatientListScreen") {

                        popUpTo("login") {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                }
            )
        }
        composable("PatientListScreen") {
            PatientListScreen(
                navHostController = navController,
                onItemClicked = {
                    navController.navigate(
                        "patient_details_screen?patientId=$it"
                    )
                },
                onFabClicked = {
                    navController.navigate(
                        "patient_details_screen?patientId=-1"
                    )
                }
            )
        }

        composable("notifications") {
            NotificationScreen()
        }

        composable("profile") {
            ProfileScreen()
        }
        composable(
            route = "patient_details_screen?patientId={patientId}",
            arguments = listOf(
                navArgument("patientId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ){
            PatientDetailScreen(
                onBackClicked = {
                    navController.navigateUp()
                },
                onSuccessfulSaveClicked = {
                    navController.navigateUp()
                }
            )
        }
    }

}
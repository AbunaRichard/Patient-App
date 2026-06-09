package com.example.patientapp.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(
    navController: NavController
) {

    NavigationBar {

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("PatientListScreen")
            },
            icon = {
                Icon(Icons.Default.Home, null)
            },
            label = {
                Text("Patients")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("notifications")
            },
            icon = {
                Icon(Icons.Default.Notifications, null)
            },
            label = {
                Text("Alerts")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("profile")
            },
            icon = {
                Icon(Icons.Default.Person, null)
            },
            label = {
                Text("Profile")
            }
        )
    }
}
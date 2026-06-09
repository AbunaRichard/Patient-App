package com.example.patientapp.presentation.patient_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.patientapp.presentation.components.BottomNavigationBar
import com.example.patientapp.presentation.components.DashboardCard
import com.example.patientapp.presentation.components.EmptyState
import com.example.patientapp.presentation.components.PatientSearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientListScreen(
    onItemClicked: (Int?) -> Unit,
    onFabClicked: () -> Unit,
    navHostController: NavHostController,
    viewModel: PatientListViewModel = hiltViewModel()
) {

    val patientsList by viewModel.patientList.collectAsState()

    Scaffold(
        containerColor = Color(0xFFF5F7FF),

        floatingActionButton = {

            FloatingActionButton(
                onClick = onFabClicked,
                containerColor = Color(0xFF6C63FF)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },

        bottomBar = {

            BottomNavigationBar(
                navController = navHostController
            )
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            DashboardCard(
                totalPatients = patientsList.size
            )

            Spacer(modifier = Modifier.height(12.dp))

            PatientSearchBar()

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(patientsList) { patient ->

                    PatientItem(
                        patient = patient,

                        onItemCLick = {
                            onItemClicked(patient.patientId)
                        },

                        onDeleteConfirm = {
                            viewModel.deletePatient(patient)
                        }
                    )
                }
            }
        }

        if (patientsList.isEmpty()) {
            EmptyState()
        }
    }
}
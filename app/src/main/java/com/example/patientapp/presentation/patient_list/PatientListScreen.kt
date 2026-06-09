package com.example.patientapp.presentation.patient_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.patientapp.presentation.components.BottomNavigationBar
import com.example.patientapp.presentation.components.DashboardCard
import com.example.patientapp.presentation.components.EmptyState
import com.example.patientapp.presentation.components.ModernAppBar

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

        topBar = {
            ModernAppBar()
        },

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
                .padding(padding)
                .fillMaxSize()
        ) {

            DashboardCard(
                totalPatients = patientsList.size
            )

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListAppBar() {
    TopAppBar(
        title = {
            Text(
                text = "Patient Tracker",
                color = Color.Blue,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListFab(onFabClicked: () -> Unit) {
    FloatingActionButton(onClick = onFabClicked) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add patients"
        )
    }
}
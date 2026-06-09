package com.example.patientapp.presentation.patient_detail

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.patientapp.presentation.components.ModernTextField
import com.example.patientapp.presentation.components.PatientHeader
import com.example.patientapp.presentation.components.SectionCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PatientDetailScreen (
    viewModel: PatientDetailsViewModel = hiltViewModel(),
    onBackClicked:()->Unit,
    onSuccessfulSaveClicked:()->Unit,

    ){

    val state = viewModel.state
    val focusRequester = remember{
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        delay(500)
        focusRequester.requestFocus()
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collectLatest {event->
            when(event){
                PatientDetailsViewModel.UIEvent.SaveNote -> {
                    onSuccessfulSaveClicked()
                    Toast.makeText(context, "Saved Successfully",Toast.LENGTH_LONG).show()
                }
                is PatientDetailsViewModel.UIEvent.ShowToast -> {
                    Toast.makeText(context,event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    Scaffold(
        containerColor = Color(0xFFF5F7FF),
        topBar = {
            TopBar(onBackClick = onBackClicked)
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            item {

                PatientHeader()

                SectionCard {

                    Text(
                        text = "Personal Information",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ModernTextField(
                        value = state.name,
                        label = "Full Name",
                        onValueChange = {
                            viewModel.onEvent(
                                PatientsDetailsEvents.EnteredName(it)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ModernTextField(
                        value = state.age,
                        label = "Age",
                        onValueChange = {
                            viewModel.onEvent(
                                PatientsDetailsEvents.EnteredAge(it)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Gender",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioGroup(
                            text = "Male",
                            selected = state.gender == 1,
                            onClick = {
                                viewModel.onEvent(PatientsDetailsEvents.SelectedMale)
                            },
                            modifier = Modifier.weight(1f)
                        )
                        RadioGroup(
                            text = "Female",
                            selected = state.gender == 2,
                            onClick = {
                                viewModel.onEvent(PatientsDetailsEvents.SelectedFemale)
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    ModernTextField(
                        value = state.doctorAssigned,
                        label = "Assigned Doctor",
                        onValueChange = {
                            viewModel.onEvent(
                                PatientsDetailsEvents.EnteredAssignedDoctor(it)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ModernTextField(
                        value = state.prescription,
                        label = "Prescription",
                        onValueChange = {
                            viewModel.onEvent(
                                PatientsDetailsEvents.EnteredPrescription(it)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            viewModel.onEvent(PatientsDetailsEvents.SaveButton)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {

                        Text("Save Patient")
                    }
                }
            }
        }
    }
}
@Composable
fun RadioGroup(
    onClick: () -> Unit,
    text:String,
    selected:Boolean,
    modifier: Modifier
){
    Row(
        modifier = Modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    )
    {
        RadioButton(
            selected =selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary
            )
        )
        Text(text = text, style = MaterialTheme.typography.bodySmall)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onBackClick:()->Unit
){
    TopAppBar(title = { Text(text ="Patient's Details Screen" ,
        style = MaterialTheme.typography.headlineMedium) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go Back")
            }
        })
}
package com.example.patientapp.presentation.components

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookOnline
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardCard(
    totalPatients: Int,
    onBookClick: () -> Unit = {},
    onReportClick: () -> Unit = {},
    onPaymentClick: () -> Unit = {},
    onResultsClick: () -> Unit = {},
    onEmergencyClick: () -> Unit = {},
    onAiClick: () -> Unit = {}
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6C63FF),
                        Color(0xFF8E7CFF)
                    )
                )
            )
            .padding(16.dp)
    ) {
        Text(
            text = "Good Morning,",
            color = Color.White.copy(alpha = 0.8f)
        )

        Text(
            text = "Dr. John Doe 👋",
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Row 1: BOOK and REPORT
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ActionButton(
                text = "BOOK",
                icon = Icons.Default.BookOnline,
                onClick = {
                    Toast.makeText(context, "Booking Appointment...", Toast.LENGTH_SHORT).show()
                    onBookClick()
                },
                modifier = Modifier.weight(1f)
            )

            ActionButton(
                text = "REPORT",
                icon = Icons.Default.Description,
                onClick = {
                    Toast.makeText(context, "Opening Reports...", Toast.LENGTH_SHORT).show()
                    onReportClick()
                },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Row 2: PAYMENT and RESULTS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ActionButton(
                text = "PAYMENT",
                icon = Icons.Default.Payments,
                onClick = {
                    Toast.makeText(context, "Checking Balance...", Toast.LENGTH_SHORT).show()
                    onPaymentClick()
                },
                modifier = Modifier.weight(1f)
            )

            ActionButton(
                text = "RESULTS",
                icon = Icons.Default.Science,
                onClick = {
                    Toast.makeText(context, "Fetching Lab Results...", Toast.LENGTH_SHORT).show()
                    onResultsClick()
                },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Row 3: EMERGENCY and AI
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ActionButton(
                text = "EMERGENCY",
                icon = Icons.Default.Warning,
                onClick = {
                    Toast.makeText(context, "Checking Emergency Cases...", Toast.LENGTH_SHORT).show()
                    onEmergencyClick()
                },
                modifier = Modifier.weight(1f)
            )

            ActionButton(
                text = "AI HELP",
                icon = Icons.Default.SmartToy,
                onClick = {
                    Toast.makeText(context, "Connecting to AI Help Desk...", Toast.LENGTH_SHORT).show()
                    onAiClick()
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ActionButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f, label = "scale")

    Card(
        onClick = onClick,
        modifier = modifier
            .height(80.dp)
            .scale(scale),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.2f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 0.dp
        ),
        interactionSource = interactionSource
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = text,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
        }
    }
}

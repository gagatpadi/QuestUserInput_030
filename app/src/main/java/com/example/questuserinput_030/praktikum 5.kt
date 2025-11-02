package com.example.questuserinput_030
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RegistrationScreen(modifier: Modifier = Modifier) {
    // --- State Management ---
    // 'remember' digunakan untuk menyimpan state dari input pengguna
    var namaLengkap by remember { mutableStateOf("") }
    var kotaAsal by remember { mutableStateOf("") }
    var tanggalLahir by remember { mutableStateOf("") }
    var rt by remember { mutableStateOf("") }
    var rw by remember { mutableStateOf("") }
    var umur by remember { mutableStateOf("") }

    val jenisKelaminOptions = listOf("Laki-laki", "Perempuan")
    var jenisKelamin by remember { mutableStateOf(jenisKelaminOptions[0]) } // Default

    var setuju by remember { mutableStateOf(false) }

    // Mendapatkan konteks (diperlukan untuk Toast dan DatePicker)
    val context = LocalContext.current
    val kalender = Calendar.getInstance()

    // --- Date Picker Dialog ---
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            kalender.set(year, month, dayOfMonth)
            val format = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            tanggalLahir = sdf.format(kalender.time)
        },
        kalender.get(Calendar.YEAR),
        kalender.get(Calendar.MONTH),
        kalender.get(Calendar.DAY_OF_MONTH)
    )
    Column(
        modifier = modifier // 1. Terapkan modifier dari parameter
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()) // Agar bisa di-scroll
    ) {
        Text(
            text = "Formulir Registrasi",
            fontSize = 24.sp,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

    }
}
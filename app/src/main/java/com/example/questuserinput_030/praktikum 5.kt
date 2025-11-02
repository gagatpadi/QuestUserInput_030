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
import androidx.compose.material3.* // Ini sudah mencakup AlertDialog dan TextButton
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
    var namaLengkap by remember { mutableStateOf("") }
    var kotaAsal by remember { mutableStateOf("") }
    var tanggalLahir by remember { mutableStateOf("") }
    var rt by remember { mutableStateOf("") }
    var rw by remember { mutableStateOf("") }
    var umur by remember { mutableStateOf("") }

    val jenisKelaminOptions = listOf("Laki-laki", "Perempuan")
    var jenisKelamin by remember { mutableStateOf(jenisKelaminOptions[0]) }

    var setuju by remember { mutableStateOf(false) }

    // --- State Baru untuk Pop-up (DITAMBAHKAN) ---
    var showSuccessDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

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
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Formulir Registrasi",
            fontSize = 24.sp,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Nama Lengkap
        OutlinedTextField(
            value = namaLengkap,
            onValueChange = { namaLengkap = it },
            label = { Text("Nama Lengkap") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Kota Asal
        OutlinedTextField(
            value = kotaAsal,
            onValueChange = { kotaAsal = it },
            label = { Text("Kota Asal") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tanggal Lahir
        OutlinedTextField(
            value = tanggalLahir,
            onValueChange = { /* Dibiarkan kosong agar read-only */ },
            label = { Text("Tanggal Lahir") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() },
            trailingIcon = {
                IconButton(onClick = { datePickerDialog.show() }) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Pilih Tanggal"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // RT dan RW
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = rt,
                onValueChange = { rt = it },
                label = { Text("RT") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            )
            OutlinedTextField(
                value = rw,
                onValueChange = { rw = it },
                label = { Text("RW") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Umur
        OutlinedTextField(
            value = umur,
            onValueChange = { umur = it },
            label = { Text("Umur") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Jenis Kelamin
        Text("Jenis Kelamin", style = MaterialTheme.typography.bodyLarge)
        Row(Modifier.fillMaxWidth()) {
            jenisKelaminOptions.forEach { text ->
                Row(
                    Modifier
                        .selectable(
                            selected = (jenisKelamin == text),
                            onClick = { jenisKelamin = text },
                            role = Role.RadioButton
                        )
                        .padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (jenisKelamin == text),
                        onClick = null
                    )
                    Text(text = text, modifier = Modifier.padding(start = 4.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Checkbox Syarat & Ketentuan
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { setuju = !setuju }
        ) {
            Checkbox(
                checked = setuju,
                onCheckedChange = { setuju = it }
            )
            Text(
                text = "Saya setuju dengan syarat dan ketentuan yang berlaku.",
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        //tombol submit
        Button(
            onClick = {
                if (validasiData(context, namaLengkap, kotaAsal, tanggalLahir, rt, rw, umur, setuju)) {
                    // Siapkan pesan untuk pop-up
                    val ringkasan = """
                        Nama: $namaLengkap
                        Kota: $kotaAsal
                        Tgl Lahir: $tanggalLahir
                        RT/RW: $rt/$rw
                        Umur: $umur
                        Jenis Kelamin: $jenisKelamin
                    """.trimIndent()


                    dialogMessage = ringkasan
                    showSuccessDialog = true

                    // Toast.makeText(context, ringkasan, Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SUBMIT", fontSize = 16.sp)
        }

        // pop up
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = {
                    showSuccessDialog = false
                },
                title = {
                    Text(text = "Submit Sudah Berhasil!")
                },
                text = {
                    Text(text = dialogMessage)
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showSuccessDialog = false
                            // TODO: Di sini Anda bisa mereset form atau pindah halaman
                        }
                    ) {
                        Text("OK")
                    }
                }
            )
        }

    }
}

private fun validasiData(
    context: Context,
    nama: String,
    kota: String,
    tgl: String,
    rt: String,
    rw: String,
    umur: String,
    setuju: Boolean
): Boolean {
    // Toast validasi yang gagal tetap dipertahankan
    if (nama.isEmpty() || kota.isEmpty() || tgl.isEmpty() || rt.isEmpty() || rw.isEmpty() || umur.isEmpty()) {
        Toast.makeText(context, "Semua data harus diisi dengan lengkap", Toast.LENGTH_SHORT).show()
        return false
    }
    if (!setuju) {
        Toast.makeText(context, "Anda harus menyetujui syarat dan ketentuan", Toast.LENGTH_SHORT).show()
        return false
    }
    return true
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RegistrationScreen()
    // }
}
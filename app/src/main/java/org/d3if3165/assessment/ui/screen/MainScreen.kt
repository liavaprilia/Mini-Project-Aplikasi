package org.d3if3165.assessment.ui.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3165.assessment.R
import org.d3if3165.assessment.navigation.Screen
import org.d3if3165.assessment.ui.theme.AssessmentTheme
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                //membuat icon menu
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.About.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.tentang_aplikasi),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { padding ->
        ScreenContent(Modifier.padding(padding))
    }
}

@Composable
fun ScreenContent(modifier: Modifier) {
    var namaPelanggan by rememberSaveable { mutableStateOf("") }
    var namaError by rememberSaveable { mutableStateOf(false) }

    var alamatPelanggan by rememberSaveable { mutableStateOf("") }
    var alamatError by rememberSaveable { mutableStateOf(false) }

    var beratCucian by rememberSaveable { mutableStateOf("") }
    var beratCucianError by rememberSaveable { mutableStateOf(false) }

    var totalBiaya by rememberSaveable { mutableFloatStateOf(0f) }
    var diskon by rememberSaveable { mutableFloatStateOf(0f) }

    val context = LocalContext.current

    var currentTime = remember { Calendar.getInstance() }
    var isHitungClicked by remember { mutableStateOf(false) }
    
    var statusPembayaran by rememberSaveable { mutableStateOf(false) }
    var metodePembayaran by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.laundrybg),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.laundry_intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = namaPelanggan,
            onValueChange = { namaPelanggan = it },
            label = { Text(text = stringResource(id = R.string.nama_pelanggan)) },
            trailingIcon = { IconPicker(isError = namaError, unit = "") },
            supportingText = { ErrorHint(namaError)},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = alamatPelanggan,
            onValueChange = { alamatPelanggan = it },
            label = { Text(text = stringResource(id = R.string.alamat_pelanggan)) },
            trailingIcon = { IconPicker(isError = alamatError, unit = "")},
            supportingText = { ErrorHint(alamatError) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = beratCucian,
            onValueChange = { beratCucian = it },
            label = { Text(text = stringResource(id = R.string.berat_cucian)) },
            trailingIcon = { IconPicker(isError = beratCucianError, unit = "kg") },
            supportingText = { ErrorHint(beratCucianError) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Text("Status Pembayaran:")
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = !statusPembayaran,
                onClick = { statusPembayaran = !statusPembayaran },
                enabled = !isHitungClicked
            )
            Text("Belum Lunas")
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = statusPembayaran,
                    onClick = { statusPembayaran = !statusPembayaran },
                    enabled = !isHitungClicked
                )
                Text("Lunas")
            }
        }
        if (statusPembayaran) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Metode Pembayaran:")
                RadioButton(
                    selected = metodePembayaran == "Cash",
                    onClick = { if (!isHitungClicked && statusPembayaran) metodePembayaran = "Cash" },
                    enabled = statusPembayaran
                )
                Text("Cash")
                RadioButton(
                    selected = metodePembayaran == "Transfer",
                    onClick = { if (!isHitungClicked && statusPembayaran) metodePembayaran = "Transfer" },
                    enabled = statusPembayaran
                )
                Text("Transfer")
            }
        }
        //hitung
        Button(
            onClick = {
                namaError = (namaPelanggan.isEmpty())
                alamatError = (alamatPelanggan.isEmpty())
                beratCucianError = (beratCucian == "" || beratCucian == "0")

                if (beratCucianError || namaError || alamatError) return@Button

                val berat = beratCucian.toFloat()
                totalBiaya = berat * 5000
                diskon = if (berat >= 5) totalBiaya * 0.1f else 0f
                isHitungClicked = true
            },
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.hitung))
        }

        Button(
            onClick = {
                namaPelanggan = ""
                alamatPelanggan = ""
                currentTime = Calendar.getInstance()
                beratCucian = ""
                totalBiaya = 0f
                diskon = 0f
                isHitungClicked = false

                //status error
                namaError = false
                alamatError = false
                beratCucianError = false

            },
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.atur_ulang))
        }
        if (isHitungClicked) {
            Text(
                text = "Total Biaya: ${totalBiaya - diskon}",
                style = MaterialTheme.typography.bodyLarge
            )
            if (diskon > 0) {
                Text(
                    text = "Diskon: $diskon",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                text = "Status Pembayaran: ${if (statusPembayaran) "Lunas" else "Belum Lunas"}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
            Button(
                onClick = {
                    shareData(
                        context = context,
                        message = context.getString(
                            R.string.bagikan_template,
                            namaPelanggan,
                            alamatPelanggan,
                            beratCucian,
                            currentTime.time.toString(),
                            totalBiaya - diskon,
                            diskon
                        )
                    )
                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(R.string.bagikan))
            }
        }
    }
}

//membuat nagivasi ke aplikasi lain (fitur share)
private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null){
        context.startActivity(shareIntent)
    }
}

@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input_invalid))
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun GreetingPreview() {
    AssessmentTheme {
        MainScreen(rememberNavController())
    }
}
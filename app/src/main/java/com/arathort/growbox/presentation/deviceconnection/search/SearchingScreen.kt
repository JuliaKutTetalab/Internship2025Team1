package com.arathort.growbox.presentation.deviceconnection.search

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.arathort.growbox.R
import com.arathort.growbox.domain.models.device.ScannedDevice
import com.arathort.growbox.presentation.common.Dimensions
import com.arathort.growbox.presentation.common.button.TransparentButton
import com.arathort.growbox.presentation.deviceconnection.components.PulsatingRadar
import com.arathort.growbox.presentation.navigation.Route
import com.arathort.growbox.ui.theme.Green800
import com.arathort.growbox.ui.theme.Grey400
import com.arathort.growbox.ui.theme.GrowBoxTheme
import com.arathort.growbox.ui.theme.Typography

@Composable
fun SearchingScreen(
    backStack: NavBackStack<NavKey>,
    viewModel: SearchingScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    val bluetoothAdapter = bluetoothManager.adapter

    val enableBluetoothLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.startScanning()
        } else {
            Toast.makeText(context, "Bluetooth is required for scanning", Toast.LENGTH_SHORT).show()
        }
    }

    fun scanOrAskToEnableBluetooth() {
        if (bluetoothAdapter?.isEnabled == true) {
            viewModel.startScanning()
        } else {
            enableBluetoothLauncher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
        }
    }

    val permissionsToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        arrayOf(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT)
    } else {
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val allGranted = perms.values.all { it }
        if (allGranted) {
            scanOrAskToEnableBluetooth()
        } else {
            Toast.makeText(context, "Bluetooth permissions required", Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(Unit) {
        val allGranted = permissionsToRequest.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
        if (allGranted) {
            scanOrAskToEnableBluetooth()
        } else {
            permissionLauncher.launch(permissionsToRequest)
        }
    }

    SearchingPage(
        onBackClick = { backStack.remove(Route.Searching) },
        onDeviceClick = {
            backStack.add(Route.Connecting)
        },
        uiState = uiState
    )
}

@Composable
fun SearchingPage(
    onBackClick: () -> Unit,
    onDeviceClick: () -> Unit,
    uiState: SearchingScreenUiState
) {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimensions.pagePadding)
            ) {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_back),
                        contentDescription = null
                    )
                }
            }

            if (uiState.scannedDevices.isEmpty()) {
                SearchingComponent(onBackClick)
            } else {
                FoundDevicesList(
                    devices = uiState.scannedDevices,
                    onDeviceClick = onDeviceClick
                )
            }
        }
    }
}

@Composable
private fun SearchingComponent(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimensions.pagePadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(Modifier.height(Dimensions.medium))

            Text(
                modifier = Modifier,
                style = Typography.titleMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                text = stringResource(R.string.search_title)
            )

            Spacer(Modifier.height(Dimensions.medium))

            Text(
                modifier = Modifier,
                style = Typography.titleSmall,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                text = stringResource(R.string.search_subtitle)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            PulsatingRadar()
        }

        Column {
            TransparentButton(
                text = stringResource(R.string.cancel),
                onClick = { onBackClick() }
            )
            Spacer(Modifier.height(Dimensions.large))
        }
    }
}

@Composable
private fun FoundDevicesList(
    devices: List<ScannedDevice>,
    onDeviceClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimensions.pagePadding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(Dimensions.medium))

        Text(
            text = "Found Devices",
            style = Typography.titleMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(Dimensions.medium))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(Dimensions.medium)
        ) {
            items(devices) { device ->
                DeviceCard(device = device, onClick = { onDeviceClick() })
            }
        }
    }
}

@Composable
private fun DeviceCard(device: ScannedDevice, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimensions.cardHeight)
            .shadow(
                elevation = Dimensions.micro,
                shape = RoundedCornerShape(Dimensions.bigRadius),
                ambientColor = Color.Black.copy(alpha = 0.1f),
                spotColor = Color.Black.copy(alpha = 0.2f)
            ),
        shape = RoundedCornerShape(Dimensions.bigRadius),
        colors = CardDefaults.cardColors()
            .copy(containerColor = MaterialTheme.colorScheme.onPrimary)
    ) {
        Row(
            modifier = Modifier
                .padding(Dimensions.medium)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimensions.medium)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_groupbox),
                contentDescription = null,
                modifier = Modifier.size(Dimensions.mediumIconSize)
            )
            Column(verticalArrangement = Arrangement.Center) {
                Text(text = device.name, style = Typography.bodyLarge, fontWeight = FontWeight.Bold)
                Text(text = device.address, style = Typography.labelLarge, color = Grey400)

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Signal: ${device.rssi} dBm",
                    style = Typography.labelSmall,
                    color = if (device.rssi > -70) Green800 else Grey400
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchingPagePreview() {
    GrowBoxTheme {
        SearchingPage(
            onBackClick = {},
            onDeviceClick = {},
            uiState = SearchingScreenUiState()
        )
    }
}
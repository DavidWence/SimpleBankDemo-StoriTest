package com.example.simplebankingapp_storitest.presentation.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.simplebankingapp_storitest.R
import com.example.simplebankingapp_storitest.presentation.ui.ActionButton
import com.example.simplebankingapp_storitest.presentation.ui.LabelText
import com.example.simplebankingapp_storitest.presentation.ui.NavigateUpAppBar
import com.example.simplebankingapp_storitest.presentation.ui.TitleText
import com.example.simplebankingapp_storitest.presentation.ui.theme.SimpleBankingAppTheme
import com.example.simplebankingapp_storitest.presentation.utils.createImageFile
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun SignUpPhotoIdScreen(onNavigateUp: () -> Unit = {}, onPhotoTaken: () -> Unit){
    //captura de imagen
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        "com.example.simplebankingapp_storitest.provider",
        file)

    var capturedImageUri by remember { mutableStateOf<Uri>(Uri.EMPTY) }
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            capturedImageUri = uri
        }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()) {
        if (it)
            cameraLauncher.launch(uri)
        else
            Toast.makeText(
                context, R.string.signup_error_camerapermissionnotgranted, Toast.LENGTH_SHORT)
                .show()
    }

    Scaffold(
        topBar = { NavigateUpAppBar(onNavigateUp) }) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            Column(
                Modifier
                    .width(IntrinsicSize.Max)
                    .padding(16.dp)) {
                TitleText(R.string.signup_photoid_title)
                LabelText(R.string.signup_label_photoid_subtitle)
                ActionButton(
                    labelId = R.string.signup_action_takephoto,
                    onButtonClicked = {
                        val permissionCheckResult =
                            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                        //si la app tiene permisos se lanza el intent para tomar una foto
                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED)
                            cameraLauncher.launch(uri)
                        else
                            //de lo contrario se solicitan los permisos correspondientes
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                    },
                    modifier = Modifier.align(CenterHorizontally))
                //se renderiza la imagen
                if (capturedImageUri.path?.isNotEmpty() == true) {
                    Image(
                        modifier = Modifier.padding(16.dp, 24.dp).align(CenterHorizontally),
                        painter = rememberImagePainter(capturedImageUri),
                        contentDescription = null)
                    ActionButton(
                        labelId = R.string.generic_action_continue,
                        onButtonClicked = onPhotoTaken,
                        modifier = Modifier.align(CenterHorizontally),
                        padddingTop = 8)
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Light")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark")
@Composable
fun SignUpPhotoIdScreen() {
    SimpleBankingAppTheme {
        SignUpUserInfoScreen()
    }
}
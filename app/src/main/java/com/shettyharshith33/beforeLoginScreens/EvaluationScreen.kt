//import android.net.Uri
//import android.widget.Toast
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonColors
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import coil.compose.AsyncImage
//import com.shettyharshith33.beforeLoginScreens.BeforeLoginScreensNavigationObject
//import com.shettyharshith33.beforeLoginScreens.PdfPickerBox
//import com.shettyharshith33.beforeLoginScreens.getFileNameFromUri
//
//@Composable
//fun EvaluationScreen(navController: NavController) {
//
//
//    val context = LocalContext.current
//    val registerNumber = remember { mutableStateOf("") }
//    val selectedImages = remember { mutableStateListOf<Uri>() }
//    var answerPaperPdf by remember { mutableStateOf<Uri?>(null) }
//
//
//    // Image Picker
//    val imagePickerLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetMultipleContents()
//    ) { uris ->
//        selectedImages.addAll(uris)
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 60.dp)
//            .padding(8.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//
//        Text(
//            text = "Enter Register Number",
//            fontSize = 20.sp, style = MaterialTheme.typography.titleMedium
//        )
//        OutlinedTextField(
//            value = registerNumber.value,
//            onValueChange = { registerNumber.value = it },
//            label = { Text("Register Number") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(text = "Upload Answer Sheet Images", style = MaterialTheme.typography.titleMedium)
//
//        Button(
//            colors = ButtonDefaults.buttonColors().copy(
//                containerColor = Color.Black
//            ), onClick = {
//                imagePickerLauncher.launch("image/*")
//            }) {
//            Text("Pick Images")
//        }
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Text("OR", fontSize = 20.sp)
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        LazyRow {
//            items(selectedImages) { uri ->
//                AsyncImage(
//                    model = uri,
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(100.dp)
//                        .padding(4.dp)
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//
//
//        Text("Upload Answer Paper in PDF format", modifier = Modifier.padding(top = 60.dp))
//        Spacer(modifier = Modifier.height(8.dp))
//
//        PdfPickerBox(
//            context = context,
//            selectedPdfName = answerPaperPdf?.let {
//                getFileNameFromUri(
//                    context,
//                    it
//                )
//            }) { uri ->
//            answerPaperPdf = uri
//
//        }
//        Button(
//            colors = ButtonDefaults.buttonColors().copy(
//                containerColor = Color.Black
//            ),
//            onClick = {
//                if (registerNumber.value.isNotBlank() && selectedImages.isNotEmpty()) {
//                    navController.navigate(BeforeLoginScreensNavigationObject.RESULT_SCREEN)
//                } else {
//                    Toast.makeText(
//                        context,
//                        "Please enter Register Number and select images",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            },
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        ) {
//            Text("Evaluate")
//        }
//    }
//}


import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.shettyharshith33.beforeLoginScreens.BeforeLoginScreensNavigationObject
import com.shettyharshith33.beforeLoginScreens.PdfPickerBox
import com.shettyharshith33.beforeLoginScreens.getFileNameFromUri

@Composable
fun EvaluationScreen(navController: NavController) {

    val context = LocalContext.current
    val registerNumber = remember { mutableStateOf("") }
    val selectedImages = remember { mutableStateListOf<Uri>() }
    var answerPaperPdf by remember { mutableStateOf<Uri?>(null) }

    // Image Picker Launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        selectedImages.addAll(uris)
    }

    // Website URL (replace with your actual website URL)
    val websiteUrl = "http://172.16.160.111:5000/"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Spacer(modifier = Modifier.height(250.dp))

        Text("Open Website", fontSize = 20.sp, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(25.dp))
        Button(
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = Color.Black
            ),
            onClick = {


                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
                context.startActivity(intent)

            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Proceed")
        }
    }
}



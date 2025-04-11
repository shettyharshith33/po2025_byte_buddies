package com.shettyharshith33.beforeLoginScreens

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.shettyharshith33.vcputtur.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var OLText by remember { mutableStateOf("") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    Spacer(modifier = Modifier.height(20.dp))
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )
    {
        ModalNavigationDrawer(
            modifier = Modifier.background(Color.Black),
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(navController)
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "AI Evaluator",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch { drawerState.open() }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu"
                                )
                            }
                        },
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = Color(Color.Black.toArgb()),
                            titleContentColor = Color.White,
                            navigationIconContentColor = Color.White
                        )
                    )
                }
            ) { innerPadding ->
                LazyColumn(
                    contentPadding = innerPadding,
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Text("Enter Registration Number", modifier = Modifier.padding(top = 8.dp))
                    }
                    item {
                        OutlinedTextField(modifier = Modifier
                            .width(180.dp)
                            .height(10.dp)
                            .padding(top = 20.dp),
                            value = OLText,
                            onValueChange = { OLText = it })
                    }
                    item {
                        Text("Upload Answer Key", modifier = Modifier.padding(top = 60.dp))
                    }
                    item {
                        val context = LocalContext.current

                        PdfPickerBox(context = context, selectedPdfName = null) { uri ->
                            if (uri != null) {
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun DrawerContent(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.White)
            .width(250.dp)
            .padding(
                top = 50.dp,
                start = 5.dp
            )
    ) {
        Text(
            text = "Menu",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Text(text = "Sign-out", modifier = Modifier
            .clickable {
                FirebaseAuth.getInstance().signOut()
                navController.navigate(BeforeLoginScreensNavigationObject.ONBOARDING_SCREEN)
            }
            .padding(vertical = 8.dp))
    }
}

@Composable
fun PdfPickerBox(context: Context, selectedPdfName: String?, onPdfSelected: (Uri?) -> Unit) {
    var fileName by remember { mutableStateOf(selectedPdfName) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null)
        {
            val name = getFileNameFromUri(context, uri)
            fileName = name
        }
        onPdfSelected(uri)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable { launcher.launch("application/pdf") }
            .background(Color.White)
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (fileName != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(painter = painterResource(R.drawable.pdf_icon),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp))
                Text(text = fileName!!, color = Color.Black, fontWeight = FontWeight.SemiBold)
            }
        } else {
            Text("Tap to select a PDF", color = Color.Gray)
        }
    }
}

fun getFileNameFromUri(context: Context, uri: Uri): String {
    var name = "Unknown.pdf"
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (it.moveToFirst())
        {
            name = it.getString(nameIndex)
        }
    }
    return name
}




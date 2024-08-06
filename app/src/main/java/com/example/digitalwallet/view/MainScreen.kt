package com.example.digitalwallet.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.digitalwallet.R
import com.example.digitalwallet.data.model.User
import com.example.digitalwallet.data.repository.FirestoreRepository
import com.example.digitalwallet.ui.myCustomColor
import com.example.digitalwallet.viewModel.MainViewModel
import com.example.digitalwallet.viewModel.factory.MainViewModelFactory


@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = viewModel(factory = MainViewModelFactory(FirestoreRepository())),
    userId: String
) {
    val user by mainViewModel.user.collectAsState()

    LaunchedEffect(userId) {
        mainViewModel.loadUserData(userId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color(0xFF6200EA)) // Custom color
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            BalanceSection(User)
            Spacer(modifier = Modifier.height(16.dp))
            TransactionsSummary(User) {
                println("Tell me more clicked")
            }
            Spacer(modifier = Modifier.height(16.dp))
            ActivitySection(navController)
        }
    }
}

@Composable
fun BalanceSection(user: User?) {
    val balance = user?.balance?.toString() ?: "0.0"
    val spent = user?.spent?.toString() ?: "0.0"
    val earned = user?.earned?.toString() ?: "0.0"
    val iban = user?.iban ?: "N/A"
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(Color(0xFF6200EA)) // Custom color
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text(
                            text = " $$balance ",
                            style = MaterialTheme.typography.headlineMedium.copy(color = Color.White, fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Available balance",
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "IBAN: $iban",
                                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(iban))
                                    Toast.makeText(context, "IBAN copied to clipboard", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "Copy IBAN",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                    Image(
                        painter = rememberAsyncImagePainter(model = "https://via.placeholder.com/150"),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.Gray), // Background color for the placeholder
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Composable
fun TransactionsSummary(user: User?, onClick: () -> Unit) {
    val spent = user?.spent?.toString() ?: "$0"
    val earned = user?.earned?.toString() ?: "$0"

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(200.dp), // Increase the height as needed
        colors = CardDefaults.cardColors(containerColor = Color.White), // Ensure the Card background is white
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Set elevation using CardDefaults
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween // Ensure spacing between items
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround // Space items evenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Spent",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray, fontWeight = FontWeight.Normal)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color.Red, shape = CircleShape)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = spent,
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Earned",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray, fontWeight = FontWeight.Normal)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color(0xFF6200EA), shape = CircleShape)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = earned,
                                style = MaterialTheme.typography.headlineSmall
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Divider(color = Color.Gray, thickness = 0.5.dp, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "You spent $spent on dining out this month. Let's try to make it lower.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Tell me more",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF6200EA)),
                    modifier = Modifier
                        .clickable { onClick() }
                        .align(Alignment.BottomStart)
                )
            }
        }
    }
}

@Composable
fun ActivitySection(navController: NavController) {
    Spacer(modifier = Modifier.height(10.dp))
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Activity",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ActivityButton("Transfer", R.drawable.img_4) { navController.navigate("transfer") }
            ActivityButton("My Card", R.drawable.img_3) { navController.navigate("my_card") }
            ActivityButton("Insight", R.drawable.img_5) { navController.navigate("insight") }
        }
    }
}

@Composable
fun ActivityButton(text: String, iconRes: Int, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            .padding(8.dp)
            .border(1.dp, Color(0xFF6200EA), shape = RoundedCornerShape(10.dp))
            .background(Color(0xFF6200EA), shape = RoundedCornerShape(10.dp))
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            modifier = Modifier.size(40.dp),
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal, color = Color.White)
        )
    }
}
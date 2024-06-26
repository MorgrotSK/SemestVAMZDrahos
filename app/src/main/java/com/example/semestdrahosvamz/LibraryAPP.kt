package com.example.semestdrahosvamz

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.semestdrahosvamz.Data.AppContainer
import com.example.semestdrahosvamz.Data.AppDataContainer
import com.example.semestdrahosvamz.ui.navigation.LibraryNavHost
import com.example.semestdrahosvamz.ui.screens.library.LibraryScreen
import com.example.semestdrahosvamz.ui.theme.SemestDrahosVAMZTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SemestDrahosVAMZTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LibraryNavHost(navController = rememberNavController())
                }
            }
        }
    }
}




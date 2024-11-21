package com.example.roomproject

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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.roomproject.database.bd.DatabaseClient
import com.example.roomproject.screens.MainScreen
import com.example.roomproject.ui.theme.RoomProjectTheme
import com.example.roomproject.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val viewModel =
            ViewModelProvider(
                this,
                object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return MainViewModel(applicationContext) as T
                    }
                }
            ).get(MainViewModel::class.java)

        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(viewModel = viewModel )
        }
        val miReceta = Receta(nombre = "Pasta", ingredientes = "Pasta, tomate, carne", instrucciones = "Cocer la pasta, preparar salsa" )

        lifecycleScope.launch(Dispatchers.IO){
            val recipeDAO = DatabaseClient.getDataBase(applicationContext).recetasDAO()

            recipeDAO.insert(miReceta)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RoomProjectTheme {
        Greeting("Android")
    }
}
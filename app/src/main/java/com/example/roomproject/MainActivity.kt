package com.example.roomproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.roomproject.presentation.screens.MainScreen
import com.example.roomproject.presentation.screens.RecipeSearchScreen
import com.example.roomproject.presentation.theme.RoomProjectTheme
import com.example.roomproject.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.rememberNavController

sealed class Sites(val ruta: String){

    object HOME : Sites("Home")
    object SEARCH : Sites("Search")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // Hilt provee automáticamente el ViewModel
        val viewModel: MainViewModel by viewModels()

        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

          NavHost(navController = navController, startDestination = Sites.HOME.ruta){
              composable(Sites.HOME.ruta){
                  MainScreen(navC = navController, viewModel = viewModel)
              }
          
          composable(Sites.SEARCH.ruta){
              RecipeSearchScreen(navC = navController, recipeViewModel = viewModel)
          }
          }
            

            //   MainScreen(viewModel = viewModel )
        //    RecipeSearchScreen(recipeViewModel = viewModel)
        }
  /*      val miReceta = RecetaRoom(nombre = "Pasta", ingredientes = "Pasta, tomate, carne", instrucciones = "Cocer la pasta, preparar salsa" )

        lifecycleScope.launch(Dispatchers.IO){
            val recipeDAO = DatabaseClient.getDataBase(applicationContext).recetasDAO()

            recipeDAO.insert(miReceta)
        }

   */
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
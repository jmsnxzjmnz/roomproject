package com.example.roomproject.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.roomproject.Sites
import com.example.roomproject.domain.modelo.Receta
import com.example.roomproject.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navC: NavController ,viewModel: MainViewModel) {
    val recetas by viewModel.recipes.collectAsState(initial = emptyList())
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text("Lista de Recetas") },
                actions = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            viewModel.deleteAllRecipes()
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Borrar todas las recetas")
                    }

                    IconButton(onClick = {
                        coroutineScope.launch {
                            navC.navigate(Sites.SEARCH.ruta){
                                popUpTo(Sites.HOME.ruta)
                            }
                        }
                    }) {
                        Icon(Icons.Default.Search, contentDescription = "Ir a pantalla de buscador")
                    }
                }
            )
        }
    )  { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(recetas) { receta ->
                RecetaItem(receta)
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecetaItem(recetaRoom: Receta) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = "Nombre: ${recetaRoom.title}", style = MaterialTheme.typography.bodyLarge)
           // Text(text = "Instrucciones: ${recetaRoom.}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

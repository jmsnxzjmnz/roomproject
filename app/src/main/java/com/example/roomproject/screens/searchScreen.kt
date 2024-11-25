package com.example.roomproject.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.roomproject.remote.models.RecetaApi
import com.example.roomproject.viewmodel.MainViewModel


@Composable
fun RecipeSearchScreen(recipeViewModel: MainViewModel) {
    var query by remember { mutableStateOf("") }
    val recetas by recipeViewModel.recetasapi.collectAsState(initial = emptyList())

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Ingresa un ingrediente") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { recipeViewModel.searchRecipes(query) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Buscar Recetas")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(recetas) { receta ->
                RecipeCard(receta)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeCard(receta: RecetaApi) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
       elevation = CardDefaults.cardElevation(4.dp)

    ) {
        Row(modifier = Modifier.padding(16.dp)) {

            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = receta.title, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
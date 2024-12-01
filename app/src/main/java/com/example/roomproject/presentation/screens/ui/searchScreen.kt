package com.example.roomproject.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.roomproject.domain.modelo.Receta
import com.example.roomproject.presentation.viewmodel.MainViewModel


@Composable
fun RecipeSearchScreen(navC: NavController, recipeViewModel: MainViewModel) {
    var query by rememberSaveable { mutableStateOf("") }
    val recetas by recipeViewModel.recipes.collectAsState(initial = emptyList())

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
fun RecipeCard(receta: Receta) {
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
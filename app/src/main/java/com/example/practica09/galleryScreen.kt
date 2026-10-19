package com.example.practica09

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import androidx.compose.ui.res.painterResource

@Composable
fun GaleriaScreen() {
    // Estado para manejar la vista de detalle de la imagen seleccionada
    var selectedImage by remember { mutableStateOf<String?>(null) }

    // Lista de URLs de imágenes de prueba[cite: 3]
    val imagenes = listOf(
        "https://picsum.photos/id/1018/400/400",
        "https://picsum.photos/id/1015/400/400",
        "https://picsum.photos/id/1019/400/400",
        "https://picsum.photos/id/1020/400/400",
        "https://picsum.photos/id/1021/400/400",
        "https://picsum.photos/id/1022/400/400",
        "https://picsum.photos/id/1023/400/400",
        "https://picsum.photos/id/1024/400/400"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .padding(top = 32.dp)
    ) {
        Text(
            text = "Mi Galería",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )

        // Indicamos que queremos 2 columnas[cite: 3]
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(imagenes) { url ->
                ImagenItem(url = url) {
                    selectedImage = url // Actualiza el estado al tocar la imagen
                }
            }
        }
    }

    // Cuadro de diálogo emergente para abarcar el centro de la pantalla[cite: 4]
    selectedImage?.let { url ->
        Dialog(onDismissRequest = { selectedImage = null }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                AsyncImage(
                    model = url,
                    contentDescription = "Imagen en detalle",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun ImagenItem(url: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() }, // Modificador para que se abra la vista de detalle[cite: 4]
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        // Maneja la carga de red y el ciclo de vida[cite: 3]
        AsyncImage(
            model = url,
            contentDescription = "Imagen de la galería",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            // Indicadores visuales y manejo de errores nativos de Coil en Compose
            placeholder = painterResource(id = android.R.drawable.ic_menu_gallery),
            error = painterResource(id = android.R.drawable.ic_dialog_alert)
        )
    }
}
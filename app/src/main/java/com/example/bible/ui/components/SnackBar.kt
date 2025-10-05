package com.example.bible.ui.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CustomSnackBar(snackbarHostState: SnackbarHostState, textColor: Color, icon: ImageVector?) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(
                modifier = Modifier
                    .padding(16.dp)
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(8.dp),
                containerColor = Color.White,
                contentColor = textColor,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if(icon != null){
                        Icon(
                            imageVector = icon,
                            contentDescription = "Erro",
                            tint = Color.Red,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                    Text(text = data.visuals.message)
                }
            }
        }
    )
}
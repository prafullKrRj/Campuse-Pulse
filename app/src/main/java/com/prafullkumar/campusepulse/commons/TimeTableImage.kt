package com.prafullkumar.campusepulse.commons

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.prafullkumar.campusepulse.R

@Composable
fun TimeTableImage(data: String) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var originalSize by remember { mutableStateOf(IntSize.Zero) }

    val modifier = Modifier
        .graphicsLayer(
            scaleX = maxOf(1f, scale),
            scaleY = maxOf(1f, scale),
            translationX = offset.x,
            translationY = offset.y
        )
        .pointerInput(Unit) {
            detectTransformGestures { _, pan, zoom, _ ->
                scale *= zoom
                scale = maxOf(1f, scale)
                offset = if (scale == 1f) {
                    Offset.Zero
                } else {
                    val newOffset = offset + pan
                    Offset(
                        x = newOffset.x.coerceIn(-originalSize.width * (scale - 1), originalSize.width * (scale - 1)),
                        y = newOffset.y.coerceIn(-originalSize.height * (scale - 1), originalSize.height * (scale - 1))
                    )
                }
            }
        }

    Box(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(data).build(),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .onSizeChanged { size ->
                    if (originalSize == IntSize.Zero) {
                        originalSize = size
                    }
                    val minScale = minOf(size.width.toFloat() / originalSize.width, size.height.toFloat() / originalSize.height)
                    scale = maxOf(scale, minScale)
                },
            placeholder = painterResource(id = R.drawable.loading_img),
            error = painterResource(id = R.drawable.ic_broken_image)
        )
    }
}
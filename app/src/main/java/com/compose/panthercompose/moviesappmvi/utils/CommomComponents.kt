package com.compose.panthercompose.moviesappmvi.utils

import android.graphics.Bitmap
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.palette.graphics.Palette
import com.compose.panthercompose.pokemon.presentation.theme.typography


@Composable
fun HeadingSection(modifier: Modifier = Modifier, title: String = "", subtitle: String = "") {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (title.isNotEmpty()) {
            Text(text = title, style = typography.h6.copy(fontSize = 14.sp))
        }
        if (title.isNotEmpty()) {
            Text(text = subtitle, style = typography.subtitle2)
        }
        Divider()
    }
}

@Composable
fun TitleText(modifier: Modifier = Modifier, title: String) {
    Text(
        text = title,
        style = typography.h6.copy(fontSize = 14.sp),
        modifier = modifier.padding(8.dp)
    )
}

@Composable
fun SubtitleText(subtitle: String, modifier: Modifier = Modifier) {
    Text(text = subtitle, style = typography.subtitle2, modifier = modifier.padding(8.dp))
}

@Composable
fun RotateIcon(
    state: Boolean,
    asset: ImageVector,
    angle: Float,
    duration: Int,
    modifier: Modifier = Modifier
) {
    /*FaIcon(
        faIcon = FaIcons.Play,
        tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
        size = 20.dp,
        modifier = modifier
            .padding(2.dp)
            .graphicsLayer(
                rotationZ = animateFloatAsState(if (state) 0f else angle, tween(duration))
                    .value
            )
    )*/
}

@Preview
@Composable
fun PreviewHeading() {
    HeadingSection(title = "Title", subtitle = "this is subtitle")
}

fun generateDominantColorState(bitmap: Bitmap): Palette.Swatch {
    return Palette.Builder(bitmap)
        .resizeBitmapArea(0)
        .maximumColorCount(16)
        .generate()
        .swatches
        .maxByOrNull { swatch -> swatch.population }!!

}
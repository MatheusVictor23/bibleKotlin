package com.composables

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Flame: ImageVector
    get() {
        if (_Flame != null) return _Flame!!
        
        _Flame = ImageVector.Builder(
            name = "Flame",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(8.5f, 14.5f)
                arcTo(2.5f, 2.5f, 0f, false, false, 11f, 12f)
                curveToRelative(0f, -1.38f, -0.5f, -2f, -1f, -3f)
                curveToRelative(-1.072f, -2.143f, -0.224f, -4.054f, 2f, -6f)
                curveToRelative(0.5f, 2.5f, 2f, 4.9f, 4f, 6.5f)
                reflectiveCurveToRelative(3f, 3.5f, 3f, 5.5f)
                arcToRelative(7f, 7f, 0f, true, true, -14f, 0f)
                curveToRelative(0f, -1.153f, 0.433f, -2.294f, 1f, -3f)
                arcToRelative(2.5f, 2.5f, 0f, false, false, 2.5f, 2.5f)
            }
        }.build()
        
        return _Flame!!
    }

private var _Flame: ImageVector? = null


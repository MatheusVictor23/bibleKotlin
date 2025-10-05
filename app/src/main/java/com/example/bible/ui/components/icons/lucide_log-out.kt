package com.composables

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LogOut: ImageVector
    get() {
        if (_LogOut != null) return _LogOut!!
        
        _LogOut = ImageVector.Builder(
            name = "LogOut",
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
                moveTo(9f, 21f)
                horizontalLineTo(5f)
                arcToRelative(2f, 2f, 0f, false, true, -2f, -2f)
                verticalLineTo(5f)
                arcToRelative(2f, 2f, 0f, false, true, 2f, -2f)
                horizontalLineToRelative(4f)
                moveToRelative(7f, 14f)
                lineToRelative(5f, -5f)
                lineToRelative(-5f, -5f)
                moveToRelative(5f, 5f)
                horizontalLineTo(9f)
            }
        }.build()
        
        return _LogOut!!
    }

private var _LogOut: ImageVector? = null


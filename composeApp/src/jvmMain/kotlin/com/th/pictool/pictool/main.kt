package com.th.pictool.pictool

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.awt.FileDialog
import java.awt.Frame
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "pictool",
    ) {
        var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

        MenuBar {
            Menu("File") {
                Item(
                    "Open",
                    shortcut = KeyShortcut(Key.O),
                    onClick = {
                        val path = chooseImage()
                        if (path != null) {
                            // 读取图片
                            val img = ImageIO.read(File(path))
                            imageBitmap = img.toImageBitmap()
                        }
                    })
            }
        }
        Box() {
            if (imageBitmap != null) {
                Image(
                    bitmap = imageBitmap!!,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

fun BufferedImage.toImageBitmap(): ImageBitmap = this.toComposeImageBitmap()

// 使用 AWT 原生 FileDialog 选择图片
fun chooseImage(): String? {
    val fd = FileDialog(null as Frame?, "Choose an image", FileDialog.LOAD)
    fd.isVisible = true
    val file = fd.file
    val dir = fd.directory
    return if (file != null && dir != null) "$dir$file" else null
}
package com.sakhura.disqueramp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import disqueramp.shared.generated.resources.Res
import disqueramp.shared.generated.resources.img_banner_1
import disqueramp.shared.generated.resources.img_banner_2
import disqueramp.shared.generated.resources.img_banner_3
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

/**Home o inico del navbar*/
@Composable
fun InicioScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //Para los estados del sms
        val sms1State = remember { mutableStateOf(false) }
        val sms2State = remember { mutableStateOf(false) }

        Text("Soy inicio screen")
        ImageSwitcher()
        ButtonsPersonalizado(
            text = "Ver discos",
            color = Color(0xfffcc203),
            onClick = {
                sms1State.value = !sms1State.value
            })
        ButtonsPersonalizado(
            text = "Registrarse",
            color = Color(0xff0390fc),
            onClick = {
                sms2State.value = !sms2State.value
            })

        //Corutina para que desaparezca el sms
        LaunchedEffect(sms1State.value || sms2State.value) {
            delay(2000)
            sms1State.value = false
            sms2State.value = false
        }

        //Aparece y desaparece segun valores
        if (sms1State.value || sms2State.value) {
            Text("Navegue por el navbar ⬇️")
        }
    }
}

/**Imagen cambiante cada 3 segundos*/
@Composable
private fun ImageSwitcher() {
    val images = remember {
        listOf(
            Res.drawable.img_banner_1,
            Res.drawable.img_banner_2,
            Res.drawable.img_banner_3
        )
    }
    var currentImageIndex by remember { mutableStateOf(0) }
    val currentImage = painterResource(images[currentImageIndex])
    val imageCount = images.size
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            currentImageIndex++
            if (currentImageIndex >= imageCount) currentImageIndex = 0
        }
    }
    Image(
        painter = currentImage,
        contentDescription = "Imagen",
        modifier = Modifier.size(400.dp) // O ajusta el Modifier según necesites
    )
}

/**Boton personalizado para texto y color
 * @param text texto del boton
 * @param color color del boton*/
@Composable
private fun ButtonsPersonalizado(text: String, color: Color, onClick: () -> Unit) {
    Button(
        modifier = Modifier.padding(16.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        )
    ) {
        Text(text = text)
    }
}
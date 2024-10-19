package cl.bootcamp.chatboxgeminiapi.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cl.bootcamp.chatboxgeminiapi.model.MessageModel
import cl.bootcamp.chatboxgeminiapi.ui.theme.focusColor
import cl.bootcamp.chatboxgeminiapi.ui.theme.unfocusedColor

@Composable
fun MessageInput(
    onClick: (String) -> Unit,
) {
    var message by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier.weight(1f),
            value = message,
            onValueChange = { message = it },
            placeholder = { Text(text = "Escribe algo") },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = focusColor,
                unfocusedContainerColor = unfocusedColor,
                focusedTextColor = Color.White
            )
        )
        IconButton(
            onClick = {
                onClick(message)
                message = ""
            }
        ) {
            Icon(Icons.AutoMirrored.Default.Send, "Send")
        }
    }
}

@Composable
fun GlobeMessage(
    messageModel: MessageModel,
) {
    val roleModel = messageModel.role == "model"
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .align(
                        if (roleModel) Alignment.BottomStart else Alignment.BottomEnd
                    )
                    .padding(10.dp)
                    .clip(RoundedCornerShape(50f))
                    .background(if (roleModel) Color.Black else Color.DarkGray)
            ) {
                Text(
                    text = messageModel.message,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
        }
    }
}


@Composable
fun Title() {
    Text(text = "Chat Gemini", fontWeight = FontWeight.Bold, color = Color.White)
}
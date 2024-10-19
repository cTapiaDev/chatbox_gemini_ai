package cl.bootcamp.chatboxgeminiapi.viewModel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import cl.bootcamp.chatboxgeminiapi.BuildConfig
import cl.bootcamp.chatboxgeminiapi.model.ChatModel
import cl.bootcamp.chatboxgeminiapi.model.MessageModel
import cl.bootcamp.chatboxgeminiapi.room.AppDatabase
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GeminiViewModel(application: Application): AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "chat_bot"
    ).build()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apikey
    )

    private val chat by lazy {
        generativeModel.startChat()
    }

    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }

//    fun sendMessage(question: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                messageList.add(MessageModel(question, role = "user"))
//                val response = chat.sendMessage(question)
//                messageList.add(MessageModel(response.text.toString(), role = "model"))
//            } catch(e:Exception) {
//                messageList.add(MessageModel("Error en la conversación: ${e.message}", role = "model"))
//            }
//        }
//    }

    // Room

    fun sendMessage(question: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                messageList.add(MessageModel(question, role = "user"))
                val response = chat.sendMessage(question)
                messageList.add(MessageModel(response.text.toString(), role = "model"))
                // room
                val chatDao = db.chatDao()
                chatDao.insertChat(item = ChatModel(chat = question, role = "user"))
                chatDao.insertChat(item = ChatModel(chat = response.text.toString(), role = "model"))
            } catch(e:Exception) {
                messageList.add(MessageModel("Error en la conversación: ${e.message}", role = "model"))
            }
        }
    }

    fun loadChat() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val chatDao = db.chatDao()
                val savedChat = chatDao.getChat()
                messageList.clear()
                for ( chat in savedChat ) {
                    messageList.add(MessageModel(message = chat.chat, role = chat.role))
                }
            }
        } catch (e:Exception) {
            messageList.add(MessageModel("Error en cargar el chat: ${e.message}", role = "model"))
        }
    }

    fun deleteChat() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val chatDao = db.chatDao()
                chatDao.deteleChat()
                messageList.clear()
            } catch (e:Exception) {
                messageList.add(MessageModel("Error al borrar el chat: ${e.message}", role = "model"))
            }
        }
    }
}
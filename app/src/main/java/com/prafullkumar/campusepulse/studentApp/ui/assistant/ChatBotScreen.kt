package com.prafullkumar.campusepulse.studentApp.ui.assistant


import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prafullkumar.campusepulse.R

@Composable
fun AiAssistantScreen(viewModel: AssistantsViewModel) {
    val chatState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    Scaffold (
        bottomBar = {
            PromptField(viewModel = viewModel) {
                focusManager.clearFocus()
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .heightIn(min = LocalConfiguration.current.screenHeightDp.dp)
    ){ paddingValues ->
        if (chatState is ChatUiState.Initial){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Hi, I am your assistant. How can I help you?",
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 24.sp
                )
            }
        }
        ChatScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            messages = viewModel.messages,
            state = chatState
        )
    }
}
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    messages: List<Message> = emptyList(),
    state: ChatUiState = ChatUiState.Initial
) {

    LazyColumn(modifier = modifier, reverseLayout = true) {
        item {
            if (state == ChatUiState.Loading) {
                CircularProgressIndicator(modifier = Modifier.padding(horizontal = 8.dp))
            }
        }
        messages.forEach { message ->
            item {
                ChatBubble(message = message)
            }
        }
    }
}
@Composable
fun ChatBubble(modifier: Modifier = Modifier, message: Message) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = if (message.person == Participant.USER) {
            Arrangement.End
        } else {
            Arrangement.Start
        }
    ) {
        Card(
            modifier = Modifier
                .padding(
                    vertical = 8.dp,
                )
                .padding(
                    start = if (message.person == Participant.USER) 32.dp else 8.dp,
                    end = if (message.person == Participant.USER) 8.dp else 32.dp,
                )
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = when (message.messageType) {
                    MessageType.USER -> MaterialTheme.colorScheme.secondaryContainer
                    MessageType.ERROR -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.tertiaryContainer
                }
            )
        ){
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = message.text)
                Spacer(modifier = Modifier.heightIn(4.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = {
                        shareText(context, message.text)
                    }, modifier = Modifier.padding(end = 4.dp)) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = null,
                            Modifier.size(18.dp)
                        )
                    }
                    IconButton(onClick = {
                        clipboardManager.setText(AnnotatedString(message.text))
                        Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_content_copy_24),
                            contentDescription = null,
                            Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}
fun shareText(context: Context, text: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    val chooserIntent = Intent.createChooser(shareIntent, null)
    context.startActivity(chooserIntent)
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PromptField(viewModel: AssistantsViewModel, onPrompted: () -> Unit = {}) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp),
        value = text,
        onValueChange = { text = it },
        label = { Text("Query") },
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = {
                    viewModel.sendMessage(text)
                    text = ""
                    keyboardController?.hide()
                    onPrompted()
                }) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = null)
                }
            }
        },
        shape = MaterialTheme.shapes.medium
    )
}
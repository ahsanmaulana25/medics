package com.example.medics.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medics.R
import androidx.compose.runtime.mutableStateListOf
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

data class ChatMessage(
    val id: String,
    val senderName: String,
    val senderImageRes: Int?,
    val message: String,
    val timestamp: String,
    val isSentByMe: Boolean,
    val isRead: Boolean = false
)

data class ChatDoctorInfo(
    val name: String,
    val specialization: String,
    val imageRes: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    chatPartnerId: String?,
    chatPartnerName: String?,
    chatPartnerImageRes: Int?,
    onNavigateBack: () -> Unit,
    onVideoCallClicked: () -> Unit,
    onVoiceCallClicked: () -> Unit
) {
    val decodedChatPartnerName = remember(chatPartnerName) {
        chatPartnerName?.let {
            URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
        } ?: "Nama Dokter"
    }

    val doctorInfo = remember(chatPartnerId, decodedChatPartnerName) {
        when (chatPartnerId) {
            "1" -> ChatDoctorInfo(decodedChatPartnerName, "Online", chatPartnerImageRes ?: R.drawable.dr_marcus_horizon)
            "2" -> ChatDoctorInfo(decodedChatPartnerName, "Skin Specialist", chatPartnerImageRes ?: R.drawable.dr_stevi_jessi)
            "3" -> ChatDoctorInfo(decodedChatPartnerName, "Cardiologist", chatPartnerImageRes ?: R.drawable.dr_maria_elena)
            else -> ChatDoctorInfo(decodedChatPartnerName, "Online", chatPartnerImageRes ?: R.drawable.dr_marcus_horizon)
        }
    }

    val chatHistory = remember {
        mutableStateListOf(
            ChatMessage("msg2", doctorInfo.name, doctorInfo.imageRes, "Hello, How can i help you?", "10 min ago", false, true),
            ChatMessage("msg3", "Saya", null, "I have suffering from headache and cold for 3 days, i took 2 tablets of dolo, but still pain", "10 min ago", true, true),
            ChatMessage("msg4", doctorInfo.name, doctorInfo.imageRes, "Ok, Do you have fever? is the headchace severe", "5 min ago", false, true),
            ChatMessage("msg5", "Saya", null, "I don,t have any fever, but headchace is painful", "5 min ago", true, true),
            ChatMessage("msg6", doctorInfo.name, doctorInfo.imageRes, "....", "Online", false, false)
        )
    }

    var messageInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        doctorInfo.imageRes?.let {
                            Image(
                                painter = painterResource(id = it),
                                contentDescription = "Doctor Profile",
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color.Gray),
                                contentScale = ContentScale.Crop
                            )
                        } ?: run {
                            Image(
                                painter = painterResource(id = R.drawable.dr_marcus_horizon),
                                contentDescription = "Default Doctor Profile",
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color.Gray),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(doctorInfo.name, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                            Text(doctorInfo.specialization, fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onVideoCallClicked) {
                        Icon(Icons.Default.Videocam, contentDescription = "Video Call", tint = Color.White)
                    }
                    IconButton(onClick = onVoiceCallClicked) {
                        Icon(Icons.Default.Call, contentDescription = "Voice Call", tint = Color.White)
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More options", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF008080)
                )
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = messageInput,
                    onValueChange = { messageInput = it },
                    placeholder = { Text("Type message ...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    trailingIcon = {
                        IconButton(onClick = { }) {
                            Icon(Icons.Default.AttachFile, contentDescription = "Attach file", tint = Color.Gray)
                        }
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        if (messageInput.isNotBlank()) {
                            val newMessage = ChatMessage(
                                id = "msg${chatHistory.size + 1}",
                                senderName = "Saya",
                                senderImageRes = null,
                                message = messageInput,
                                timestamp = "Now",
                                isSentByMe = true,
                                isRead = false
                            )
                            chatHistory.add(newMessage)
                            messageInput = ""
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF008080))
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Send message", tint = Color.White)
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F7FF))
                .padding(horizontal = 8.dp),
            reverseLayout = true,
            verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Bottom)
        ) {
            item {
                if (chatHistory.firstOrNull()?.message?.startsWith("Consultation Start") == true) {
                    ConsultationStartBubble(chatHistory.first().message)
                }
            }

            items(chatHistory.filter { !it.message.startsWith("Consultation Start") }.reversed()) { message ->
                MessageBubble(message = message)
            }
        }
    }
}

@Composable
fun MessageBubble(message: ChatMessage) {
    val bubbleColor = if (message.isSentByMe) Color(0xFF008080) else Color.White
    val textColor = if (message.isSentByMe) Color.White else Color.Black
    val cornerShape = if (message.isSentByMe) {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 4.dp)
    } else {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 4.dp)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = if (message.isSentByMe) 60.dp else 0.dp,
                end = if (message.isSentByMe) 0.dp else 60.dp
            ),
        horizontalArrangement = if (message.isSentByMe) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!message.isSentByMe) {
            message.senderImageRes?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = message.senderName,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        Column(
            horizontalAlignment = if (message.isSentByMe) Alignment.End else Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .clip(cornerShape)
                    .background(bubbleColor)
                    .padding(10.dp)
            ) {
                Text(
                    text = message.message,
                    color = textColor,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (message.isSentByMe && message.isRead) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Message read",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Message read",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                } else if (message.isSentByMe && !message.isRead) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Message sent",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text(
                    text = message.timestamp,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
        if (message.isSentByMe) {
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
fun ConsultationStartBubble(message: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFE0F3EF))
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = message,
                color = Color.Black.copy(alpha = 0.7f),
                fontSize = 13.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, name = "Chat Screen Preview")
@Composable
fun ChatScreenPreview() {
    ChatScreen(
        chatPartnerId = "1",
        chatPartnerName = "Dr. Marcus Horizon",
        chatPartnerImageRes = R.drawable.dr_marcus_horizon,
        onNavigateBack = {},
        onVideoCallClicked = {},
        onVoiceCallClicked = {}
    )
}
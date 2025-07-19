package com.example.medics.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Badge
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medics.R

data class ChatConversation(
    val id: String,
    val doctorName: String,
    val doctorProfileImageRes: Int,
    val lastMessage: String,
    val lastMessageTimestamp: String,
    val unreadCount: Int = 0
)

@Composable
fun MessagesScreen(
    onNavigateToChat: (conversationId: String, chatPartnerName: String, chatPartnerImageRes: Int) -> Unit
) {
    val sampleConversations = remember {
        listOf(
            ChatConversation(
                id = "1",
                doctorName = "Dr. Marcus Horizon",
                doctorProfileImageRes = R.drawable.dr_marcus_horizon,
                lastMessage = "Okay, I understand. How long have you been experiencing this?",
                lastMessageTimestamp = "10:03 AM",
                unreadCount = 2
            ),
            ChatConversation(
                id = "2",
                doctorName = "Dr. Alysa Hana",
                doctorProfileImageRes = R.drawable.dr_stevi_jessi,
                lastMessage = "Sure, I can help with that. Please wait a moment.",
                lastMessageTimestamp = "Yesterday",
                unreadCount = 0
            ),
            ChatConversation(
                id = "3",
                doctorName = "Dr. Walter White",
                doctorProfileImageRes = R.drawable.dr_maria_elena,
                lastMessage = "I'm prescribing you a new medication.",
                lastMessageTimestamp = "Mon",
                unreadCount = 5
            ),
            ChatConversation(
                id = "4",
                doctorName = "Dr. Gerty Cori",
                doctorProfileImageRes = R.drawable.dr_marcus_horizon,
                lastMessage = "Your test results are back and they look good!",
                lastMessageTimestamp = "12/03/24",
                unreadCount = 0
            )
        )
    }

    if (sampleConversations.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("No messages yet.", fontSize = 18.sp, color = Color.Gray)
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(sampleConversations) { conversation ->
                ConversationItem(
                    conversation = conversation,
                    onClick = {
                        onNavigateToChat(conversation.id, conversation.doctorName, conversation.doctorProfileImageRes)
                        println("Clicked on conversation with ${conversation.doctorName}")
                    }
                )
            }
        }
    }
}

@Composable
fun ConversationItem(
    conversation: ChatConversation,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = conversation.doctorProfileImageRes),
            contentDescription = "${conversation.doctorName} profile picture",
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = conversation.doctorName,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = conversation.lastMessage,
                fontSize = 15.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = conversation.lastMessageTimestamp,
                fontSize = 13.sp,
                color = if (conversation.unreadCount > 0) Color(0xFF008080) else Color.Gray
            )
            if (conversation.unreadCount > 0) {
                Spacer(modifier = Modifier.height(4.dp))
                Badge(
                    containerColor = Color(0xFF008080),
                    contentColor = Color.White
                ) {
                    Text(
                        text = conversation.unreadCount.toString(),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }
        }
    }
    Divider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 0.5.dp)
}

@Preview(showBackground = true, name = "Messages Screen Preview")
@Composable
fun MessagesScreenPreview() {
    MessagesScreen(
        onNavigateToChat = { conversationId, chatPartnerName, chatPartnerImageRes ->
            println("Preview: Navigate to chat with ID: $conversationId, Name: $chatPartnerName, Image: $chatPartnerImageRes")
        }
    )
}

@Preview(showBackground = true, name = "Conversation Item Preview")
@Composable
fun ConversationItemPreview() {
    val sampleConvo = ChatConversation(
        id = "1",
        doctorName = "Dr. Marcus Horizon",
        doctorProfileImageRes = R.drawable.dr_marcus_horizon,
        lastMessage = "Okay, I understand. How long have you been experiencing this? This is a very long message to see how ellipsis works.",
        lastMessageTimestamp = "10:03 AM",
        unreadCount = 3
    )
    ConversationItem(conversation = sampleConvo, onClick = {})
}

@Preview(showBackground = true, name = "Conversation Item No Unread Preview")
@Composable
fun ConversationItemNoUnreadPreview() {
    val sampleConvo = ChatConversation(
        id = "2",
        doctorName = "Dr. Alysa Hana",
        doctorProfileImageRes = R.drawable.dr_stevi_jessi,
        lastMessage = "Sure, I can help with that.",
        lastMessageTimestamp = "Yesterday",
        unreadCount = 0
    )
    ConversationItem(conversation = sampleConvo, onClick = {})
}
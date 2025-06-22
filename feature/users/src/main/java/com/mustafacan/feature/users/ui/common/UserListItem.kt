package com.mustafacan.feature.users.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mustafacan.core.domain.model.socket.OnlineUser
import com.mustafacan.core.domain.model.users.User
import com.mustafacan.core.ui.R
import com.mustafacan.core.ui.theme.BackgroundDark
import com.mustafacan.core.ui.theme.CardButtonTextColor
import com.mustafacan.core.ui.theme.CardItemBackgroundColor
import com.mustafacan.core.ui.theme.CardItemTextColor

@Composable
fun UserItem(user: User, buttonClicked: () -> Unit, isSelf: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = if (isSelf) BorderStroke(2.dp, Color.Green) else null,
        colors = CardDefaults.cardColors(containerColor = CardItemBackgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(BackgroundDark),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = user.username.first().uppercase(),
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    color = CardButtonTextColor
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = user.username,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                color = CardItemTextColor
            )

            Spacer(modifier = Modifier.width(8.dp))


            Button(
                onClick = {
                    if (!isSelf) buttonClicked()
                },
                modifier = Modifier.height(32.dp),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp),
                colors = ButtonDefaults.buttonColors(containerColor =  BackgroundDark)
            ) {
                Text(
                    text = if (isSelf) stringResource(R.string.you) else stringResource(R.string.chat),
                    style = TextStyle(fontSize = 12.sp),
                    color = CardButtonTextColor,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun UserItemForOnlineUsers(user: OnlineUser, buttonClicked: () -> Unit, isSelf: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = if (isSelf) BorderStroke(2.dp, Color.Green) else null,
        colors = CardDefaults.cardColors(containerColor = CardItemBackgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(BackgroundDark),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = user.name.first().uppercase(),
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    color = CardButtonTextColor
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = user.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                color = CardItemTextColor
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (!isSelf) buttonClicked()
                },
                modifier = Modifier.height(32.dp),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BackgroundDark)
            ) {
                Text(
                    text = if (isSelf) stringResource(R.string.you) else stringResource(R.string.chat),
                    style = TextStyle(fontSize = 12.sp),
                    color = CardButtonTextColor,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun HorizontalUserItem(user: OnlineUser, buttonClicked: () -> Unit, isSelf: Boolean) {
    Card(
        modifier = Modifier
            .width(100.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = if (isSelf) BorderStroke(2.dp, Color.Green) else null,
        colors = CardDefaults.cardColors(containerColor = CardItemBackgroundColor)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(BackgroundDark),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = user.name.first().uppercase(),
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                    color = CardButtonTextColor
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = user.name,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                color = CardItemTextColor
            )

            Spacer(modifier = Modifier.height(2.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF4CAF50))
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.connection_state_online),
                    style = MaterialTheme.typography.labelSmall,
                    color = CardItemTextColor
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Button(
                onClick = {
                    if (!isSelf) buttonClicked()
                },
                modifier = Modifier.height(32.dp),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BackgroundDark)
            ) {
                Text(
                    text = if (isSelf) stringResource(R.string.you) else stringResource(R.string.chat),
                    style = TextStyle(fontSize = 12.sp),
                    color = CardButtonTextColor,
                    maxLines = 1,
                )
            }
        }
    }
}
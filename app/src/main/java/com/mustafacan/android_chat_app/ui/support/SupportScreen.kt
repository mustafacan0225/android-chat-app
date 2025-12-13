package com.mustafacan.android_chat_app.ui.support

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mustafacan.core.ui.R
import com.mustafacan.core.ui.animation.lottie.LottieAnimation
import com.mustafacan.core.ui.theme.CardItemBackgroundColor
import com.mustafacan.core.ui.theme.CardItemTextColor
import com.mustafacan.core.ui.theme.ChatAppTheme

/*@Composable
fun SupportScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("Mustafa Can")
        Text("mustafacan0225@gmail.com")
        Text("Türkiye")
        LottieAnimation(R.raw.contact, modifier = Modifier.weight(1f))
    }

}*/
@Composable
fun SupportScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Profil Kartı
            Card(
                modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = CardItemBackgroundColor)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    ProfileRow(
                        icon = Icons.Default.Person,
                        title = "Mustafa Can"
                    )

                    ProfileRow(
                        icon = Icons.Default.Email,
                        title = "mustafacan0225@gmail.com"
                    )

                    ProfileRow(
                        icon = Icons.Default.LocationOn,
                        title = "Türkiye"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LottieAnimation(R.raw.contact, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun ProfileRow(
    icon: ImageVector,
    title: String
) {
    Row (
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF64B5F6),
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = title,
            color = CardItemTextColor,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}


@Preview(
    showBackground = true,
    name = "Support Screen - Light"
)
@Composable
fun SupportScreenPreview() {
    ChatAppTheme(darkTheme = false) {
        Surface {
            SupportScreen()
        }
    }
}
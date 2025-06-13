package com.mustafacan.core.ui.component.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mustafacan.core.ui.animation.shimmer.ShimmerEffect

@Composable
fun HorizontalCircleShimmer() {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        repeat(10) {
            ShimmerEffect(
                modifier = Modifier
                    .width(80.dp)
                    .height(100.dp)
                    .padding(5.dp)
                    .background(Color.LightGray, RoundedCornerShape(12)),
                durationMillis = 1000
            )
        }
    }
}

@Composable
fun VerticalRectangleShimmer() {
    LazyColumn {
        repeat(10) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ShimmerEffect(
                            Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray)
                        )

                        ShimmerEffect(
                            Modifier
                                .fillMaxWidth()
                                .height(20.dp)
                                .padding(start = 12.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.LightGray)
                        )

                    }
                }

            }

        }
    }

}
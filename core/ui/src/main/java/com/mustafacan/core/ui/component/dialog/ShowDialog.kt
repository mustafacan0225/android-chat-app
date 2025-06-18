package com.mustafacan.core.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mustafacan.core.ui.R
import com.mustafacan.core.ui.theme.BackgroundDark
import com.mustafacan.core.ui.theme.CardButtonTextColor
import com.mustafacan.core.ui.theme.CardItemBackgroundColor
import com.mustafacan.core.ui.theme.TitleTextColor

@Composable
fun ShowDialog(
    dialogModel: DialogModel
) {
    Dialog(
        onDismissRequest = { if (dialogModel.isCancelable) dialogModel.onDismiss },
        properties = DialogProperties(
            dismissOnBackPress = dialogModel.isCancelable,
            dismissOnClickOutside = dialogModel.isCancelable
        )
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = CardItemBackgroundColor,
            tonalElevation = 6.dp,
            shadowElevation = 10.dp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier.background(CardItemBackgroundColor)
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.dialog_title_default),
                    style = MaterialTheme.typography.headlineSmall,
                    color = TitleTextColor,
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = dialogModel.message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TitleTextColor,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    when (dialogModel.dialogType) {
                        DialogType.Info -> {
                            Button(
                                onClick = { dialogModel.onConfirm?.invoke() },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = CardButtonTextColor,
                                    containerColor = BackgroundDark
                                )
                            ) {
                                Text(text = dialogModel.confirmText ?: stringResource(android.R.string.ok))

                            }
                        }

                        DialogType.Confirm -> {
                            Button(
                                onClick = { dialogModel.onCancel?.invoke() },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = CardButtonTextColor,
                                    containerColor = BackgroundDark
                                )
                            ) {
                                Text(text = dialogModel.cancelText ?: stringResource(android.R.string.cancel))
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Button(
                                onClick = { dialogModel.onConfirm?.invoke() },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = CardButtonTextColor,
                                    containerColor = BackgroundDark
                                )
                            ) {
                                Text(
                                    text = dialogModel.confirmText ?: stringResource(android.R.string.ok)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


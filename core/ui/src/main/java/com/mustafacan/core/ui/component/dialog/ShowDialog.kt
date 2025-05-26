package com.mustafacan.core.ui.component.dialog

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mustafacan.core.ui.R

@Composable
fun ShowDialog(
    message: String,
    dialogType: DialogType = DialogType.Info,
    onConfirm: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null,
    onDismiss: () -> Unit,
    confirmText: String = stringResource(android.R.string.ok),
    cancelText: String = stringResource(android.R.string.cancel),
    isCancelable: Boolean = true
) {
    Dialog(
        onDismissRequest = { if (isCancelable) onDismiss },
        properties = DialogProperties(
            dismissOnBackPress = isCancelable,
            dismissOnClickOutside = isCancelable
        )
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            shadowElevation = 10.dp,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                // Başlık
                Text(
                    text = stringResource(R.string.dialog_title_default),
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black,
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Mesaj
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Butonlar
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    when (dialogType) {
                        DialogType.Info -> {
                            Button(
                                onClick = { onConfirm?.invoke() },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = MaterialTheme.colorScheme.surface,
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text(text = confirmText)
                            }
                        }

                        DialogType.Confirm -> {
                            Button(
                                onClick = { onCancel?.invoke() },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = MaterialTheme.colorScheme.surface,
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text(text = cancelText)
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Button(
                                onClick = { onConfirm?.invoke() },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = MaterialTheme.colorScheme.surface,
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text(
                                    text = confirmText
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


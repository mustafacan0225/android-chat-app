package com.mustafacan.core.ui.component.searchbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mustafacan.core.ui.R
import com.mustafacan.core.ui.component.textfield.DefaultTextFieldColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchedText: String,
    onSearch: (query: String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val iconColor = if (isFocused) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            //.padding(top = 10.dp)
    ) {
        val (searchBar) = createRefs()

        SearchBar(
            modifier = Modifier.constrainAs(searchBar) {
                centerVerticallyTo(parent)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            expanded = false,
            onExpandedChange = { },
            inputField = {
                OutlinedTextField(
                    value = searchedText,
                    onValueChange = {
                        onSearch(it)
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.search_by_username))
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search Icon", tint = iconColor)
                    },
                    trailingIcon = {
                        if (searchedText.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Icon",
                                modifier = Modifier.clickable {
                                    onSearch("")
                                },
                                tint = iconColor
                            )
                        }
                    },
                    singleLine = true,
                    colors = DefaultTextFieldColors,
                    shape = RoundedCornerShape(50), // %50 oval köşeler
                    modifier = Modifier.fillMaxWidth(),
                    interactionSource = interactionSource,
                )
            },colors = SearchBarDefaults.colors(
                containerColor = Color.Transparent,
                dividerColor = Color.Transparent   
            ),
            shape = RoundedCornerShape(50) // SearchBar kendisi de oval olsun
        ) {}

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemoteSearchBar(searchedText: String,
              onSearch: () -> Unit, onValueChange: (query: String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val iconColor = if (isFocused) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (searchBar, textViewCancel) = createRefs()

        SearchBar(
            modifier = Modifier.constrainAs(searchBar) {
                centerVerticallyTo(parent)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            expanded = false,
            onExpandedChange = { },
            inputField = {
                OutlinedTextField(
                    value = searchedText,
                    onValueChange = {
                        onValueChange(it)
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.search_by_username))
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search Icon", tint = iconColor)
                    },
                    trailingIcon = {
                        if (searchedText.isNotEmpty()) {
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp) // ikonlar arası boşluk
                            ) {
                                Icon(
                                    Icons.Filled.Clear,
                                    contentDescription = "Search Apply Icon",
                                    modifier = Modifier.clickable {
                                        onValueChange("")
                                    },
                                    tint = iconColor)

                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "Search Apply Icon",
                                    modifier = Modifier.padding(end = 10.dp).clickable { onSearch() },
                                    tint = iconColor)


                            }

                        }


                    },
                    singleLine = true,
                    colors = DefaultTextFieldColors,
                    shape = RoundedCornerShape(50), // %50 oval köşeler
                    modifier = Modifier.fillMaxWidth(),
                    interactionSource = interactionSource,
                )
            },colors = SearchBarDefaults.colors(
                containerColor = Color.Transparent,
                dividerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(50) // SearchBar kendisi de oval olsun
        ) {}


    }
}


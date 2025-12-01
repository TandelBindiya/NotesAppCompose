package com.bindiya.noteapp.prensentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bindiya.noteapp.R

@Composable
fun HeaderSection(
    searchText: String,
    onSearchTextChanged: (String) -> Unit
) {
    Column(modifier = Modifier.padding(all= 16.dp)) {
        Text(
            text = stringResource(R.string.label_organize_your_notes_with_tags_and_search),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = searchText,
            onValueChange = onSearchTextChanged,
            placeholder = { Text(stringResource(R.string.hnt_search_notes_by_title_body_or_tags)) },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(24.dp), // Match the rounded look in the image
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp) // Standard height for a text field
        )
    }
}
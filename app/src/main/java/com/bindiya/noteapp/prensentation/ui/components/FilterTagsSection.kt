package com.bindiya.noteapp.prensentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bindiya.noteapp.prensentation.viewmodel.TagsViewModel

@Composable
fun FilterTagsSection(
    tagsViewModel: TagsViewModel= hiltViewModel(),
    selectedTag: String?,
    onTagSelected: (String?) -> Unit
) {
    val tags by tagsViewModel.tags.collectAsState()

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = "Filter by tags:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tags.size) {
                tags[it].let {tag->
                    FilterChip(
                        selected = selectedTag == tag,
                        onClick = {
                            onTagSelected(if (selectedTag == tag) null else tag)
                        },
                        label = { Text(tag) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            selected = selectedTag==tag,
                            enabled = true,
                            borderColor = if (selectedTag == tag) Color.Transparent else MaterialTheme.colorScheme.outline
                        )
                    )
                }

            }
        }
    }
}
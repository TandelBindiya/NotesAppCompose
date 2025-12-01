package com.bindiya.noteapp.prensentation.ui.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.bindiya.noteapp.R
import com.bindiya.noteapp.core.model.Note
import kotlinx.coroutines.flow.Flow

@Composable
fun NotesListWithPaging(
    notesFlow: Flow<PagingData<Note>>,
    scrollState: ScrollState,
    onDeleteClicked: (Note) -> Unit
) {

    val notes = notesFlow.collectAsLazyPagingItems()
    val loadState = notes.loadState

    val isAppending = loadState.append is LoadState.Loading

    val LoadMoreThreshold = 300 // Increase threshold for safety margin
    
    val shouldLoadMore = (scrollState.maxValue - scrollState.value) < LoadMoreThreshold && !isAppending

    LaunchedEffect(scrollState.value) {
        if (shouldLoadMore) {
            if (notes.itemCount > 0) {
                 notes[notes.itemCount - 1] 
            }
        }
    }
    
    when (loadState.refresh) {
        is LoadState.Loading -> {
            Box(Modifier
                .fillMaxWidth()
                .height(200.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is LoadState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Failed to load notes.")
                Spacer(Modifier.height(8.dp))
                Button(onClick = { notes.retry() }) {
                    Text("Retry")
                }
            }
        }
        LoadState.NotLoading(endOfPaginationReached = false) -> {
            (0 until notes.itemCount).forEach { index ->
                val note = notes[index]
                if (note != null) {
                    NoteItem(note = note, onDeleteClicked = onDeleteClicked)
                } else {
                    Text("Loading item...")
                }
            }
            
            if (isAppending) {
                Box(Modifier
                    .fillMaxWidth()
                    .padding(16.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            val appendError = loadState.append is LoadState.Error
            if (appendError) {
                Box(Modifier
                    .fillMaxWidth()
                    .padding(16.dp), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(stringResource(R.string.error_loading_next_page))
                        Button(onClick = { notes.retry() }) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
        else -> Unit
    }
}

@Composable
fun NoteItem(note: Note, onDeleteClicked: (Note) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = note.body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    items(note.tags.size) {
                        TagChip(note.tags[it])
                    }
                }
                Spacer(Modifier.width(8.dp))

                Text(
                    text = note.createdAt,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Delete Button Icon
        IconButton(onClick = { onDeleteClicked(note) }) {
            Icon(
                Icons.Outlined.Delete,
                contentDescription = "Delete note",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
    Divider(modifier = Modifier.padding(horizontal = 16.dp))
}

@Composable
fun TagChip(tag: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Text(
            text = tag,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}



package com.bindiya.noteapp.domain.usecases

import com.bindiya.noteapp.core.model.Note
import com.bindiya.noteapp.domain.repository.NoteRepository
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(private val repo: NoteRepository) {
   suspend operator fun invoke(note: Note) = repo.insertNote(note)
}
package com.bindiya.noteapp.domain.usecases

import com.bindiya.noteapp.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
   private val repository: NoteRepository
) {
   suspend operator fun invoke(id: Long) {
      repository.deleteNote(id)
   }
}
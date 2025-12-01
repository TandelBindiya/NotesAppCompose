package com.bindiya.noteapp.domain.usecases

import com.bindiya.noteapp.domain.repository.NoteRepository
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(private val repo: NoteRepository) {
   operator fun invoke(query: String?, tag: String?) = repo.getNotesPaged(query,tag)
}


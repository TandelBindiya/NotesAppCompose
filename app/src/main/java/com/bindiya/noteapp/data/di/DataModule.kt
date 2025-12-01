package com.bindiya.noteapp.data.di

import android.content.Context
import androidx.room.Room
import com.bindiya.noteapp.core.model.Converters
import com.bindiya.noteapp.data.NoteDao
import com.bindiya.noteapp.data.NoteDatabase
import com.bindiya.noteapp.data.NoteRepositoryImpl
import com.bindiya.noteapp.domain.repository.NoteRepository
import com.bindiya.noteapp.domain.usecases.DeleteNoteUseCase
import com.bindiya.noteapp.domain.usecases.GetNotesUseCase
import com.bindiya.noteapp.domain.usecases.InsertNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context): NoteDatabase =
        Room.databaseBuilder(ctx, NoteDatabase::class.java, "notes.db")
            .build()

    @Provides
    fun provideNoteDao(db: NoteDatabase): NoteDao = db.noteDao()

    @Provides
    fun provideNoteRepository(dao: NoteDao): NoteRepository = NoteRepositoryImpl(dao)

    @Provides
    fun provideDeleteNoteUseCase(repository: NoteRepository): DeleteNoteUseCase =
        DeleteNoteUseCase(repository)

    @Provides
    fun provideGetNotesUseCase(repository: NoteRepository): GetNotesUseCase =
        GetNotesUseCase(repository)

    @Provides
    fun provideInsertNoteUseCase(repository: NoteRepository): InsertNoteUseCase =
        InsertNoteUseCase(repository)

}

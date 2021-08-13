package com.fahim.demo.di

import android.content.Context
import androidx.room.Room
import com.fahim.demo.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
object TestAppModule {

    @Provides
    @Named("testDb")
    fun injectInMemoryRoom(@ApplicationContext context: Context) = Room.inMemoryDatabaseBuilder(
        context,
        AppDatabase::class.java
    ).allowMainThreadQueries().build()
}
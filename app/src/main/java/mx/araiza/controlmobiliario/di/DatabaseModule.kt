package mx.araiza.controlmobiliario.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mx.araiza.controlmobiliario.data.database.FurnitureDB
import mx.araiza.controlmobiliario.data.database.dao.FurnitureDAO
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val FURNITURE_DATABASE_NAME = "furniture_database"
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext ctx : Context) :FurnitureDB =
        Room.databaseBuilder(ctx, FurnitureDB::class.java, FURNITURE_DATABASE_NAME)
        .fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideFurnitureDao(db :FurnitureDB) :FurnitureDAO = db.getFurnitureDAO()
}
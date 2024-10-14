package com.khoirulfahmi.kotlinportfolio

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteHadithDao {
    @Query("SELECT * FROM favorite_hadiths")
    fun getAllFavorites(): Flow<List<FavoriteHadith>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteHadith: FavoriteHadith)

    @Delete
    suspend fun deleteFavorite(favoriteHadith: FavoriteHadith)

    @Query("SELECT * FROM favorite_hadiths WHERE id = :id")
    suspend fun getFavoriteById(id: String): FavoriteHadith?
}

@Database(entities = [FavoriteHadith::class], version = 1)
abstract class HadithDatabase : RoomDatabase() {
    abstract fun favoriteHadithDao(): FavoriteHadithDao

    companion object {
        private var instance: HadithDatabase? = null

        fun getInstance(context: android.content.Context): HadithDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    HadithDatabase::class.java,
                    "hadith_database"
                ).build().also { instance = it }
            }
        }
    }
}
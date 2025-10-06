package com.example.bible.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.bible.data.local.dao.BibleDao
import com.example.bible.data.local.dao.QuizzDao
import com.example.bible.data.local.entity.BookEntity
import com.example.bible.data.local.entity.ChapterEntity
import com.example.bible.data.local.entity.DailyVerseEntity
import com.example.bible.data.local.entity.LastChapterEntity
import com.example.bible.data.local.entity.QuizzEntity
import com.example.bible.data.local.entity.VerseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        BookEntity::class,
        ChapterEntity::class,
        VerseEntity::class,
        LastChapterEntity::class,
        DailyVerseEntity::class,
        QuizzEntity::class
    ],
    version = 2,
    exportSchema = true,

    )
abstract class BibleDatabase : RoomDatabase() {
    abstract fun bibleDao(): BibleDao
    abstract fun quizzDao(): QuizzDao

    companion object {
        @Volatile private var INSTANCE: BibleDatabase? = null

        fun getDatabase(context: Context): BibleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BibleDatabase::class.java,
                    "bible_database"
                )
                    .fallbackToDestructiveMigration(true)
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                val database = getDatabase(context)
                                val quizzDao = database.quizzDao()

                                val quizzes = listOf(
                                    QuizzEntity(
                                        chapterId = 1,
                                        question = "Quem criou os céus e a terra?",
                                        optionA = "Deus",
                                        optionB = "Moisés",
                                        optionC = "Abraão",
                                        optionD = "Adão",
                                        correctAnswer = "A"
                                    ),
                                    QuizzEntity(
                                        chapterId = 1,
                                        question = "O que Deus criou no primeiro dia?",
                                        optionA = "O sol",
                                        optionB = "A luz",
                                        optionC = "O homem",
                                        optionD = "Os animais",
                                        correctAnswer = "B"
                                    ),
                                    QuizzEntity(
                                        chapterId = 1,
                                        question = "Quantos dias Deus levou para criar tudo?",
                                        optionA = "3",
                                        optionB = "5",
                                        optionC = "6",
                                        optionD = "7",
                                        correctAnswer = "C"
                                    ),
                                    QuizzEntity(
                                        chapterId = 1,
                                        question = "O que Deus fez no sétimo dia?",
                                        optionA = "Criou os animais",
                                        optionB = "Trabalhou",
                                        optionC = "Descansou",
                                        optionD = "Fez o homem",
                                        correctAnswer = "C"
                                    )
                                )

                                quizzDao.insertAll(quizzes)
                                println("🎉 Quizzes inseridos com sucesso!")
                            }
                        }
                    })
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

}
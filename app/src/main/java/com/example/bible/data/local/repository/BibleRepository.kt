package com.example.bible.data.local.repository

import android.content.Context
import com.example.bible.data.local.model.BibleJson
import com.example.bible.data.local.dao.BibleDao
import com.example.bible.data.local.entity.BookEntity
import com.example.bible.data.local.entity.ChapterEntity
import com.example.bible.data.local.entity.LastChapterEntity
import com.example.bible.data.local.entity.VerseEntity
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BibleRepository(private val dao: BibleDao, private val context: Context) {

    suspend fun loadBibleFromJson() {
        withContext(Dispatchers.IO) {
            val json = context.assets.open("pt_nvi.json").bufferedReader().use { it.readText() }
            val type = object : TypeToken<List<BibleJson>>() {}.type
            val bibleList: List<BibleJson> = Gson().fromJson(json, type)

            bibleList.forEach { book ->
                val bookId =
                    dao.insertBooks(listOf(BookEntity(abbrev = book.abbrev, name = book.name)))
                        .first().toInt()
                book.chapters.forEachIndexed { chapIndex, verses ->
                    val chapterId = dao.insertChapters(
                        listOf(ChapterEntity(bookId = bookId, number = chapIndex + 1))
                    ).first().toInt()
                    val verseEntities = verses.mapIndexed { vIndex, text ->
                        VerseEntity(chapterId = chapterId, number = vIndex + 1, text = text)
                    }
                    dao.insertVerses(verseEntities)
                }
            }
        }
    }

    suspend fun getBooks() = dao.getAllBooks()
    suspend fun getChapters(bookId: Int) = dao.getChaptersByBook(bookId)
    suspend fun getVerses(bookId: Int, chapterNumber: Int) = dao.getVersesByBookAndChapter(bookId, chapterNumber)

    suspend fun getLastLecture() = dao.getLastChapter()

    suspend fun saveLastLecture(bookId: Int, chapterNumber: Int) = dao.saveLastChapter(
        LastChapterEntity(bookId = bookId, chapterNumber = chapterNumber)
    )

    suspend fun markChapterAsRead(chapterId: Int?) = dao.markChapterAsRead(chapterId)

}
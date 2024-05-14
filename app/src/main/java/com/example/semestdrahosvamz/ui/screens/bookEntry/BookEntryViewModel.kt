package com.example.semestdrahosvamz.ui.screens.bookEntry

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.semestdrahosvamz.Data.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class BookEntryViewModel(private val bookRepository: BookRepository, private val context: Context) : ViewModel() {

    var bookEntryUIState by mutableStateOf(BookEntryUIState())
        private set

    fun updateState(title : String, link : String, imageUri : Uri) {
        bookEntryUIState = BookEntryUIState(title, link, imageUri)
    }
    suspend fun saveBook() {
        Log.d("SaveBook", "Original URI: ${bookEntryUIState.imageUri}")
        val bookId = bookRepository.insertItem(bookEntryUIState.getBook())
        val newUri = saveImageLocally(bookEntryUIState.imageUri, bookId)
        Log.d("SaveBook", "New URI: $newUri")

        val updatedBook = bookEntryUIState.getBook().copy(imageUri = newUri.toString(), id =  bookId, status = 1)
        bookRepository.updateItem(updatedBook)
        updateState(updatedBook.title, updatedBook.link, newUri)
        Log.d("SaveBook", "Book updated in repository with new URI")
    }

    private suspend fun saveImageLocally(uri: Uri, id : Long): Uri = withContext(Dispatchers.IO) {
        val source = ImageDecoder.createSource(context.contentResolver, uri)
        val bitmap = Bitmap.createScaledBitmap(ImageDecoder.decodeBitmap(source), 150, 300, true)

        val filename = "${id}.jpg"
        val file = File(context.filesDir, filename)
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
        }

        Uri.fromFile(file)
    }

}

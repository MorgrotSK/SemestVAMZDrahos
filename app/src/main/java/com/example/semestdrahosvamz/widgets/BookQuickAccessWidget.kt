//Sourced from: https://developer.android.com/develop/ui/compose/glance/create-app-widget


import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import com.example.semestdrahosvamz.Data.Book
import com.example.semestdrahosvamz.Data.BookDatabase
import com.example.semestdrahosvamz.widgets.ui.QuickAccessWidgetLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

/**
 * GlanceAppWidget implementation for displaying a quick access widget.
 */
class QuickAccessWidget : GlanceAppWidget() {

    /**
     * Provides the content for the Glance widget.
     *
     * @param context The context to access resources.
     * @param id The GlanceId of the widget.
     */
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val sharedPreferences = context.getSharedPreferences("widget_prefs", Context.MODE_PRIVATE)
        val bookId = sharedPreferences.getInt("book_id", -1)

        val book = if (bookId != -1) getBookById(bookId, context) else null

        provideContent {
            book?.let {
                QuickAccessWidgetLayout(book = book, context = context)
            }
        }
    }

    /**
     * Retrieves a book from the database by its ID.
     *
     * @param id The ID of the book to retrieve.
     * @param context The context to access the database.
     * @return The Book object if found, otherwise null.
     */
    suspend fun getBookById(id: Int, context: Context): Book? {
        return withContext(Dispatchers.IO) {
            val bookDao = BookDatabase.getDatabase(context).bookDao()
            bookDao.getBook(id).firstOrNull()
        }
    }
}

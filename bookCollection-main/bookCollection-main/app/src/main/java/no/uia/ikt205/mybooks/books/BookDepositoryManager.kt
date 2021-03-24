package no.uia.ikt205.mybooks.books

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import no.uia.ikt205.mybooks.books.data.Book

class BookDepositoryManager {

    private lateinit var bookColection: MutableList<Book>
    private lateinit var queue: RequestQueue

    var onBooks: ((List<Book>) -> Unit)? = null
    var onBookUpdate: ((book: Book) -> Unit)? = null


    fun load(url: String, context: Context) {

      /*  queue = Volley.newRequestQueue(context)

        val request = JsonArrayRequest(Request.Method.GET, url, null,
            {

                // JSON -> transport formatet
                // Gson -> Manipulering og serialisering av json

                Log.d("BookDepositoryManager", it.toString(3))
            },
            {
                Log.e("BookDepositoryManager", it.toString())
            })

        queue.add(request)*/


        bookColection = mutableListOf(
            Book("Martha Wells", "All systems red", 2017),
            Book("Neil Gaiman", "American Gods", 2011),
            Book("Terry Pratchett", "The wee free men", 2003)
        )

        onBooks?.invoke(bookColection)
    }

    fun updateBook(book: Book) {
        // finn bok i listen og erstat med den nye boken.
        onBookUpdate?.invoke(book)
    }

    fun addBook(book: Book) {
        bookColection.add(book)
        onBooks?.invoke(bookColection)
    }

    companion object {
        val instance = BookDepositoryManager()
    }

}
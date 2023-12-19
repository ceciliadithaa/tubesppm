package com.example.tubesppm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tubesppm.adapter.BookListAdapter
import com.example.tubesppm.data.BookItem
import com.example.tubesppm.databinding.ActivityMainBinding
import com.example.tubesppm.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrieveBooks()

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddActivity::class.java))
        }
    }

    private fun bookItemClicked(book: BookItem) {
        val bookId = book.bookId.toString()

        startActivity(
            Intent(this@MainActivity, DetailActivity::class.java)
                .putExtra("book_id", bookId)
        )
    }

    private fun buildBookList(books: ArrayList<BookItem>) {
        val bookAdapter = BookListAdapter(books) { book: BookItem ->
            bookItemClicked(book)
        }

        binding.rvBooks.adapter = bookAdapter
        binding.rvBooks.layoutManager = LinearLayoutManager(this@MainActivity,
            LinearLayoutManager.VERTICAL, false)
    }

    private fun retrieveBooks() {
        RetrofitClient.instance.getBooks()
            .enqueue(object: Callback<ArrayList<BookItem>> {
                override fun onResponse(call: Call<ArrayList<BookItem>>, response: Response<ArrayList<BookItem>>) {
                    if (response.code() == 200) {
                        val list = response.body()
                        Log.d("GET BOOK ITEMS", list.toString())

                        if (list!!.isEmpty()) {
                            Toast.makeText(this@MainActivity, "There is no book data to display", Toast.LENGTH_LONG).show()
                        } else {
                            buildBookList(list)
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Fail fetching from database response is not 200", Toast.LENGTH_LONG).show()
                        Log.d("GET BOOK ITEMS FAIL ${response.code()}", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<ArrayList<BookItem>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Fail fetching from database onFailure", Toast.LENGTH_LONG).show()
                    Log.d("GET BOOK ITEMS FAIL", t.toString())
                }
            })
    }
}
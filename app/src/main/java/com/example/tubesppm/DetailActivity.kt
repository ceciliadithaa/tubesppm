package com.example.tubesppm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.tubesppm.data.BookDetail
import com.example.tubesppm.databinding.ActivityDetailBinding
import com.example.tubesppm.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    lateinit var list: BookDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bookId: String = intent.getStringExtra("book_id").toString()

        retrieveBookDetail(bookId)

        binding.btnEdit.setOnClickListener {
            startActivity(
                Intent(this@DetailActivity, EditActivity::class.java)
                    .putExtra("book_detail", list)
            )
        }

        binding.btnDelete.setOnClickListener {
            deleteBook(bookId)
        }
    }

    private fun retrieveBookDetail(bookId: String) {
        RetrofitClient.instance.getBookDetail(bookId)
            .enqueue(object: Callback<BookDetail> {
                override fun onResponse(call: Call<BookDetail>, response: Response<BookDetail>) {
                    if (response.code() == 200) {
                        list = response.body()!!
                        Log.d("GET BOOK DETAIL", list.toString())

                        binding.tvBookName.text = list.bookName
                        binding.tvBookWriter.text = list.bookWriter
                        binding.tvBookYear.text = list.bookYear.toString()
                        binding.tvBookSummary.text = list.bookSummary
                    } else {
                        Toast.makeText(this@DetailActivity, "Fail fetching from database response is not 200", Toast.LENGTH_LONG).show()
                        Log.d("GET COUNTRY ITEMS FAIL ${response.code()}", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<BookDetail>, t: Throwable) {
                    Toast.makeText(this@DetailActivity, "Fail fetching from database onFailure", Toast.LENGTH_LONG).show()
                    Log.d("GET COUNTRY ITEMS FAIL", t.toString())
                }
            })
    }

    private fun deleteBook(countryId: String) {
        RetrofitClient.instance.deleteBookDetail(countryId)
            .enqueue(object: Callback<com.example.tubesppm.data.Response> {
                override fun onResponse(
                    call: Call<com.example.tubesppm.data.Response>,
                    response: Response<com.example.tubesppm.data.Response>
                ) {
                    if (response.code() == 200) {
                        val resp = response.body()

                        if (resp!!.error) Toast.makeText(this@DetailActivity, resp.message + ", please try again later", Toast.LENGTH_LONG).show()

                        else {
                            Toast.makeText(this@DetailActivity, resp.message, Toast.LENGTH_SHORT).show()

                            startActivity(Intent(this@DetailActivity, MainActivity::class.java))

                            this@DetailActivity.finish()
                        }
                    } else {
                        Toast.makeText(this@DetailActivity, "Something wrong on server", Toast.LENGTH_LONG).show()
                        Log.d("DELETE BOOK (${response.code()})", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<com.example.tubesppm.data.Response>, t: Throwable) {
                    Toast.makeText(this@DetailActivity, "Something wrong on server...", Toast.LENGTH_LONG).show()
                    Log.d("DELETE BOOK FAIL", t.toString())
                }
        })
    }
}
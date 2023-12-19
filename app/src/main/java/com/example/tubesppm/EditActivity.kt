package com.example.tubesppm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.tubesppm.data.BookDetail
import com.example.tubesppm.data.Response
import com.example.tubesppm.databinding.ActivityEditBinding
import com.example.tubesppm.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bookData: BookDetail? = intent.getParcelableExtra("book_detail")

        binding.edtName.setText(bookData!!.bookName)
        binding.edtWriter.setText(bookData.bookWriter)
        binding.edtYear.setText((bookData.bookYear!!).toString());
        binding.edtSummary.setText(bookData.bookSummary)

        binding.btnUpdate.setOnClickListener {
            updateBookDetail(bookData.bookId!!)
        }
    }

    private fun updateBookDetail(bookId: Int) {
        val inputName = binding.edtName.text.toString().trim()
        val inputWriter = binding.edtWriter.text.toString().trim()
        val inputYear = binding.edtYear.text.toString().trim()
        val inputSummary = binding.edtSummary.text.toString().trim()

        //validation data
        if (inputName.isEmpty()) {
            binding.edtName.error = "Field is empty"
        }
        if (inputWriter.isEmpty()) {
            binding.edtWriter.error = "Field is empty"
        }
        if (inputYear.isEmpty()) {
            binding.edtYear.error = "Field is empty"
        }
        if (inputSummary.isEmpty()) {
            binding.edtSummary.error = "Field is empty"
        }

        if (inputName.isNotEmpty() && inputWriter.isNotEmpty() && inputYear.isNotEmpty() && inputSummary.isNotEmpty()) {
            val updatedId = bookId.toString()
            RetrofitClient.instance.updateBookDetail(updatedId, inputName, inputWriter, inputYear, inputSummary)
                .enqueue(object: Callback<Response> {
                    override fun onResponse(
                        call: Call<Response>,
                        response: retrofit2.Response<Response>
                    ) {
                        if (response.code() == 200) {
                            val resp = response.body()

                            if (resp!!.error) Toast.makeText(this@EditActivity, resp.message + ", please try again later", Toast.LENGTH_LONG).show()

                            else {
                                Toast.makeText(this@EditActivity, resp.message, Toast.LENGTH_SHORT).show()

                                startActivity(Intent(this@EditActivity, MainActivity::class.java))

                                this@EditActivity.finish()
                            }
                        } else {
                            Toast.makeText(this@EditActivity, "Something wrong on server", Toast.LENGTH_LONG).show()
                            Log.d("EDIT BOOK (${response.code()})", response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<Response>, t: Throwable) {
                        Toast.makeText(this@EditActivity, "Something wrong on server...", Toast.LENGTH_LONG).show()
                        Log.d("EDIT BOOK FAIL", t.toString())
                    }
                })
        } else {
            Toast.makeText(this@EditActivity, "Fail editing book data, field(s) is empty!", Toast.LENGTH_LONG).show()
        }
    }
}
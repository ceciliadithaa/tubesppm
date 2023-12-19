package com.example.tubesppm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tubesppm.data.BookItem
import com.example.tubesppm.databinding.BookItemBinding

class BookListAdapter(
    private val books: ArrayList<BookItem>,
    val itemClickListener: (BookItem) -> Unit
): RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {

    // create an inner class for ViewHolder
    inner class BookViewHolder(private val binding: BookItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        // bind the items with each item of the list
        // which than will be shown in recycler view
        fun bind(book: BookItem) = with(binding) {
            tvBookName.text = book.bookName
            tvBookWriter.text = book.bookWriter
            root.setOnClickListener { itemClickListener(book) }
        }
    }

    // inside the onCreateViewHolder inflate the view of BookItemBinding
    // and return new ViewHolder object containing this layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return BookViewHolder(binding)
    }

    // in OnBindViewHolder this is where we get the current item
    // and bind it to the layout
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.bind(book)
    }

    // return the size of ArrayList
    override fun getItemCount() = books.size
}
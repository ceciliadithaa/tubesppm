package com.example.tubesppm.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookItem(
    val bookId : Int?,
    val bookName : String?,
    val bookWriter : String?
) : Parcelable
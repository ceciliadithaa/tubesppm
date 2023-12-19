package com.example.tubesppm.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookDetail(
    var bookId : Int?,
    var bookName : String?,
    var bookWriter : String?,
    var bookYear : Int?,
    var bookSummary : String
) : Parcelable
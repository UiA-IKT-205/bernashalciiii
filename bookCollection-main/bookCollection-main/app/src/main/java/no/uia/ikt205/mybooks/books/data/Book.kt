package no.uia.ikt205.mybooks.books.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Book(val author:String, val title:String, val published:Int):Parcelable
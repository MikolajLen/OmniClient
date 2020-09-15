package co.micode.omniclient.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleEntity(val title: String, val imageId: String, val text: String) : Parcelable
package co.micode.omniclient.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TopicEntity(val title: String, val type: String) : Parcelable
package com.example.memelist.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


/*
{
    "code": 200,
    "data": [
        {
            "ID": 22,
            "bottomText": "",
            "image": "https://imgflip.com/s/meme/Conspiracy-Keanu.jpg",
            "name": "Conspiracy Keanu",
            "rank": 210,
            "tags": "Keanu Reeves, Bill and Ted, Excellent Adventure, Neo",
            "topText": "What If"
        },
        .....
    "message": "GET successful",
    "next": "https://alpha-meme-maker.herokuapp.com/3"
    }
 */
data class MemeResponse(
    val data: List<MemeItem>,
    val next: String
)

@Parcelize
data class MemeItem(
    @SerializedName("ID") val id: Int,
    val bottomText: String? = null,
    val image: String? = null,
    val name: String? = null,
    val rank: Int? = null,
    val tags: String? = null,
    val topText: String? = null
) : Parcelable

data class IdResponse(
    val data: MemeItem
)


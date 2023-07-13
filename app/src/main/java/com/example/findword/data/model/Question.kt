package com.example.findword.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Question (

    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("singer")
    @Expose
    var singer: String? = null,

    @SerializedName("firstword")
    @Expose
    var firstWord: String? = null,

    @SerializedName("character")
    @Expose
    var character: String? = null,

    @SerializedName("questionType")
    @Expose
    var questionType: Int? = null,

    @SerializedName("secondword")
    @Expose
    var secondWord: String? = null,

    @SerializedName("numberCharacter")
    @Expose
    var numberCharacter: Int? = null,

    @SerializedName("wordcomplete")
    @Expose
    var wordComplete: String? = null,
)
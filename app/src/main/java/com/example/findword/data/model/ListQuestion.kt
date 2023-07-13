package com.example.findword.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ListQuestion (

    @SerializedName("question")
    @Expose
    var question: List<Question>? = null

)
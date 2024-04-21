package com.example.hobbyapp.model

import com.google.gson.annotations.SerializedName

data class Hobby( //tidak semua data member di serialize
    var id:String?,
    @SerializedName("music")
    var name_hobby:String?,
    @SerializedName("creator_username")
    var name:String?,
    var description:String?,
    @SerializedName("additional_info")
    var additional:String?,
    @SerializedName("images")
    var photoUrl:String?
)

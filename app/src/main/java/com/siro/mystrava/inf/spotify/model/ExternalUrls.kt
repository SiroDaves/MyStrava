package com.siro.mystrava.inf.spotify.model

import com.google.gson.annotations.SerializedName


data class ExternalUrls(

    @SerializedName("spotify") var spotify: String? = null

)
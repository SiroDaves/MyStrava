package com.siro.mystrava.inf.spotify.model

import com.google.gson.annotations.SerializedName


data class Items(

    @SerializedName("track") var track: Track? = Track(),
    @SerializedName("played_at") var playedAt: String? = null,
)
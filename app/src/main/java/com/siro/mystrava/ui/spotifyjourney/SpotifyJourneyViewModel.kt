package com.siro.mystrava.ui.spotifyjourney

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siro.mystrava.inf.spotify.SpotifyApis
import com.siro.mystrava.inf.spotify.SpotifySessionRepository
import com.siro.mystrava.inf.spotify.model.RecentlyPlayedSongs
import com.siro.mystrava.inf.spotify.model.Track
import com.siro.mystrava.strava.model.activites.ActivitiesItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpotifyJourneyViewModel @Inject constructor(
    private val spotifySessionRepository: SpotifySessionRepository,
    private val spotifyJourneyRepository: SpotifyJourneyRepository
) : ViewModel() {

    private var _isLoggedInSpotify: MutableLiveData<Boolean> = MutableLiveData(null)
    var isLoggedInSpotify: LiveData<Boolean> = _isLoggedInSpotify

    private var _recentlyPlayedSongs: MutableLiveData<MutableList<Pair<ActivitiesItem, MutableList<Track>>>> = MutableLiveData(null)
    var recentlyPlayedSongs: LiveData<MutableList<Pair<ActivitiesItem, MutableList<Track>>>> = _recentlyPlayedSongs

    init {
        _isLoggedInSpotify.postValue(spotifySessionRepository.isLoggedIn())
    }

    fun saveCode(spotifyCode: String?) {
        spotifyCode?.let {
            viewModelScope.launch {
                val response = spotifySessionRepository.getFirstTokens(it)

                _isLoggedInSpotify.postValue(spotifySessionRepository.isLoggedIn())
            }
        }
    }

    fun getRecentlyPlayedSongs() {
        viewModelScope.launch {
            _recentlyPlayedSongs.postValue(spotifyJourneyRepository.getRecentlyPlayed())
        }
    }
}
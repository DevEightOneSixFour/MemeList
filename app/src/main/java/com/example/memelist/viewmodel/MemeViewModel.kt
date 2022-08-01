package com.example.memelist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memelist.api.MemeRepository
import com.example.memelist.model.MemeItem
import com.example.memelist.model.UIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

const val TAG = "MemeViewModel"
class MemeViewModel(
    private val repository: MemeRepository,
    private val dispatcher: CoroutineDispatcher
    ): ViewModel() {

    private val _memeListData = MutableLiveData<UIState>()
    val memeListData: LiveData<UIState> get() = _memeListData

    private val _memeId = MutableLiveData<UIState>()
    val memeID: LiveData<UIState> get() = _memeId

    private val coroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e(TAG, "Context: $coroutineContext\nMessage: ${throwable.localizedMessage}",throwable)
        }
    }

    private val viewModelSafeScope by lazy {
        viewModelScope + coroutineExceptionHandler
    }

    var shouldLoadId = false
    var currentPage = 1

    fun getDemMemes(num: Int) {
        viewModelSafeScope.launch(dispatcher) {
            // collect from our flow
            repository.getMemesByPageNum(num).collect { state ->
                // postValue updates LiveData asynchronously
                _memeListData.postValue(state)
            }
        }
    }

    fun getMemeById(id: Int) {
        viewModelSafeScope.launch(dispatcher) {
            repository.getMemeById(id).collect {
                _memeId.postValue(it)
            }
        }
    }

    // setValue is not asynchronous
    fun setLoading() {
        if (shouldLoadId) _memeId.value = UIState.Loading
        else _memeListData.value = UIState.Loading
    }

    fun setLoadingForDetails() { _memeId.value = UIState.Loading }

    fun setSuccessForId(memeItem: MemeItem) { _memeId.value = UIState.Success(memeItem) }
}
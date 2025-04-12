package com.shettyharshith33.firebaseRealTimeDB.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shettyharshith33.firebaseRealTimeDB.RealTimeModelResponse
import com.shettyharshith33.firebaseRealTimeDB.repository.RealTimeRepository
import com.shettyharshith33.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealTimeViewModel @Inject constructor(
    private val repo:RealTimeRepository
):ViewModel(){

    private val _res:MutableState<ItemState> = mutableStateOf(ItemState())
    val res :State<ItemState> = _res


    fun insert(items:RealTimeModelResponse.RealTimeItems)=repo.insert(items)
    init {
        viewModelScope.launch {
            repo.getItems().collect{
                when(it){
                    is ResultState.Success ->{
                        _res.value=ItemState(
                            item = it.data)
                    }

                    is ResultState.Failure  ->{
                        _res.value = ItemState(
                            error = it.msg.toString()
                        )

                    }
                    is ResultState.Loading ->{
                        _res.value = ItemState(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }
    fun delete(key :String) = repo.delete(key)
    fun update(item:RealTimeModelResponse) = repo.update(item)
}

data class ItemState(
    val item: List<RealTimeModelResponse> = emptyList(),
    val error:String ="",
    val isLoading: Boolean = false

)
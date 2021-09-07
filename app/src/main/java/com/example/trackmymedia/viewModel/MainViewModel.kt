package com.example.trackmymedia.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.trackmymedia.model.MediaDataSource
import com.example.trackmymedia.model.RoomMediaDataSource
import com.example.trackmymedia.model.entities.MediaEntity
import com.example.trackmymedia.utilits.APP_ACTIVITY
import com.example.trackmymedia.utilits.TypesLists
import com.example.trackmymedia.utilits.TypesMedia
import com.example.trackmymedia.utilits.TypesSort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val mediaLiveData: LiveData<List<MediaEntity>> get() = _mediaLiveData
    private val _mediaLiveData = MutableLiveData<List<MediaEntity>>()

    private val dataSource: MediaDataSource = RoomMediaDataSource(application.applicationContext)

    private var typeSort: TypesSort = TypesSort.SORT_BY_DATE_OLD_FIRST
    private lateinit var typeMedia: TypesMedia
    private lateinit var typeList: TypesLists


    fun setTypes(typeMedia: TypesMedia, typeLists: TypesLists) {
        this.typeMedia = typeMedia
        this.typeList = typeLists
    }

    fun setSortType(typeSort: TypesSort) {
        this.typeSort = typeSort
        getEntities()
    }

    fun getEntities() {
        doAsync {
            _mediaLiveData.postValue(sortData(dataSource.getAll(typeMedia, typeList).toMutableList()))
        }
    }

    private fun sortData(list: MutableList<MediaEntity>): List<MediaEntity> {

        when (typeSort) {
            TypesSort.SORT_BY_DATE_NEW_FIRST -> list.sortByDescending { it.date }
            TypesSort.SORT_BY_DATE_OLD_FIRST -> list.sortBy { it.date }
            TypesSort.SORT_BY_ALPHABET_A -> list.sortBy { it.name }
            TypesSort.SORT_BY_ALPHABET_Z -> list.sortByDescending { it.name }
            TypesSort.SORT_BY_RATING_GOOD_FIRST -> list.sortByDescending { it.rating }
            TypesSort.SORT_BY_RATING_BAD_FIRST -> list.sortBy { it.rating }
        }

        return list
    }

    fun saveSortState() {
        val sPref = APP_ACTIVITY.getPreferences(Context.MODE_PRIVATE)
        val editor = sPref.edit()
        editor.putString(typeMedia.toString() + typeList.toString(), typeSort.toString())
        editor.apply()
    }

    fun initSortState(): TypesSort {
        val sPref = APP_ACTIVITY.getPreferences(Context.MODE_PRIVATE)
        val type = sPref.getString(
            typeMedia.toString() + typeList.toString(),
            TypesSort.SORT_BY_DATE_OLD_FIRST.toString()
        )
        typeSort = TypesSort.valueOf(type!!)

        return typeSort
    }

    fun insert(entity: MediaEntity) {
        doAsync {
            dataSource.insert(entity)
            getEntities()
        }
    }

    fun update(entity: MediaEntity) {
        doAsync {
            dataSource.update(entity)
            getEntities()
        }
    }

    fun delete(entity: MediaEntity) {
        doAsync {
            dataSource.delete(entity)
            getEntities()
        }
    }

    private fun doAsync(task: () -> Unit) = CoroutineScope(Dispatchers.IO).launch {
        task()
    }

}
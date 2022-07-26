package com.chocolang.android.chocoapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chocolang.android.chocoapp.repository.ApiClient
import com.chocolang.android.chocoapp.repository.SharedValue
import com.chocolang.android.chocoapp.repository.model.GitRepositoryList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RepositoryViewModel() : ViewModel() {
    var repo: ApiClient? = null
    var disposable: Disposable? = null
    var result: MutableLiveData<List<GitRepositoryList>?> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var oldSearchPath : String = "google/dagger"
    var searchPath: MutableLiveData<String> = MutableLiveData(oldSearchPath)
    var page = 0

    fun setSplitSearchPath(path: String) {
        searchPath.value?.run {
            oldSearchPath = this
        }
        searchPath.value = path
    }

    fun getSplitSearchPath(): List<String> {
        return searchPath.value?.run {
            split("/")
        }?: "google/dagger".split("/")
    }

    fun getRepositoryList() {
        val path = searchPath.value?.split("/")
        if(path?.size != 2) return

        isLoading.value = true
        disposable = repo?.run {
            apiService.getRepositoryList(path[0], path[1])
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response ->
                    page += 1
                    result.value = response
                    isLoading.value = false
                }, { throwable ->
                    result.value = null
                    isLoading.value = false
                    throwable.printStackTrace()
                })
        }
    }
}
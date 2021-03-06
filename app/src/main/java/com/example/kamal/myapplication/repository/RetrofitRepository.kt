package com.example.kamal.myapplication.repository

import com.example.kamal.myapplication.model.APIResponseHttp
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class RetrofitRepository {

    // Http request with RXJava
    fun <T> callAPI(request: Single<T>, observer: DisposableSingleObserver<APIResponseHttp<T>>?): Disposable? {
        return request
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(observer as SingleObserver<T>?) as Disposable
    }
}
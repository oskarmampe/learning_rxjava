package com.jonbott.learningrxjava.Activities.NetworkExample

import com.jonbott.learningrxjava.ModelLayer.Entities.Message
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jonbott.learningrxjava.ModelLayer.ModelLayer
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

class NetworkExamplePresenter {



    //Old Way
    private val modelLayer = ModelLayer.shared //normally injected

    val messages: BehaviorRelay<List<Message>>
        get() = modelLayer.messages

    private var bag = CompositeDisposable()


    //Old Way
    init {
        modelLayer.getMessages()
    }

    //New Way

    fun getMessagesRx(): Single<List<Message>> {
        return modelLayer.getMessagesRx()
    }
}
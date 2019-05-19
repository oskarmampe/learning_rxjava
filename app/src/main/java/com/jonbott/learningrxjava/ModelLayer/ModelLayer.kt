package com.jonbott.learningrxjava.ModelLayer

import android.support.design.widget.AppBarLayout
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jonbott.learningrxjava.ModelLayer.Entities.Message
import com.jonbott.learningrxjava.ModelLayer.Entities.Person
import com.jonbott.learningrxjava.ModelLayer.NetworkLayer.NetworkLayer
import com.jonbott.learningrxjava.ModelLayer.PersistenceLayer.PersistenceLayer
import com.jonbott.learningrxjava.ModelLayer.PersistenceLayer.PhotoDescription
import io.reactivex.Observable
import io.reactivex.Single

class ModelLayer {

    companion object {
        val shared = ModelLayer()
    }

    val photoDescriptions = BehaviorRelay.createDefault(listOf<PhotoDescription>())
    val messages = BehaviorRelay.createDefault(listOf<Message>())

    private val networkLayer = NetworkLayer.instance
    private val persistenceLayer = PersistenceLayer.shared

    fun loadAllPhotoDescriptions() {//result may be immediate, but use async callbacks anyway
        persistenceLayer.loadAllPhotoDescriptions { photoDescriptions ->
            this.photoDescriptions.accept(photoDescriptions)
        }
    }

    fun getMessages(){
        return networkLayer.getMessages({messages ->
            this.messages.accept(messages)
        }, { errorMessages ->
            notifyError(errorMessages)
            }
        )
    }

    private fun notifyError(errorMessage: String) {
        println("! ERROR OCCURED: $errorMessage")
    }

    fun getMessagesRx(): Single<List<Message>> {
        return networkLayer.getMessagesRx()
    }

    fun loadInfoFor(people: List<Person>): Observable<List<String>> {
        return networkLayer.loadInfoForPeople(people)
    }
}
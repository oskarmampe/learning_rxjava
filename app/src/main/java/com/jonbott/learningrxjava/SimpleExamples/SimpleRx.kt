package com.jonbott.learningrxjava.SimpleExamples

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jonbott.learningrxjava.Common.disposedBy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.toObservable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit

object SimpleRx {
    var bag = CompositeDisposable()

    fun simpleValues() {
        println("~~~~~~~simpleValues~~~~~~~");

        val someInfo = BehaviorRelay.createDefault("1")

        println("~~~~~~~someInfo.value ${someInfo.value}")

        val plainString = someInfo.value
        println("plainString: $plainString")

        someInfo.accept("2")
        println("~~~~~~~someInfo.value ${someInfo.value}")

        someInfo.subscribe { newValues ->
            println("value has changed $newValues")

        }

        someInfo.accept("3")
        //NOTE: Relays will never receive onError and onCompleteEvents


    }

    fun subjects() {
        val behaviourSubject = BehaviorSubject.createDefault(24)

        val disposable = behaviourSubject.subscribe({ newValue ->
            //OnNext
            println("lbehavious subject subscription: $newValue")
        }, { error ->
            println("subscription error")
        }, { //onCompleted
            println("completed")
        }, { //onSubscribe
           println("subscribed")
        })

        behaviourSubject.onNext(34)
        behaviourSubject.onNext(48)
        behaviourSubject.onNext(48)//duplicates show as new events by default

        //1 on error
//        val someException = IllegalArgumentException("some fake error")
//        behaviourSubject.onError(someException)
//        behaviourSubject.onNext(109) //this will never show
        //2 on completed
        behaviourSubject.onComplete()
        behaviourSubject.onNext(23)//this will never show
    }

    fun basicObservable() {
        //The observable
        val observable = Observable.create<String> { observer ->
            //The lambda called for every subscriber - by default
            println("~~~~~~~~ Observable logic being triggerd ~~~~~~~~~")

            //Do work on a background thread
            launch {
                delay(1000)//artificial delay 1 second
                observer.onNext("some value of 23")
                observer.onComplete()
            }
        }

        observable.subscribe { someString ->
            println("new value: $someString")
        }.disposedBy(bag)

        val observer = observable.subscribe{ someString ->
            println("Another subscriber: $someString")
        }.disposedBy(bag)
    }

    fun creatingObservables() {
        //val observable = Observable.just("Oskar")
       //val observable = Observable.interval(300, TimeUnit.MILLISECONDS).timeInterval(AndroidSchedulers.mainThread())
//        val observable = Observable.fromArray(1,2,3,4,5,6)
//        val userIds = arrayOf(1,2,3,4,5,6)
//        val observable = Observable.fromArray(*userIds)
//        val observable = userIds.toObservable()
    }

}
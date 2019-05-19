package com.jonbott.learningrxjava.Activities.TasksExample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jonbott.learningrxjava.Common.disposedBy
import com.jonbott.learningrxjava.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class TasksExampleActivity : AppCompatActivity() {

    private val presenter = TasksExamplePresenter()
    private var bag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks_example)
        presenter.loadPeopleInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ infoList ->
                    println("all processs completed successfully \n\t $infoList")
                    infoList.forEach{
                        println("value: ${it}")
                    }
                }, {error ->
                    println("error occured: ${error.localizedMessage}")
                }).disposedBy(bag)
    }

    override fun onDestroy() {
        super.onDestroy()
        bag.clear()
    }
}


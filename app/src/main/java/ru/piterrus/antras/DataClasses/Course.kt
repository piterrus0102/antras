package ru.piterrus.antras.DataClasses

class Course(duration: String,
             finished: Int,
             institution: String,
             title: String) {

    var duration: String
    var finished: Int
    var institution: String
    var title: String

    init {
        this.duration = duration
        this.finished = finished
        this.institution = institution
        this.title = title
    }
}
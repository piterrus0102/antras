package ru.piterrus.antras.DataClasses

class Language(level: Int,
               title:String) {

    var level: Int
    var title: String

    init {
        this.level = level
        this.title = title
    }
}
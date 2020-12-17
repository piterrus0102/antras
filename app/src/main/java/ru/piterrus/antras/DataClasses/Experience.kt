package ru.piterrus.antras.DataClasses

import java.text.FieldPosition

class Experience(start: String,
                 end: String,
                 company: String,
                 position: String,
                 detail: String,
                 tillNow: Boolean) {

    var start: String
    var end: String
    var company: String
    var position: String
    var detail: String
    var tillNow: Boolean

    init {
        this.start = start
        this.end = end
        this.company = company
        this.position = position
        this.detail = detail
        this.tillNow = tillNow
    }
}
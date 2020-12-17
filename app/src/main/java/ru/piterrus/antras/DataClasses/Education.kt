package ru.piterrus.antras.DataClasses

class Education(finished: Int,
                institution: String,
                level: String,
                qualification: String,
                specialization: String) {

    var finished: Int
    var institution: String
    var level: String
    var qualification: String
    var specialization: String

    init {
        this.finished = finished
        this.institution = institution
        this.level = level
        this.qualification = qualification
        this.specialization = specialization
    }
}
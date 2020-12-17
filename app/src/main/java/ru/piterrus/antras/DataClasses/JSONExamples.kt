package ru.piterrus.antras.DataClasses

class JSONExamples(value: String,
                    label: String,
                    jsonFile: String,
                    pdfFile: String,
                    imageFile: String) {
    var value: String
    var label: String
    var jsonFile: String
    var pdfFile: String
    var imageFile: String

    init {
        this.value = value
        this.label = label
        this.jsonFile = jsonFile
        this.pdfFile = pdfFile
        this.imageFile = imageFile
    }
}
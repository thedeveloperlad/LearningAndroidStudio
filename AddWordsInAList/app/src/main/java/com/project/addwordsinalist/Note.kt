package com.project.addwordsinalist

class Note {
    var title: String
    var description: String
    var id: Int

    constructor( title: String,  description: String, id: Int){
        this.title = title
        this.description = description
        this.id = id
    }

    override fun toString(): String {
        return title // Or: "$title - $description"
    }

    fun turnOn() {
        println("Smart device is turned on.")
    }

    fun turnOff() {
        println("Smart device is turned off.")
    }
}
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

    fun getTitle(): String {
        return title;
    }

    fun getDescription(): String {
        return description;
    }

    fun getId(): Int {
        return id;
    }

    fun setTitle(title: String){
        this.title = title;
    }

    fun setDescription(description: String){
        this.description = title;
    }

    fun setId(idNumber: Int){
        this.id = idNumber;
    }

    fun turnOn() {
        println("Smart device is turned on.")
    }

    fun turnOff() {
        println("Smart device is turned off.")
    }
}
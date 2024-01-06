package com.example.firebase

class CourseRVModal() {
    private var courseName: String ?= null
    private var coursePrice: String ?= null
    private var suitedFor: String ?= null
    private var courseImage: String ?= null
    private var courseLink: String ?= null
    private var courseDesc: String ?= null
    private var courseId: String ?= null

    fun getCourseDetails(name: String,price: String,suited: String,image: String, link: String,desc: String,id: String){
        courseName=name
        coursePrice= price
        suitedFor= suited
        courseImage= image
        courseLink= link
        courseDesc= desc
        courseId= id
    }
}
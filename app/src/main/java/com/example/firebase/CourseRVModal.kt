package com.example.firebase

import android.os.Parcel
import android.os.Parcelable

class CourseRVModal() : Parcelable {
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

    fun getCourseName():String{
        return courseName!!
    }
    fun getCoursePrice():String{
        return coursePrice!!
    }
    fun getSuitedFor():String{
        return suitedFor!!
    }
    fun getCourseImage():String{
        return courseImage!!
    }
    fun getCourseLink():String{
        return courseLink!!
    }
    fun getCourseDesc():String{
        return courseDesc!!
    }
    fun getCourseId():String{
        return courseId!!
    }


    constructor(parcel: Parcel) : this() {
        courseName = parcel.readString()
        coursePrice = parcel.readString()
        suitedFor = parcel.readString()
        courseImage = parcel.readString()
        courseLink = parcel.readString()
        courseDesc = parcel.readString()
        courseId = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(courseName)
        parcel.writeString(coursePrice)
        parcel.writeString(suitedFor)
        parcel.writeString(courseImage)
        parcel.writeString(courseLink)
        parcel.writeString(courseDesc)
        parcel.writeString(courseId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CourseRVModal> {
        override fun createFromParcel(parcel: Parcel): CourseRVModal {
            return CourseRVModal(parcel)
        }

        override fun newArray(size: Int): Array<CourseRVModal?> {
            return arrayOfNulls(size)
        }
    }
}
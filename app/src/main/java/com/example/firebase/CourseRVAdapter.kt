package com.example.firebase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CourseRVAdapter(var courseRVModalArrayList : ArrayList<CourseRVModal>,var context: Context, var courseClickInterface: CourseClickInterface): RecyclerView.Adapter<CourseRVAdapter.ViewHolder>() {
    private lateinit var courseRVModal: CourseRVModal
    private var lastPos : Int = -1

    companion object{

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_rv_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        courseRVModal = courseRVModalArrayList[position]
        holder.courseNameTv.setText(courseRVModal.getCourseName())
        holder.coursePriceTv.setText("Rs: "+courseRVModal.getCoursePrice())
        Picasso.get().load(courseRVModal.getCourseImage()).into(holder.courseIV)
        setAnimation(holder.itemView, position)
        holder.itemView.setOnClickListener {
            courseClickInterface.onCourseClick(position)
        }
    }

    private fun setAnimation(itemView: View,position: Int){
        if(position>lastPos){
            var animation: Animation= AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            itemView.animation= animation
            lastPos= position
        }
    }
    override fun getItemCount(): Int {
        return courseRVModalArrayList.size
    }

    class ViewHolder(var view: View): RecyclerView.ViewHolder(view) {
        var courseNameTv= view.findViewById<TextView>(R.id.idTvCourseName)
        var coursePriceTv= view.findViewById<TextView>(R.id.idTVCoursePrice)
        var courseIV=view.findViewById<ImageView>(R.id.idIVCourse)

    }
}
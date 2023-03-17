package com.example.myapplication.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Courses;

import java.util.List;
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseListViewHolder> {
    // Define an interface to handle clicks on list items
    public interface OnItemClickListener {
        void onItemClick(Courses course);
    }

    private List<Courses> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;
    private final OnItemClickListener mListener;

    public CourseAdapter(Context context, OnItemClickListener onItemClickListener) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        mListener=onItemClickListener;
    }



    @NonNull
    @Override
    public CourseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseListViewHolder holder, int position) {
        if (mCourses != null) {
            Courses current = mCourses.get(position);

            String courseTitle = current.getCourseTitle();
            String courseStart = current.getCourseStartDate();
            String courseEnd = current.getCourseEndDate();
            String courseProgress = current.getCourseProgress();
            String instructorName = current.getInstructorName();
            String instructorPhoneNumber = current.getInstructorPhoneNumber();
            String instructorEmail = current.getInstructorEmail();
            String displayText = courseTitle + " - " + courseStart + " to " + courseEnd + " progress =" +
                    courseProgress + " Instructor Name = " + instructorName + " Phone Number = " + instructorPhoneNumber +
                    " Email = " + instructorEmail;

            holder.courseItemView.setText(displayText);
        } else {
            holder.courseItemView.setText("No Courses Named");
        }
    }

    @Override
    public int getItemCount() {
        return mCourses == null ? 0 : mCourses.size();
    }

    public void setCourses(List<Courses> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

    public class CourseListViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;

        public CourseListViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.courseItemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Courses currentCourse = mCourses.get(position);

                    mListener.onItemClick(currentCourse);
                }
            });
        }
    }
}



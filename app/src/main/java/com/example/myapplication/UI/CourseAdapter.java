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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseListViewHolder> {

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
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH-mm", Locale.getDefault());
            String courseTitle = current.getCourseTitle();
            Date courseStart = current.getCourseStartDate();
            String formattedCourseStartDate = dateFormatter.format(courseStart);
            Date courseEnd = current.getCourseEndDate();
            String formattedCourseEnd = dateFormatter.format(courseEnd);

            String courseProgress = current.getCourseProgress();
            String instructorName = current.getInstructorName();
            String instructorPhoneNumber = current.getInstructorPhoneNumber();
            String instructorEmail = current.getInstructorEmail();
            String displayText = courseTitle + " - " + formattedCourseStartDate + " to " + formattedCourseEnd + " progress =" +
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

    public int findPositionById(int courseId) {
        if(mCourses == null){
            return -1;
        }
        for (int i = 0; i < mCourses.size(); i++) {
            if (mCourses.get(i).getCourseId() == courseId) {
                return i;
            }
        }
        return -1;
    }
}




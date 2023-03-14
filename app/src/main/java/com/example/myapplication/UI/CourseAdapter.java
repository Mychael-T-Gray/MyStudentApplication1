package com.example.myapplication.UI;

import android.content.Context;
import android.content.Intent;
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

    class CourseListViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;
        private CourseListViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.courseItemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Courses currentCourse = mCourses.get(position);

                    Intent intent = new Intent(context, CourseDetails.class);

                    intent.putExtra("courseId", currentCourse.getCourseId());
                    intent.putExtra("courseTitle", currentCourse.getCourseTitle());
                    intent.putExtra("courseStartDate", currentCourse.getCourseStartDate());
                    intent.putExtra("courseEndDate", currentCourse.getCourseEndDate());
                    intent.putExtra("courseProgress", currentCourse.getCourseProgress());
                    intent.putExtra("instructorName", currentCourse.getInstructorName());
                    intent.putExtra("instructorPhoneNumber", currentCourse.getInstructorPhoneNumber());
                    intent.putExtra("instructorEmail", currentCourse.getInstructorEmail());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Courses> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseListViewHolder holder, int position) {
        if (mCourses != null && !mCourses.isEmpty()) {
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

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}


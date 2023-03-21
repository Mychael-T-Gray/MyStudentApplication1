package com.example.myapplication.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.CourseNotes;

import java.util.List;

public class CourseNotesAdapter extends RecyclerView.Adapter<CourseNotesAdapter.CourseNoteViewHolder> {
    private List<CourseNotes> mCourseNotes;
    private Context mContext;

    public CourseNotesAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public CourseNoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_note_item, parent, false);
        return new CourseNoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseNoteViewHolder holder, int position) {
        if (mCourseNotes != null) {
            CourseNotes currentNote = mCourseNotes.get(position);
            holder.courseNoteTextView.setText(currentNote.getNoteText());
        } else {
            holder.courseNoteTextView.setText("No notes available");
        }
    }

    public void setCourseNotes(List<CourseNotes> courseNotes) {
        mCourseNotes = courseNotes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCourseNotes != null)
            return mCourseNotes.size();
        else return 0;
    }

    public class CourseNoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseNoteTextView;

        private CourseNoteViewHolder(View itemView) {
            super(itemView);
            courseNoteTextView = itemView.findViewById(R.id.courseNoteItem);
        }
    }
}


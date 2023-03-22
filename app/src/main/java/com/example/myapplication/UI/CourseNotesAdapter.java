package com.example.myapplication.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    public class CourseNoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView courseNoteTextView;

        private CourseNoteViewHolder(View itemView) {
            super(itemView);
            courseNoteTextView = itemView.findViewById(R.id.courseNoteItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            CourseNotes note = mCourseNotes.get(position);
            sendEmail(note);
        }
    }

    private void sendEmail(CourseNotes note) {
        Context context = mContext;

        // Create a dialog to input the email address
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Enter the email address");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        builder.setView(input);
        builder.setMessage(note.getNoteText());

        // Set up the buttons
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = input.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(context, "Email address is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create email intent
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + email));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Course Notes");
                emailIntent.putExtra(Intent.EXTRA_TEXT, note.getNoteText());

                // Check if an email app is available to handle the intent
                if (emailIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(emailIntent);
                } else {
                    Toast.makeText(context, "No email app found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}



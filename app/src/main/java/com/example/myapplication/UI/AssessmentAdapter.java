package com.example.myapplication.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.AssessmentsEntity;

import java.util.ArrayList;
import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentListViewHolder> {
    // Define an interface to handle clicks on list items
    public interface OnItemClickListener {
        void onItemClick(AssessmentsEntity assessment);
    }

    //private List<AssessmentsEntity> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;
    private final OnItemClickListener mListener;
    private List<AssessmentsEntity> mAssessments = new ArrayList<>();

    public AssessmentAdapter(Context context, OnItemClickListener onItemClickListener) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        mListener=onItemClickListener;
    }

    @NonNull
    @Override
    public AssessmentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentListViewHolder holder, int position) {
        if (mAssessments != null) {
            AssessmentsEntity current = mAssessments.get(position);

            String assessmentTitle = current.getAssessmentTitle();
            String assessmentType = current.getAssessmentType();
            String assessmentEndDate = current.getAssessmentEndDate();
            String displayText = assessmentTitle + " - " + assessmentType + " - " + assessmentEndDate;

            holder.assessmentItemView.setText(displayText);
        } else {
            holder.assessmentItemView.setText("No Assessments Named");
        }
    }

    @Override
    public int getItemCount() {
        return mAssessments == null ? 0 : mAssessments.size();
    }

    public void setAssessments(List<AssessmentsEntity> assessments) {
        if(assessments != null) {
            mAssessments .clear();
            mAssessments.addAll(assessments);
            notifyDataSetChanged();
        }
    }

    public class AssessmentListViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;

        public AssessmentListViewHolder(View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.assessmentItemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final AssessmentsEntity currentAssessment = mAssessments.get(position);

                    mListener.onItemClick(currentAssessment);
                }
            });
        }
    }

    public int findPositionById(int assessmentId) {
        if(mAssessments == null){
            return -1;
        }
        for (int i = 0; i < mAssessments.size(); i++) {
            if (mAssessments.get(i).getAssessmentId() == assessmentId) {
                return i;
            }
        }
        return -1;
    }
}

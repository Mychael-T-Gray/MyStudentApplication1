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
import com.example.myapplication.entities.Terms;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermListViewHolder> {
    class TermListViewHolder extends RecyclerView.ViewHolder{
        private final TextView termItemView;
        private TermListViewHolder(View itemview){
            super(itemview);
            termItemView= itemview.findViewById(R.id.termItemView);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Terms currentTerm = mTerms.get(position);
                    Intent intent = new Intent(context, TermDetails.class);
                    intent.putExtra("termId", currentTerm.getTermId());
                    intent.putExtra("termTitle", currentTerm.getTermTitle());
                    intent.putExtra("termStart", currentTerm.getTermStart());
                    intent.putExtra("termEnd", currentTerm.getTermEnd());
                    context.startActivity(intent);

                }
            });
        }

    }
    private List<Terms> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;
    public TermAdapter(Context context){
        mInflater= LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public TermAdapter.TermListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);

        return new TermListViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermListViewHolder holder, int position) {
        if(mTerms!=null){
            Terms current = mTerms.get(position);
            String termTitle = current.getTermTitle();
            String termStart = current.getTermStart();
            String termEnd = current.getTermEnd();
            String displayText = termTitle + " - " + termStart + " to " + termEnd;

            holder.termItemView.setText(displayText);


    }
        else{
        holder.termItemView.setText("No Terms Named");

        }
    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }

    public void setTerms(List<Terms> terms){
        mTerms = terms;
        notifyDataSetChanged();

    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

}

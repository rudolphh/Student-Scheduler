package com.rudolphh.studentscheduler.term.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rudolphh.studentscheduler.R;
import com.rudolphh.studentscheduler.course.main.CourseMainActivity;
import com.rudolphh.studentscheduler.term.database.TermWithCourses;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TermMainAdapter extends RecyclerView.Adapter<TermMainAdapter.TermHolder> {

    private List<TermWithCourses> terms = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public TermHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_item, parent, false);

        return new TermHolder(itemView);
    }

    /*******************  onBindViewHolder */
    @Override
    public void onBindViewHolder(@NonNull TermHolder holder, int position) {

        TermWithCourses currentTermDetails = terms.get(position);

        holder.textViewTitle.setText(currentTermDetails.term.getTitle());// set title

        // format text and set start and end Date textView
        SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd yyyy", Locale.US);

        holder.textViewStart.setText(formatter.format(currentTermDetails.term.getStart()));
        holder.textViewEnd.setText(formatter.format(currentTermDetails.term.getEnd()));

        String numberOfCourses = currentTermDetails.courses.size() + " courses";
        holder.textViewNumberCourses.setText(numberOfCourses);

        holder.termView.setOnClickListener((view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("termId", currentTermDetails.term.getId());
            bundle.putString("termTitle", currentTermDetails.term.getTitle());

            // take us to CourseMain
            Intent intent = new Intent(context, CourseMainActivity.class);
            intent.putExtras(bundle);

            context.startActivity(intent);
        }));
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    public void setTerms(List<TermWithCourses> terms){
        this.terms = terms;
        notifyDataSetChanged();
        // notifyItemInserted(); && notifyItemRemoved();
    }


    ///////////////////////// TermHolder
    static class TermHolder extends RecyclerView.ViewHolder {

        private View termView;

        private TextView textViewTitle;
        private TextView textViewStart;
        private TextView textViewEnd;
        private TextView textViewNumberCourses;

        public TermHolder(@NonNull View itemView) {
            super(itemView);
            termView = itemView;

            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewStart = itemView.findViewById(R.id.text_view_start);
            textViewEnd = itemView.findViewById(R.id.text_view_end);
            textViewNumberCourses = itemView.findViewById(R.id.text_view_number_courses);
        }
    }
}

package com.my.calendar;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

    private final ArrayList<String> dayOfMonth;
    private final OnItemListener onItemListener;
    private final Boolean currentDate;

    public CalendarAdapter(ArrayList<String> dayOfMonth, OnItemListener onItemListener, Boolean currentDate) {
        this.dayOfMonth = dayOfMonth;
        this.onItemListener = onItemListener;
        this.currentDate = currentDate;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cell_calendar, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);

        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {

        holder.dayOfMonth.setText(dayOfMonth.get(position));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
        if (currentDate == true && dayOfMonth.get(position).equals(LocalDate.now().format(formatter))){
            holder.cl.setBackgroundColor(Color.GRAY);
        }
    }

    @Override
    public int getItemCount() {
        return dayOfMonth.size();
    }

    public  interface  OnItemListener {
        void OnItemClick(int position, String dayText);
    }
}

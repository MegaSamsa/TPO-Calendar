package com.my.calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectData;
    private Boolean currentDate;
    String current = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        selectData = LocalDate.now();
        setMonthView();
        Toast.makeText(this, "Сегодня " + current + " " + monthYearFromDate(selectData), Toast.LENGTH_LONG).show();
    }

    private void setMonthView() {
        monthYearText.setText(current + " " + monthYearFromDate(selectData));
        ArrayList<String> daysInMonth = daysInMonthArray(selectData);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this, currentDate);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> dayInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = selectData.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue() - 1;

        for(int i = 1; i <= 42; i++){
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek){
                dayInMonthArray.add("");
            }
            else {
                dayInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return dayInMonthArray;
    }

    private String monthYearFromDate(LocalDate date){
        if (date.equals(LocalDate.now())){
            currentDate = true;
        }
        else {
            currentDate = false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy");
        return date.format(formatter);
    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendar_recycler);
        monthYearText = findViewById(R.id.year_cdr);
    }

    public void leftPressButton(View view) {
        selectData = selectData.minusMonths(1);
        setMonthView();
    }

    public void rightPressButton(View view) {
        selectData = selectData.plusMonths(1);
        setMonthView();
    }

    @Override
    public void OnItemClick(int position, String dayText) {
        if(!dayText.equals("")){
            String message = dayText + " " + monthYearFromDate(selectData);
            //Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    public void pressBack(View view) {
        selectData = LocalDate.now();
        setMonthView();
    }

    public void rightYearButton(View view) {
        selectData = selectData.plusYears(1);
        setMonthView();
    }
}
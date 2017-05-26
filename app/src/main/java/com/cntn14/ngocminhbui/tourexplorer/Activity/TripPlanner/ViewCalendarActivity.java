package com.cntn14.ngocminhbui.tourexplorer.Activity.TripPlanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alamkanak.weekview.WeekViewEvent;
import com.cntn14.ngocminhbui.tourexplorer.Database.SQLiteWeekSchedule;
import com.cntn14.ngocminhbui.tourexplorer.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ViewCalendarActivity extends ViewCalendarBaseActivity {


    private List<MyWeekViewEvent> events = new ArrayList<MyWeekViewEvent>();
    boolean calledNetwork = false;

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {

        // Download events from network if it hasn't been done already. To understand how events are
        // downloaded using retrofit, visit http://square.github.io/retrofit
        if (!calledNetwork) {
            events = new SQLiteWeekSchedule(this).getListEvent();
            calledNetwork = true;
        }

        // Return only the events that matches newYear and newMonth.
        List<MyWeekViewEvent> matchedEvents = new ArrayList<MyWeekViewEvent>();
        for (MyWeekViewEvent event : events) {
            if (eventMatches(event, newYear, newMonth)) {
                matchedEvents.add(event);
            }
        }
        return matchedEvents;
    }

    /**
     * Checks if an event falls into a specific year and month.
     * @param event The event to check for.
     * @param year The year.
     * @param month The month.
     * @return True if the event matches the year and month.
     */
    private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month - 1) || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
    }


}

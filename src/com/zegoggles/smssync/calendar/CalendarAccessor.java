package com.zegoggles.smssync.calendar;

import android.content.ContentResolver;
import android.os.Build;

import java.util.Date;
import java.util.Map;

public interface CalendarAccessor {
    /**
     * Adds an event to a calendar.
     *
     * @param resolver     the resolver
     * @param calendarId  the ID of the calendar to add to
     * @param when        when the call was made
     * @param duration    the duration of the event, in seconds
     * @param title       a title for the calendar event
     * @param description a description for the calendar event
     */
    public void addEntry(
            ContentResolver resolver, int calendarId, Date when, int duration, String title,
            String description);

    /**
     * Finds a list of calendars available on the phone.
     *
     *
     * @param resolver the resolver
     * @return a Map relating the id of the calendars found to their names.
     */
    public Map<String, String> getCalendars(ContentResolver resolver);


    public static class Get {
        private static CalendarAccessor sCalendarAccessor;

        public static CalendarAccessor instance() {
            final  int sdkVersion = Build.VERSION.SDK_INT;
            if (sCalendarAccessor == null) {
                try {
                    if (sdkVersion < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        sCalendarAccessor = new CalendarAccessorPre40();
                    } else {
                        sCalendarAccessor = new CalendarAccessorPost40();
                    }
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
            return sCalendarAccessor;
        }
    }
}

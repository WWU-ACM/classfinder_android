package edu.wwu.classfinder2.data;

import java.util.List;
import java.util.ArrayList;

import org.apache.commons.collections4.CollectionUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.Duration;
import org.threeten.bp.LocalTime;

import org.threeten.bp.temporal.ChronoUnit;

public class Schedule {

    private List<Meeting> mMeetings;

    public List<Meeting> getMeetings() {
        return mMeetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.mMeetings = meetings;
    }

    public static Schedule fromString(String scheduleStr) {
        Schedule schedule = new Schedule();
        List<Meeting> meetings = new ArrayList<Meeting>();
        CollectionUtils.addAll(meetings, new ScheduleParser(scheduleStr));
        schedule.setMeetings(meetings);
        return schedule;
    }

    public String asString() {
        return "";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj == this || !(obj instanceof Schedule)) {
            return false;
        }

        Schedule oSchedule = (Schedule) obj;
        return new EqualsBuilder()
            .append(mMeetings, oSchedule.mMeetings)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31)
            .append(mMeetings)
            .toHashCode();
    }

    public static class Meeting {

        private DayOfWeek mDay;

        private LocalTime mStartTime;

        private Duration mDuration;

        public Meeting(DayOfWeek day,
                       LocalTime startTime,
                       Duration duration) {
            if (day == null)
                throw new IllegalArgumentException("Day of week is null.");
            mDay = day;
            if (startTime == null)
                throw new IllegalArgumentException("Start time is null.");
            mStartTime = startTime;
            if (duration == null)
                throw new IllegalArgumentException("Duration is null.");
            mDuration = duration;
        }

        public DayOfWeek getDay() {
            return mDay;
        }

        public LocalTime getStartTime() {
            return mStartTime;
        }

        public Duration getDuration() {
            return mDuration;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || obj == this
                || !(obj instanceof Meeting)) {
                return false;
            }

            Meeting oMeeting = (Meeting) obj;
            return new EqualsBuilder()
                .append(mDay, oMeeting.mDay)
                .append(mStartTime, oMeeting.mStartTime)
                .append(mDuration, oMeeting.mDuration)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 31)
                .append(mDay)
                .append(mStartTime)
                .append(mDuration)
                .toHashCode();
        }

        @Override
        public String toString() {
            char dayChar;
            switch (mDay) {
            case SUNDAY:    dayChar = 'U';
                break;
            case MONDAY:    dayChar = 'M';
                break;
            case TUESDAY:   dayChar = 'T';
                break;
            case WEDNESDAY: dayChar = 'W';
                break;
            case THURSDAY:  dayChar = 'R';
                break;
            case FRIDAY:    dayChar = 'F';
                break;
            case SATURDAY:  dayChar = 'S';
                break;
            default: dayChar = 'X';
                break;
            }

            LocalTime start;
            String amOrPm;

            if (mStartTime.isAfter(LocalTime.NOON)) {
                amOrPm = "pm";
                start = mStartTime.minus(12, ChronoUnit.HOURS);
            } else {
                amOrPm = "am";
                start = mStartTime;
            }

            LocalTime end = start.plus(mDuration);

            return String.format("%c %s-%s %s",
                                 dayChar,
                                 start.toString(),
                                 end.toString(),
                                 amOrPm);
        }
    }


}

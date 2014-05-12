package edu.wwu.classfinder2.data;

import java.util.List;

public class Schedule {

    private List<Meeting> mMeetings;

    public List<Meeting> getMeetings() {
        return mMeetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.mMeetings = meetings;
    }


    public static class Meeting {

        private DayOfWeek mDay;

        private MeetTime mTime;

        public Meeting(DayOfWeek day, MeetTime time) {
            mDay = day;
            mTime = time;
        }

        public MeetTime getTime() {
            return mTime;
        }

        public DayOfWeek getDay() {
            return mDay;
        }

    }

    public static class MeetTime {
        private int mStartTime;

        private int mDuration;

        public MeetTime(int startTime, int duration) {
            mStartTime = startTime;
            mDuration = duration;
        }

        public int getDuration() {
            return mDuration;
        }

        public int getStartTime() {
            return mStartTime;
        }

    }

    public static enum DayOfWeek {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
        THURSDAY, FRIDAY, SATURDAY
    }
}

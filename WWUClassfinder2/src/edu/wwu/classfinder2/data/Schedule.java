package edu.wwu.classfinder2.data;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Schedule {

    private List<Meeting> mMeetings;

    public List<Meeting> getMeetings() {
        return mMeetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.mMeetings = meetings;
    }

    public static Schedule fromString(String schedule) {
        return new Schedule();
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

        @Override
        public boolean equals(Object obj) {
            if (obj == null || obj == this
                || !(obj instanceof Meeting)) {
                return false;
            }

            Meeting oMeeting = (Meeting) obj;
            return new EqualsBuilder()
                .append(mDay, oMeeting.mDay)
                .append(mTime, oMeeting.mTime)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 31)
                .append(mDay)
                .append(mTime)
                .toHashCode();
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

        @Override
        public boolean equals(Object obj) {
            if (obj == null || obj == this
                || !(obj instanceof MeetTime)) {
                return false;
            }

            MeetTime oMeetTime = (MeetTime) obj;
            return new EqualsBuilder()
                .append(mStartTime, oMeetTime.mStartTime)
                .append(mDuration, oMeetTime.mDuration)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 31)
                .append(mStartTime)
                .append(mDuration)
                .toHashCode();
        }
    }

    public static enum DayOfWeek {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
        THURSDAY, FRIDAY, SATURDAY
    }
}

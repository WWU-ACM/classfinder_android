package edu.wwu.classfinder2.data;

import edu.wwu.classfinder2.data.Schedule.Meeting;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.Duration;
import org.threeten.bp.LocalTime;

import org.threeten.bp.temporal.ChronoUnit;

public class ScheduleParser
    implements Iterable<Meeting> {

    private final String[] mScheduleStrs;

    // Schedule strings with multiple different day/time combinations
    // should be have each combination delimited by semi-colons, and
    // no other semi-colons should be present
    // i.e. "MWF 02:00-2:50 pm;T 10:00-11:50 am"
    public ScheduleParser(String scheduleStr) {
        mScheduleStrs = scheduleStr.split(";");
    }

    public String[] getScheduleStrs() {
        return mScheduleStrs;
    }

    public Iterator<Meeting> iterator() {
        return this.new ParserIterator();
    }

    public class ParserIterator
        implements Iterator<Meeting> {

        private int mListIndex = 0;
        private int mDayIndex = 0;

        private String mCurrentDays;
        private LocalTime mCurrentStartTime;
        private Duration mCurrentDuration;

        @Override
        public boolean hasNext() {
            return (mListIndex < mScheduleStrs.length);
        }

        @Override
        public Meeting next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            // Initialize the current strings
            if (mCurrentDays == null
                || mCurrentStartTime == null
                || mCurrentDuration == null) {
                String curSchedule = mScheduleStrs[mListIndex];
                int index = curSchedule.indexOf(' ');
                mCurrentDays = curSchedule.substring(0, index);
                timeFromString(curSchedule
                               .substring(index+1,
                                          curSchedule.length()));
                mDayIndex = 0;
            }

            // Get the day char and increment the dayIndex
            char dayChar = mCurrentDays.charAt(mDayIndex++);

            Meeting meeting = new Meeting(dayFromCharacter(dayChar),
                                          mCurrentStartTime,
                                          mCurrentDuration);

            // Check if out of days for current time and duration
            if (mDayIndex >= mCurrentDays.length()) {
                mCurrentDays      = null;
                mCurrentStartTime = null;
                mCurrentDuration  = null;
                // and go to next list element
                mListIndex++;
                mDayIndex = 0;
            }

            return meeting;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private DayOfWeek dayFromCharacter(char dayChar) {
            switch (dayChar) {
            case 'U': return DayOfWeek.SUNDAY;
            case 'M': return DayOfWeek.MONDAY;
            case 'T': return DayOfWeek.TUESDAY;
            case 'W': return DayOfWeek.WEDNESDAY;
            case 'R': return DayOfWeek.THURSDAY;
            case 'F': return DayOfWeek.FRIDAY;
            case 'S': return DayOfWeek.SATURDAY;
            default:
                throw new IllegalArgumentException(String
                                                   .format("dayChar: %c",
                                                           dayChar));
            }
        }

        private void timeFromString(String durationString) {
            int durStringLen = durationString.length();

            char amPm = durationString.charAt(durStringLen-2);

            // Remove the " {a,p}m" from the end, and split on
            // the "-".
            String[] startAndEndTime =
                durationString.substring(0, durStringLen-3)
                .split("-");

            mCurrentStartTime = LocalTime.parse(startAndEndTime[0]);
            LocalTime endTime = LocalTime.parse(startAndEndTime[1]);

            if (amPm == 'p') {
                if (mCurrentStartTime.isBefore(LocalTime.NOON))
                    mCurrentStartTime = mCurrentStartTime.plusHours(12);
                if (endTime.isBefore(LocalTime.NOON))
                    endTime = endTime.plusHours(12);
            }

            mCurrentDuration = Duration.between(mCurrentStartTime,
                                                endTime);
        }

    }
}

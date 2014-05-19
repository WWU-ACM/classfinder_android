package edu.wwu.classfinder2.test.data;

import android.support.v4.util.ArrayMap;

import edu.wwu.classfinder2.data.ScheduleParser;
import edu.wwu.classfinder2.data.Schedule.Meeting;

import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.Duration;
import org.threeten.bp.LocalTime;

import org.threeten.bp.temporal.ChronoUnit;

public class ScheduleParserTest extends TestCase {

    public void testParse() {
        Map<String, Meeting[]> cases = new ArrayMap<String, Meeting[]>();
        cases.put("MT 10:00-10:50 am",
                  new Meeting[] {
                      new Meeting(DayOfWeek.MONDAY,
                                  LocalTime.of(10,0),
                                  Duration.of(50, ChronoUnit.MINUTES)),
                      new Meeting(DayOfWeek.TUESDAY,
                                  LocalTime.of(10,0),
                                  Duration.of(50, ChronoUnit.MINUTES))});
        cases.put("TR 12:00-01:50 pm",
                  new Meeting[] {
                      new Meeting(DayOfWeek.TUESDAY,
                                  LocalTime.of(12,0),
                                  Duration.of(110, ChronoUnit.MINUTES)),
                      new Meeting(DayOfWeek.THURSDAY,
                                  LocalTime.of(12,0),
                                  Duration.of(110, ChronoUnit.MINUTES))});

        for (Map.Entry<String, Meeting[]> entry : cases.entrySet()) {
            ScheduleParser sp = new ScheduleParser(entry.getKey());
            Meeting[] meetings = entry.getValue();
            int i = 0;
            for (Meeting m : sp) {
                Assert.assertTrue("", i < meetings.length);
                Assert.assertEquals("Meetings should parse equivalently",
                                    meetings[i++],
                                    m);
            }
        }
    }

}

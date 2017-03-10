package com.banksy.expandablecalendar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
  @Test public void addition_isCorrect() throws Exception {
    assertEquals(4, 2 + 2);
  }

  /**
   * 注意事项：
   * Calendar 的 month 从 0 开始，也就是全年 12 个月由 0 ~ 11 进行表示。
   * 而 Calendar.DAY_OF_WEEK 定义和值如下：
   * Calendar.SUNDAY = 1
   * Calendar.MONDAY = 2
   * Calendar.TUESDAY = 3
   * Calendar.WEDNESDAY = 4
   * Calendar.THURSDAY = 5
   * Calendar.FRIDAY = 6
   * Calendar.SATURDAY = 7
   * @see <a href="https://developer.android.com/reference/java/util/GregorianCalendar.html">From GregorianCalendar Android API </a>
   */
  @Test public void TextCalendar(){
    // get the supported ids for GMT-08:00 (Pacific Standard Time)
    String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);
    // if no ids were returned, something is wrong. get out.
    if (ids.length == 0)
      System.exit(0);

    // begin output
    System.out.println("Current Time");

    // create a Pacific Standard Time time zone
    SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);

    // set up rules for Daylight Saving Time
    pdt.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
    pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

    // create a GregorianCalendar with the Pacific Daylight time zone
    // and the current date and time
    Calendar calendar = new GregorianCalendar(pdt);
    Date trialTime = new Date();
    calendar.setTime(trialTime);

    // print out a bunch of interesting things
    System.out.println("ERA: " + calendar.get(Calendar.ERA));
    System.out.println("YEAR: " + calendar.get(Calendar.YEAR));
    System.out.println("MONTH: " + calendar.get(Calendar.MONTH));
    System.out.println("WEEK_OF_YEAR: " + calendar.get(Calendar.WEEK_OF_YEAR));
    System.out.println("WEEK_OF_MONTH: " + calendar.get(Calendar.WEEK_OF_MONTH));
    System.out.println("DATE: " + calendar.get(Calendar.DATE));
    System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));
    System.out.println("DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR));
    System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));
    System.out.println("DAY_OF_WEEK_IN_MONTH: "
        + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
    System.out.println("AM_PM: " + calendar.get(Calendar.AM_PM));
    System.out.println("HOUR: " + calendar.get(Calendar.HOUR));
    System.out.println("HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
    System.out.println("MINUTE: " + calendar.get(Calendar.MINUTE));
    System.out.println("SECOND: " + calendar.get(Calendar.SECOND));
    System.out.println("MILLISECOND: " + calendar.get(Calendar.MILLISECOND));
    System.out.println("ZONE_OFFSET: "
        + (calendar.get(Calendar.ZONE_OFFSET)/(60*60*1000)));
    System.out.println("DST_OFFSET: "
        + (calendar.get(Calendar.DST_OFFSET)/(60*60*1000)));

    System.out.println("Current Time, with hour reset to 3");
    calendar.clear(Calendar.HOUR_OF_DAY); // so doesn't override
    calendar.set(Calendar.HOUR, 3);
    System.out.println("ERA: " + calendar.get(Calendar.ERA));
    System.out.println("YEAR: " + calendar.get(Calendar.YEAR));
    System.out.println("MONTH: " + calendar.get(Calendar.MONTH));
    System.out.println("WEEK_OF_YEAR: " + calendar.get(Calendar.WEEK_OF_YEAR));
    System.out.println("WEEK_OF_MONTH: " + calendar.get(Calendar.WEEK_OF_MONTH));
    System.out.println("DATE: " + calendar.get(Calendar.DATE));
    System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));
    System.out.println("DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR));
    System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));
    System.out.println("DAY_OF_WEEK_IN_MONTH: "
        + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
    System.out.println("AM_PM: " + calendar.get(Calendar.AM_PM));
    System.out.println("HOUR: " + calendar.get(Calendar.HOUR));
    System.out.println("HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
    System.out.println("MINUTE: " + calendar.get(Calendar.MINUTE));
    System.out.println("SECOND: " + calendar.get(Calendar.SECOND));
    System.out.println("MILLISECOND: " + calendar.get(Calendar.MILLISECOND));
    System.out.println("ZONE_OFFSET: "
        + (calendar.get(Calendar.ZONE_OFFSET)/(60*60*1000))); // in hours
    System.out.println("DST_OFFSET: "
        + (calendar.get(Calendar.DST_OFFSET)/(60*60*1000))); // in hours
  }

  @Test public void TestCalenderData(){
    Calendar mCalendar = Calendar.getInstance();
    int mCurWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
    System.out.println("DAY_OF_WEEK ==> " + mCurWeek);
    System.out.println("DAY_OF_MONTH ==> " + mCalendar.get(Calendar.DAY_OF_MONTH));


    if (mCurWeek != 7) {// not Saturday
      mCalendar.add(Calendar.DAY_OF_YEAR,- mCalendar.get(Calendar.DAY_OF_WEEK));
    }

    System.out.println("DAY_OF_MONTH ==> " + mCalendar.get(Calendar.DAY_OF_MONTH));
    for (int i = 0; i < 6; i++) {
      mCalendar.add(Calendar.DAY_OF_YEAR, 1);
      System.out.println("==>>" + mCalendar.get(Calendar.DAY_OF_MONTH));
    }
  }

}
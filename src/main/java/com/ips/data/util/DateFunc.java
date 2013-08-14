/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ips.data.util;

import javax.servlet.http.*;

import java.util.Calendar;
import java.util.Locale;

/**
 *
 * @author pasquale
 */
public final class DateFunc
{

    /** Verifica se la data � corretta*/
    public boolean verifyDate(java.lang.String Day, java.lang.String Month, java.lang.String Year)
    {
        Integer myDay = new Integer(Day);
        Integer myMonth = new Integer(Month);
        Integer myYear = new Integer(Year);
        Calendar rightNow = Calendar.getInstance();
        rightNow.set(myYear.intValue(), (myMonth.intValue() - 1), 1);
        int Maximum = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (myDay.intValue() > Maximum)
        {
            return true;
        } else
        {
            return false;
        }
    }

    public static String JavaDateToAccessDate(java.sql.Date DateParam)
    {
        return new String("#" + (DateParam.getMonth() + 1) + "-" + DateParam.getDate() + "-" + (DateParam.getYear() + 1900) + "#");
    }

    public static String JavaDateToSqlServerDate(java.util.Calendar DateParam)
    {
        java.text.SimpleDateFormat myFormatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        java.util.Date tempDate = new java.util.Date(DateParam.getTimeInMillis());
        String myDate = myFormatter.format(tempDate);
        return myDate;

    }

    /** Effettua la comparazione tra due date con precisione = minuti*/
    public static long compareDate(java.util.Calendar DateRequested, java.util.Calendar DateToCompared, int Precision)
    {
        long MinuteDiffer = (DateRequested.getTimeInMillis() - DateToCompared.getTimeInMillis()) / (1000 * 60);
        /* le due date sono uguali*/
        if (java.lang.Math.abs(MinuteDiffer) <= Precision)
        {
            return 0;
        }
        return MinuteDiffer;
    }
       /** NB: L'intervallo � chiuso a sinistra e aperto a destra*/
    public static boolean IsBetween(java.util.Calendar CalendarParam, java.util.Calendar DateI, java.util.Calendar DateF)
    {
        long calendarParam = (CalendarParam.getTimeInMillis()) / (1000 * 60);
        long dateI = (DateI.getTimeInMillis()) / (1000 * 60);
        long dateF = (DateF.getTimeInMillis()) / (1000 * 60);
        if (calendarParam >= dateI && calendarParam < dateF)
        {
            return true;
        }
        return false;
    }

     public static java.util.Calendar buildDate(int DayParam, int MonthParam, int YearParam)
    {

        if (DayParam == 0 || MonthParam == 0 || YearParam == 0)
        {
            return null;
        } else
        {
            java.util.Calendar myDate = java.util.Calendar.getInstance(Locale.ITALY);
            myDate.set(YearParam, (MonthParam - 1), DayParam);
            myDate.set(java.util.Calendar.HOUR_OF_DAY, 0);
            myDate.set(java.util.Calendar.MINUTE, 0);
            myDate.set(Calendar.SECOND, 0);
            myDate.set(Calendar.MILLISECOND, 0);
            return myDate;
        }

    }
     public static java.util.Calendar makeDate(String DayLabel, String MonthLabel, String YearLabel, HttpServletRequest Request)
    {
        int Day = StringFunc.getInt(DayLabel, Request);
        int Month = StringFunc.getInt(MonthLabel, Request);
        int Year = StringFunc.getInt(YearLabel, Request);
        if (Day == 0 || Month == 0 || Year == 0)
        {
            return null;
        } else
        {
            java.util.Calendar myDate = java.util.Calendar.getInstance(Locale.ITALY);
            myDate.set(Year, (Month - 1), Day);
            myDate.set(java.util.Calendar.HOUR_OF_DAY, 12);
            myDate.set(java.util.Calendar.MINUTE, 0);
            myDate.set(Calendar.SECOND, 0);
            return myDate;
        }

    }
     public static java.util.Calendar makeDate(String DayLabel, String MonthLabel, String YearLabel, String HourLabel, String MinuteLabel, HttpServletRequest Request)
    {
        int Day = StringFunc.getInt(DayLabel, Request);
        int Month = StringFunc.getInt(MonthLabel, Request);
        int Year = StringFunc.getInt(YearLabel, Request);
        int Hour = StringFunc.getInt(HourLabel, Request);
        int Minute = StringFunc.getInt(MinuteLabel, Request);

        if (Day == 0 || Month == 0 || Year == 0)
        {
            return null;
        } else
        {
            java.util.Calendar myDate = java.util.Calendar.getInstance();
            myDate.set(Year, (Month - 1), Day);
            myDate.set(java.util.Calendar.HOUR_OF_DAY, Hour);
            myDate.set(java.util.Calendar.MINUTE, Minute);
            myDate.set(Calendar.SECOND, 1);
            return myDate;
        }

    }

}

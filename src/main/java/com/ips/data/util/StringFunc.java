package com.ips.data.util;

import java.util.*;
import javax.servlet.http.*;
import java.util.regex.*;
import java.io.*;


import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

public final class StringFunc
{

    private final int version = 19;

    public static String escape(String aURLFragment)
    {
        String result = null;
        try
        {
            result = URLEncoder.encode(aURLFragment, "UTF-8");
        } catch (UnsupportedEncodingException ex)
        {
            return "";
        }
        return result;
    }

    public static String escRegEx(String inStr)
    {
        return inStr.replaceAll("([\\\\*+\\[\\](){}\\$.?\\^|])", "\\\\$1");

    }

    public static String escapeJavascript(String param)
    {
        char[] myParam = param.toCharArray();
        String Result = "";
        for (char Car : myParam)
        {
            if (Car == '\'')
            {
                Result += "\\'";
            } else
            {
                Result += Car;
            }
        }
        return Result;
    }
    public static float getFloat(String FieldName, HttpServletRequest Request)
    {
        float myField;
        try
        {
            myField = Float.parseFloat(Request.getParameter(FieldName));
        } catch (NullPointerException npe)
        {
            myField = 0;
        } catch (NumberFormatException nfe)
        {
            myField = 0;
        }
        return myField;
    }
    public static String isNull(String Param, String ReplaceParam)
    {
        if(Param == null)
            return ReplaceParam;                    
        return Param;
    }

    public static String clearPeek(String StringToClear)
    {
        int StartIndex = 0;
        int EndIndex;
        EndIndex = StringToClear.indexOf('\'', StartIndex);
        if (EndIndex == -1) // carattere non trovato, restituisci stringa invariata
        {
            return StringToClear;
        }
        String clearedString = new String();
        do
        {
            clearedString += StringToClear.substring(StartIndex, ++EndIndex);
            clearedString += '\''; //aggiungi un apice
            StartIndex = EndIndex;
            EndIndex = StringToClear.indexOf('\'', StartIndex);
        } while (EndIndex != -1);
        clearedString += StringToClear.substring(StartIndex, StringToClear.length());
        return clearedString;

    }

    
    public static String getString(String FieldName, HttpServletRequest Request)
    {
        try
        {
            String myField = Request.getParameter(FieldName);
            if (myField != null)
            {
                return new String(myField.getBytes(),"UTF-8");

            }
        } catch (NullPointerException npe)
        {
            return new String("");
        }
        catch (UnsupportedEncodingException usee)
        {
            return new String("");
        }
        return new String("");
    }

    public static String getString(String FieldName, int lengthParam, HttpServletRequest Request)
    {
        try
        {
            String myField = Request.getParameter(FieldName);
            /* test if is not null*/
            if (myField != null)
            {
                if (myField.length() > lengthParam)
                {
                    myField = myField.substring(0, lengthParam);
                }
                return new String(myField.getBytes(),"UTF-8");
            }
        } catch (NullPointerException npe)
        {
            return new String("");
        }
        catch (UnsupportedEncodingException usee)
        {
            return new String("");
        }
        return new String("");
    }

    public static int getInt(String FieldName, HttpServletRequest Request)
    {
        int myField;
        try
        {
            myField = Integer.parseInt(Request.getParameter(FieldName));
            /* test if is not null*/
        } catch (NullPointerException npe)
        {
            myField = 0;
        } catch (NumberFormatException nfe)
        {
            myField = 0;
        }
        return myField;
    }

 

    


    /* ricerca un valore numerico in un array restituendone la posizione*/
    public static int match(int[] ArrayParam, int value)
    {
        for (int i = 0; i < ArrayParam.length; i++)
        {
            if (ArrayParam[i] == value)
            {
                return i;
            }
        }
        return -1;
    }
    /* ricerca una stringa in un array restituendone la posizione*/

    public static int match(String[] ArrayParam, String value)
    {
        for (int i = 0; i < ArrayParam.length; i++)
        {
            if (ArrayParam[i].equalsIgnoreCase(value))
            {
                return i;
            }
        }
        return -1;
    }

    public static String view(String param)
    {
        if (param != null && param.length() != 0)
        {
            return param;
        }
        return "";
    }

    public static String view(int param)
    {
        if (param != 0)
        {
            return String.valueOf(param);
        }
        return "";
    }

    public static String view(float param)
    {
        if (param != 0)
        {
            return String.valueOf(param);
        }
        return "";
    }

    public static String view(java.util.Calendar calendarParam)
    {
        String DateFormatted = "";
        if (calendarParam != null)
        {
            int Day = calendarParam.get(Calendar.DATE);
            if (Day < 10)
            {
                DateFormatted += "0";
            }
            DateFormatted += Day + ".";
            int Month = calendarParam.get(Calendar.MONTH) + 1;
            if (Month < 10)
            {
                DateFormatted += "0";
            }
            DateFormatted += Month + ".";
            DateFormatted += calendarParam.get(Calendar.YEAR);
            return DateFormatted;

        } else
        {
            return "";
        }
    }

    public static String viewCompleteDate(java.util.Calendar calendarParam)
    {
        if (calendarParam != null)
        {
            String DateFormatted = view(calendarParam);
            DateFormatted += " ";
            int Hour = calendarParam.get(Calendar.HOUR_OF_DAY);
            if (Hour < 10)
            {
                DateFormatted += "0";
            }
            DateFormatted += Hour + ":";
            int Minutes = calendarParam.get(Calendar.MINUTE);
            if (Minutes < 10)
            {
                DateFormatted += 0;
            }
            DateFormatted += Minutes;
            return DateFormatted;
        } else
        {
            return "";
        }
    }

    public static String view(java.sql.Date param)
    {
        String DateFormatted = "";
        if (param != null)
        {
            int Day = param.getDate();
            if (Day < 10)
            {
                DateFormatted += "0";
            }
            DateFormatted += Day + ".";
            int Month = param.getMonth() + 1;
            if (Month < 10)
            {
                DateFormatted += "0";
            }
            DateFormatted += Month + ".";
            DateFormatted += param.getYear() + 1900;
            return DateFormatted;
        }
        return "";
    }

    public static String view(java.sql.Timestamp param)
    {
        String DateFormatted = "";
        if (param != null)
        {
            Calendar myDate = java.util.Calendar.getInstance();
            myDate.setTimeInMillis(param.getTime());
            return view(myDate);
        }
        return "";
    }

   
   

   
 
    /** Advanced String Handling Utilities **************************** db2k8 */
    public static String trimLongWords(String s, int l)
    {
        Matcher myMatcher = Pattern.compile("[^ ]{" + l + ",}").matcher(s);
        while (myMatcher.find())
        {
            String myMatch = myMatcher.group(0);
            s = s.replace(myMatch, myMatch.substring(0, l - 1) + "&#133;");
        }
        return s;
    }

    public static String trimLongWords(String s)
    {
        return trimLongWords(s, 40);
    }

    public static String stripHTML(String text)
    {
        Matcher myMatcher = Pattern.compile("<html[ >].*", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE).matcher(text);
        return myMatcher.replaceAll("");
    }

    public static String getAlpha(String s)
    {
        Matcher myMatcher = Pattern.compile("^([a-zA-Z_]+)[0-9]+").matcher(s);
        if (myMatcher.find())
        {
            return myMatcher.group(1);
        } else
        {
            return "";
        }
    }

    public static String autoLinkURL(String s, String t)
    {
        return s.replaceAll("(https?://[^\"\\s<>]*[^.,;'\">:\\s<>\\)\\]!])", "<a href=\"$1\" target=\"" + t + "\">$1</a>");
    }

    public static String ISO2HTML(String s)
    {
        Matcher iso_matcher = Pattern.compile("=\\?(iso-8859-\\d+|windows-1252)\\?Q\\?(.*?)\\?=", Pattern.CASE_INSENSITIVE).matcher(s);
        while (iso_matcher.find())
        { // is ISO/Win-encoded
            String encoded_txt = iso_matcher.group(0);
            String decoded_txt = iso_matcher.group(2);
            // Convert characters
            Matcher char_matcher = Pattern.compile("=([0-9A-E]{2})", Pattern.CASE_INSENSITIVE).matcher(decoded_txt);
            while (char_matcher.find())
            {
                decoded_txt = decoded_txt.replace(char_matcher.group(0), "&#" + Integer.parseInt(char_matcher.group(1), 16) + ";");
            }
            // Convert spaces
            decoded_txt = decoded_txt.replaceAll("_", " ");
            s = s.replace(encoded_txt, decoded_txt);
        }
        return s;
    }

    public static String stringToHTMLString(String string)
    {
        StringBuffer sb = new StringBuffer(string.length());
        // true if last char was blank
        boolean lastWasBlankChar = false;
        int len = string.length();
        char c;

        for (int i = 0; i < len; i++)
        {
            c = string.charAt(i);
            if (c == ' ')
            {
                // blank gets extra work,
                // this solves the problem you get if you replace all
                // blanks with &nbsp;, if you do that you loss
                // word breaking
                if (lastWasBlankChar)
                {
                    lastWasBlankChar = false;
                    sb.append("&nbsp;");
                } else
                {
                    lastWasBlankChar = true;
                    sb.append(' ');
                }
            } else
            {
                lastWasBlankChar = false;
                //
                // HTML Special Chars
                if (c == '"')
                {
                    sb.append("&quot;");
                } else if (c == '&')
                {
                    sb.append("&amp;");
                } else if (c == '<')
                {
                    sb.append("&lt;");
                } else if (c == '>')
                {
                    sb.append("&gt;");
                } else if (c == '\n') // Handle Newline
                {
                    sb.append("<br>");
                } else
                {
                    int ci = 0xffff & c;
                    if (ci < 160) // nothing special only 7 Bit
                    {
                        sb.append(c);
                    } else
                    {
                        // Not 7 Bit use the unicode system
                        sb.append("&#");
                        sb.append(new Integer(ci).toString());
                        sb.append(';');
                    }
                }
            }
        }
        return sb.toString();
    }

    public static final String escapeHTML(String s)
    {
        if (s == null)
        return "";
        StringBuffer sb = new StringBuffer();
        int n = s.length();
        for (int i = 0; i < n; i++)
        {
            char c = s.charAt(i);
            switch (c)
            {
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                case '"':
                    sb.append("&quot;");
                    break;
                case 'à':
                    sb.append("&agrave;");
                    break;
                case 'À':
                    sb.append("&Agrave;");
                    break;
                case 'â':
                    sb.append("&acirc;");
                    break;
                case 'Â':
                    sb.append("&Acirc;");
                    break;
                case 'ä':
                    sb.append("&auml;");
                    break;
                case 'Ä':
                    sb.append("&Auml;");
                    break;
                case 'å':
                    sb.append("&aring;");
                    break;
                case 'Å':
                    sb.append("&Aring;");
                    break;
                case 'æ':
                    sb.append("&aelig;");
                    break;
                case 'Æ':
                    sb.append("&AElig;");
                    break;
                case 'ç':
                    sb.append("&ccedil;");
                    break;
                case 'Ç':
                    sb.append("&Ccedil;");
                    break;
                case 'é':
                    sb.append("&eacute;");
                    break;
                case 'É':
                    sb.append("&Eacute;");
                    break;
                case 'è':
                    sb.append("&egrave;");
                    break;
                case 'È':
                    sb.append("&Egrave;");
                    break;
                case 'ê':
                    sb.append("&ecirc;");
                    break;
                case 'Ê':
                    sb.append("&Ecirc;");
                    break;
                case 'ë':
                    sb.append("&euml;");
                    break;
                case 'Ë':
                    sb.append("&Euml;");
                    break;
                case 'ï':
                    sb.append("&iuml;");
                    break;
                case 'Ï':
                    sb.append("&Iuml;");
                    break;
                case 'ô':
                    sb.append("&ocirc;");
                    break;
                case 'Ô':
                    sb.append("&Ocirc;");
                    break;
                case 'ö':
                    sb.append("&ouml;");
                    break;
                case 'Ö':
                    sb.append("&Ouml;");
                    break;
                case 'ø':
                    sb.append("&oslash;");
                    break;
                case 'Ø':
                    sb.append("&Oslash;");
                    break;
                case 'ß':
                    sb.append("&szlig;");
                    break;
                case 'ù':
                    sb.append("&ugrave;");
                    break;
                case 'Ù':
                    sb.append("&Ugrave;");
                    break;
                case 'û':
                    sb.append("&ucirc;");
                    break;
                case 'Û':
                    sb.append("&Ucirc;");
                    break;
                case 'ü':
                    sb.append("&uuml;");
                    break;
                case 'Ü':
                    sb.append("&Uuml;");
                    break;
                case '®':
                    sb.append("&reg;");
                    break;
                case '©':
                    sb.append("&copy;");
                    break;
                case '€':
                    sb.append("&euro;");
                    break;
                case '\n':
                    sb.append("<br>");
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }

    
   
    public static String safeFilename(String FileNameParam, String ReplaceParam)
    {
        String[] SafeArray =
        {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
        };
        char[] DirtyFileName = FileNameParam.toCharArray();
        String safeFileName = "";
        for (int i = 0; i < DirtyFileName.length; i++)
        {
            String toCheck = "" + DirtyFileName[i];
            int found = StringFunc.match(SafeArray, toCheck);
            if (found != -1)
            {
                safeFileName += DirtyFileName[i];
            } else
            {
                safeFileName += ReplaceParam;
            }

        }
        return safeFileName;
    }

    /** some useful string functions*/
    public static String removeSpaces(String s)
    {
        StringTokenizer st = new StringTokenizer(s, " ", false);
        String t = "";
        while (st.hasMoreElements())
        {
            t += st.nextElement();
        }
        return t;
    }

    public static String removeCharacter(String s, char CharParam)
    {
        String mySplitter = new String("" + CharParam);
        StringTokenizer st = new StringTokenizer(s, mySplitter, false);
        String t = "";
        while (st.hasMoreElements())
        {
            t += st.nextElement();
        }
        return t;
    }

    public static String extractDigits(String StringParam)
    {
        String[] SafeArray =
        {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"
        };
        char[] DigitString = StringParam.toCharArray();
        String DigitOutput = "";
        for (int i = 0; i < DigitString.length; i++)
        {
            String toCheck = "" + DigitString[i];
            int found = StringFunc.match(SafeArray, toCheck);
            if (found != -1)
            {
                DigitOutput += DigitString[i];
            } else
            {
                DigitOutput += "";
            }

        }
        return DigitOutput;
    }

    public static String getStackTrace(Throwable aThrowable)
    {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }
    public static String viewTime(java.util.Calendar calendarParam)
    {
        if (calendarParam != null)
        {

            String DateFormatted = "";
            int Hour = calendarParam.get(Calendar.HOUR_OF_DAY);
            if (Hour < 10)
            {
                DateFormatted += "0";
            }
            DateFormatted += Hour + ":";
            int Minutes = calendarParam.get(Calendar.MINUTE);
            if (Minutes < 10)
            {
                DateFormatted += 0;
            }
            DateFormatted += Minutes;
            return DateFormatted;
        } else
        {
            return "";
        }
    }
    public static String viewCompleteTime(java.util.Calendar calendarParam)
    {
        if (calendarParam != null)
        {

            String DateFormatted = "";
            int Hour = calendarParam.get(Calendar.HOUR_OF_DAY);
            if (Hour < 10)
            {
                DateFormatted += "0";
            }
            DateFormatted += Hour + ":";
            int Minutes = calendarParam.get(Calendar.MINUTE);
            if (Minutes < 10)
            {
                DateFormatted += 0;
            }
            DateFormatted += Minutes;
            int Seconds = calendarParam.get(Calendar.SECOND);
            DateFormatted += ":" + Seconds;
            return DateFormatted;
        } else
        {
            return "";
        }
    }
    public static String fString(String Param, int length, int align)
    {
        String Output ="";
        if(Param == null)
        {
        }    
        return Output;
        
    }


}




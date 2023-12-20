package com.example.myapplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String formatDateTimeString(String dateTime) {
        SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        try {
            // Parse the string to Date
            Date date = inputFormatter.parse(dateTime);
            // Define the output format
            SimpleDateFormat outputFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            // Format and print the datetime with only hour, minute, and date
            String shortFormattedDate = outputFormatter.format(date);
            return shortFormattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
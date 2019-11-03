package com.splitwise;

import com.splitwise.exceptions.NoSuchUserException;
import com.splitwise.models.User;

public class Utils {
    public static double roundOff(double value) {
        return ((long) (value * 100)) / 100.0d;
    }

    public static boolean isApproxEqual(double v1, double v2) {
        return (Math.abs(v1 - v2) / (Math.min(Math.abs(v1), Math.abs(v2)))) < 1e-10; // relative error
    }

    public static User getUser(String userIdentification) throws NoSuchUserException {
        BookKeeper bk = BookKeeper.getInstance();
        User user;
        try {
            Long userId = Long.parseLong(userIdentification);
            user = bk.getUser(userId);
        } catch (NumberFormatException e) {
            String userEmail = userIdentification.strip();
            user = bk.getUser(userEmail);
        }
        return user;
    }
}
package common;

import java.util.Date;

public class Result {
    final String message;
    final boolean isSuccessful;
    final Date date;

    public Result(String message, boolean isSuccessful, Date date) {
        this.message = message;
        this.isSuccessful = isSuccessful;
        this.date = date;
    }
}

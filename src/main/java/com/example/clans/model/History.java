package com.example.clans.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

public class History {
    String from;
    String fromName;
    String to;
    String toName;
    BigDecimal gold;
    Date date;
    Time time;

    public History(String from, String fromName, String to, String toName, BigDecimal gold, Date date, Time time) {
        this.from = from;
        this.fromName = fromName;
        this.to = to;
        this.toName = toName;
        this.gold = gold;
        this.date = date;
        this.time = time;
    }

    public String toString() {
        return "{" +
                "\n\t \"from\":\"" + from + "\"" +
                ",\n\t \"sender name\":\"" + fromName + "\"" +
                ",\n\t \"to\":\"" + to + "\"" +
                ",\n\t \"receiver name\":\"" + toName + "\"" +
                ",\n\t \"gold\":" + gold +
                ",\n\t \"dateTime\":\"" + date + 'T' + time + "\"" +
                "\n}\n";
    }
}

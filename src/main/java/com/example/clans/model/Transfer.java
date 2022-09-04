package com.example.clans.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.Time;

public class Transfer {
    private Long id;
    private Long fromId;
    private Long toId;
    private BigDecimal gold;
    private String sentFrom;
    private String sentTo;
    private Date date;
    private Time time;

    public Transfer(Long id, Long fromId, Long toId, BigDecimal gold, String sentFrom, String sentTo, Date date, Time time) {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
        this.gold = gold;
        this.sentFrom = sentFrom;
        this.sentTo = sentTo;
        this.date = date;
        this.time = time;
    }
    public Transfer(Long fromId, Long toId, BigDecimal gold, String sentFrom, String sentTo) {
        this.fromId = fromId;
        this.toId = toId;
        this.gold = gold;
        this.sentFrom = sentFrom;
        this.sentTo = sentTo;
    }
    public Transfer() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public BigDecimal getGold() {
        return gold;
    }

    public void setGold(BigDecimal gold) {
        this.gold = gold.setScale(2, RoundingMode.FLOOR);
    }

    public String getSentFrom() {
        return sentFrom;
    }

    public void setSentFrom(String sentFrom) {
        this.sentFrom = sentFrom;
    }

    public String getSentTo() {
        return sentTo;
    }

    public void setSentTo(String sentTo) {
        System.out.println("sent to" + sentTo);
        this.sentTo = sentTo;
        System.out.println(this.sentTo);
    }

    public Date getDate() {
        return date;
    }

    public void setDateTime(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "{" +
                "\n\t\"id\":" + id +
                ",\n\t \"fromId\":" + fromId +
                ",\n\t \"toId\":" + toId +
                ",\n\t \"gold\":" + gold +
                ",\n\t \"sentFrom\":\"" + sentFrom + '\"' +
                ",\n\t \"sentTo\":\"" + sentTo + '\"' +
                ",\n\t \"dateTime\":\"" + date + 'T' + time + "\"" +
                "\n}\n";
    }

}

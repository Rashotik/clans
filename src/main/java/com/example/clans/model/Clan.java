package com.example.clans.model;

import java.math.BigDecimal;

public class Clan {
    private Long id;
    private String name;
    private BigDecimal gold;

    Clan(){

    }

    public Clan(String name, BigDecimal gold) {
        this.name = name;
        this.gold = gold;
    }

    public Clan(Long id, String name, BigDecimal gold) {
        this.id = id;
        this.name = name;
        this.gold = gold;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getGold() {
        return gold;
    }

    public void setGold(BigDecimal gold) {
        this.gold = gold;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"name\":" + "\"" + name + "\"" +
                ", \"gold\":" + gold +
                '}';
    }
}

package com.example.clans.model;

import java.math.BigDecimal;

public class Player {
    private Long id;
    private String name;
    private BigDecimal gold;
    private Long clanId;
    //Other fields

    public Player(Long id, String nickname, BigDecimal gold, Long clanId) {
        this.id = id;
        this.name = nickname;
        this.gold = gold;
        this.clanId = clanId;
    }
    public Player(String nickname, BigDecimal gold, Long clanId) {
        this.name = nickname;
        this.gold = gold;
        this.clanId = clanId;
    }
    public Player(String nickname, BigDecimal gold) {
        this.name = nickname;
        this.gold = gold;
    }
    public Player() {

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

    public Long getClanId() {
        return clanId;
    }

    public void setClanId(Long clanId) {
        this.clanId = clanId;
    }
}

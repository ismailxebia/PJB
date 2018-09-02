package com.example.helmi.pjbdata;

public class DataChart {
    private String name,apos,bpos,cpops;
    private int id;

    public DataChart(String name, String apos, String bpos, String cpops, int id) {
        this.name = name;
        this.apos = apos;
        this.bpos = bpos;
        this.cpops = cpops;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApos() {
        return apos;
    }

    public void setApos(String apos) {
        this.apos = apos;
    }

    public String getBpos() {
        return bpos;
    }

    public void setBpos(String bpos) {
        this.bpos = bpos;
    }

    public String getCpops() {
        return cpops;
    }

    public void setCpops(String cpops) {
        this.cpops = cpops;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


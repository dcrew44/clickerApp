package com.example.clickerapp;

public class Cookie {
    public long tClicks; //total clicks
    public long cClicks; //current clicks
    public long cPS; //current clicks per second
    public long cPC; //current clicks per click
    public long autoClickers; //number of autoclickers
    public int autoClickerLevel; //level of autoclickers
    public int autoClickRate; //rate of autoclickers
    public Cookie() {
        tClicks = 0;
        cClicks = 0;
        autoClickers = 0;
        cPC = 1;
        autoClickerLevel = 1;
        autoClickRate = 1000;
    }
    public void click() {
        tClicks+=cPC;
        cClicks+=cPC;
    }
    public void addAutoClicker() {
        autoClickers++;
    }
    public void update() {
        long autoCPC = autoClickerLevel * autoClickers;
        tClicks += autoCPC;
        cClicks += autoCPC;
    }
    public void upgradeAutoClicker() {
        autoClickerLevel++;
    }
    public void upgradeClicker() {
        cPC++;
    }
    public String getClicks() {
        return Long.toString(cClicks);
    }

    public int getAutoClickRate() {
        return autoClickRate;
    }
}

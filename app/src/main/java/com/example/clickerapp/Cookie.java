package com.example.clickerapp;

public class Cookie {
    public long tClicks; //total clicks
    public long cClicks; //current clicks
    public long cPS; //current clicks per second
    public long cPC; //current clicks per click
    public int clickLevel; //level of clicker
    public long autoClickers; //number of autoclickers
    public int autoClickerLevel; //level of autoclickers
    public int autoClickRate; //rate of autoclickers
    public long autoClickerCost; //cost of autoclickers
    public long autoClickerUpgradeCost; //cost of upgrading autoclickers
    public long upgradeCost; //cost of upgrading clicker
    private final long MULTIPLIER = (long) 1.2;
    public Cookie() {
        tClicks = 0;
        cClicks = 0;
        autoClickers = 0;
        cPC = 1;
        autoClickerLevel = 1;
        autoClickRate = 1000;
        autoClickerCost = 10;
        autoClickerUpgradeCost = 10;
        upgradeCost = 10;
        clickLevel = 1;
    }
    public void click() {
        tClicks+=cPC;
        cClicks+=cPC;
    }
    public boolean addAutoClicker() {
        if (autoClickerCost <= cClicks) {
            cClicks -= autoClickerCost;
            autoClickers++;
            autoClickerCost = (long) (autoClickerCost * Math.pow(MULTIPLIER, autoClickers));
            return true;
        } else {
            return false;
        }
    }

    public boolean upgradeAutoClicker() {
        if (autoClickerUpgradeCost <= cClicks) {
            cClicks -= autoClickerUpgradeCost;
            autoClickRate /= 2;
            autoClickerUpgradeCost = (long) (autoClickerUpgradeCost * Math.pow(MULTIPLIER, autoClickerLevel));
            autoClickerLevel++;
            return true;
        } else {
            return false;
        }
    }
    public boolean upgradeClicker() {
        if (upgradeCost <= cClicks) {
            cClicks -= upgradeCost;
            cPC += Math.pow(1.4, clickLevel);
            upgradeCost = (long) (upgradeCost * Math.pow(MULTIPLIER, cPC));
            clickLevel++;
            return true;
        } else {
            return false;
        }
    }
    public void update() {
        long autoCPC = autoClickerLevel * autoClickers;
        tClicks += autoCPC;
        cClicks += autoCPC;
    }
    public String getClicks() {
        return Long.toString(cClicks);
    }

    public int getAutoClickRate() {
        return autoClickRate;
    }
    public String getAutoClickerCost() {
        return Long.toString(autoClickerCost);
    }
    public String getAutoClickerUpgradeCost() {
        return Long.toString(autoClickerUpgradeCost);
    }
    public String getUpgradeCost() {
        return Long.toString(upgradeCost);
    }

}

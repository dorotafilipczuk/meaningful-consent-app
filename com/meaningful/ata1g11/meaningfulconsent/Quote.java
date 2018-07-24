package com.meaningful.ata1g11.meaningfulconsent;

public class Quote {
    private int Pending;
    private Boolean[] Prefs;

    public Quote(Boolean[] prefs, int pending) {
        this.Prefs = prefs;
        this.Pending = pending;
    }

    public int getPending() {
        return this.Pending;
    }

    public void setPending(int pending) {
        this.Pending = pending;
    }

    public Boolean[] getPrefs() {
        return this.Prefs;
    }

    public void setPrefs(Boolean[] prefs) {
        this.Prefs = prefs;
    }
}

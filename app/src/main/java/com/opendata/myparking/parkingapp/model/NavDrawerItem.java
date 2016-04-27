package com.opendata.myparking.parkingapp.model;

/**
 * Created by is chan on 17/04/2016.
 */
public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    int icon;

    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title) {
        this.showNotify = showNotify;
        this.title = title;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon(){
        return this.icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIcon(int icon) { this.icon = icon;}
}

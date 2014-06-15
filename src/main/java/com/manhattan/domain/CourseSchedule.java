package com.manhattan.domain;

/**
 * Created by Administrator on 2014/6/16 0016.
 */
public class CourseSchedule {

    private boolean forenoon;
    private boolean afternoon;
    private boolean aftersix;

    public boolean isForenoon() {
        return forenoon;
    }

    public void setForenoon(boolean forenoon) {
        this.forenoon = forenoon;
    }

    public boolean isAfternoon() {
        return afternoon;
    }

    public void setAfternoon(boolean afternoon) {
        this.afternoon = afternoon;
    }

    public boolean isAftersix() {
        return aftersix;
    }

    public void setAftersix(boolean aftersix) {
        this.aftersix = aftersix;
    }
}

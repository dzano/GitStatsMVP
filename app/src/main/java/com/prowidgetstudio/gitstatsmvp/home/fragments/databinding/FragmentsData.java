package com.prowidgetstudio.gitstatsmvp.home.fragments.databinding;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class FragmentsData extends BaseObservable {

    String totalDay, countDay, dateDay;
    String countWeek, dateWeek;
    String countMonth, dateMonth, firstDateMonth, lastDateMonth;

    //tab1
    @Bindable
    public String getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(String totalDay) {
        this.totalDay = totalDay;
        notifyPropertyChanged(BR.totalDay);
    }

    @Bindable
    public String getCountDay() {
        return countDay;
    }

    public void setCountDay(String countDay) {
        this.countDay = countDay;
        notifyPropertyChanged(BR.countDay);
    }

    @Bindable
    public String getDateDay() {
        return dateDay;
    }

    public void setDateDay(String dateDay) {
        this.dateDay = dateDay;
        notifyPropertyChanged(BR.dateDay);
    }

    //tab2
    @Bindable
    public String getCountWeek() {
        return countWeek;
    }

    public void setCountWeek(String countWeek) {
        this.countWeek = countWeek;
        notifyPropertyChanged(BR.countWeek);
    }

    @Bindable
    public String getDateWeek() {
        return dateWeek;
    }

    public void setDateWeek(String dateWeek) {
        this.dateWeek = dateWeek;
        notifyPropertyChanged(BR.dateWeek);
    }

    //tab3
    @Bindable
    public String getCountMonth() {
        return countMonth;
    }

    public void setCountMonth(String countMonth) {
        this.countMonth = countMonth;
        notifyPropertyChanged(BR.countMonth);
    }

    @Bindable
    public String getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(String dateMonth) {
        this.dateMonth = dateMonth;
        notifyPropertyChanged(BR.dateMonth);
    }

    @Bindable
    public String getFirstDateMonth() {
        return firstDateMonth;
    }

    public void setFirstDateMonth(String firstDateMonth) {
        this.firstDateMonth = firstDateMonth;
        notifyPropertyChanged(BR.firstDateMonth);
    }

    @Bindable
    public String getLastDateMonth() {
        return lastDateMonth;
    }

    public void setLastDateMonth(String lastDateMonth) {
        this.lastDateMonth = lastDateMonth;
        notifyPropertyChanged(BR.lastDateMonth);
    }



}

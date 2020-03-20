package com.handen.lab.data;

public class Record {
    private String surname;
    private String phone;
    private String yearString;
    private short year;
    private boolean isChanged;

    public Record(String surname, String phone, short year) {
        this.surname = surname;
        this.phone = phone;
        this.year = year;
    }

    public Record() {
        surname = "";
        phone = "";

    }

    public String getYearString() {
        if(year == 0)
            return "";
        else
            return Integer.toString(year);
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public short getYear() {
        return year;
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }
}

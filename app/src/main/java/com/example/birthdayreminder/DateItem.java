package com.example.birthdayreminder;

public class DateItem extends ListItem {

    private String month;

    public DateItem(int month) {
        switch (month) {
            case 1: this.setMonth("Januar"); break;
            case 2: this.setMonth("Februar"); break;
            case 3: this.setMonth("März"); break;
            case 4: this.setMonth("April"); break;
            case 5: this.setMonth("Mai"); break;
            case 6: this.setMonth("Juni"); break;
            case 7: this.setMonth("Juli"); break;
            case 8: this.setMonth("August"); break;
            case 9: this.setMonth("September"); break;
            case 10: this.setMonth("Oktober"); break;
            case 11: this.setMonth("November"); break;
            case 12: this.setMonth("Dezember"); break;
            default: this.setMonth("unknown"); break;
        }
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public int getType() {
        return TYPE_DATE;
    }


}

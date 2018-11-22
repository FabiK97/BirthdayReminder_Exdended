package com.example.birthdayreminder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BirthdayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    List<Birthday> birthdays;
    List<ListItem> groupedList;
    BirthdayDao birthdayDao;
    public NotifyInterface notifyInterface; //Diese Variable kann nicht nur Interfaces aufnehmen, sondern auch Klassen die dieses Interface implementieren

    public BirthdayAdapter(Context context,BirthdayDao birthdayDao, NotifyInterface notifyInterface) {
        this.mContext = context;
        this.notifyInterface = notifyInterface;
        this.birthdayDao = birthdayDao;
        this.birthdays = birthdayDao.getAllBirthdays();
        this.groupList();
    }

    public void updateData(BirthdayDao birthdayDao) {
        this.birthdays = birthdayDao.getAllBirthdays();
        this.groupList();
        this.notifyDataSetChanged();
    }

    public void removeItem(int position){
        this.groupedList.remove(position);
        this.notifyItemRemoved(position);

        if((position==this.groupedList.size() || this.groupedList.get(position).getType()==0) && this.groupedList.get(position - 1).getType()==0) {
            this.groupedList.remove(position-1);
            this.notifyItemRemoved(position-1);
        }

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());



        switch (i) {
            case ListItem.TYPE_BIRTHDAY:
                View v1 = inflater.inflate(R.layout.birthday_item, parent, false);
                viewHolder = new BirthdayItemHolder(v1);
                break;
            case ListItem.TYPE_DATE:
                View v2 = inflater.inflate(R.layout.date_item, parent, false);
                viewHolder = new DateItemHolder(v2);
                break;
        }
        //View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.birthday_item, parent, false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {

            case ListItem.TYPE_BIRTHDAY:
                Birthday birthday = (Birthday) groupedList.get(position);

                BirthdayItemHolder dbItemHolder = (BirthdayItemHolder) holder;
                dbItemHolder.name.setText(birthday.getName());
                dbItemHolder.birthday.setText(birthday.getDay() + "." + birthday.getMonth() + "." + birthday.getYear());
                dbItemHolder.birthdayObj = birthday;
                dbItemHolder.age.setText(String.valueOf(getDiffYears(new Date(), birthday.getYear(), birthday.getMonth(), birthday.getDay())));
                dbItemHolder.birthdayDao = this.birthdayDao;
                dbItemHolder.itemView.setTag(birthday.id);

                dbItemHolder.bindView(position, notifyInterface);
                break;

            case ListItem.TYPE_DATE:
                DateItem dateItem = (DateItem) groupedList.get(position);

                DateItemHolder dateItemHolder = (DateItemHolder) holder;
                dateItemHolder.monthView.setText(dateItem.getMonth());
                break;
        }

    }

    @Override
    public int getItemCount() {
        return groupedList != null ? groupedList.size() : 0;
    }

    public int getDiffYears(Date birth, int y, int m, int d) {
        Calendar a = getCalendar(birth);
        int diff = y - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > m ||
                (a.get(Calendar.MONTH) == m && a.get(Calendar.DATE) > d)) {
            diff--;
        }
        return -diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.GERMAN);
        cal.setTime(date);
        return cal;
    }

    public void groupList() {
        this.groupedList = new ArrayList<ListItem>();

        int temp = 0;

        for(Birthday birthday : birthdays) {
            int month = birthday.getMonth();
            if(month != temp){
                DateItem dateItem = new DateItem(month);
                this.groupedList.add(dateItem);
                this.groupedList.add(birthday);
                temp = month;
            } else {
                this.groupedList.add(birthday);
            }
        }

        for(ListItem i : groupedList) {
            Log.d("TESTLIST", i.toString());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return groupedList.get(position).getType();
    }

    interface NotifyInterface { //Dieses Interface wird hier erstellt, weil es als externes Interface keinen Sinn machen würde. Es ist ein "nested" Interface und wird über Klassenname.Interface implementiert.
        void notifyEvent(int position);
    }
}

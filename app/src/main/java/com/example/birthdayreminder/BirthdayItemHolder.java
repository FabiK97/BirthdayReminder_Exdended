package com.example.birthdayreminder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class BirthdayItemHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView birthday;
    public TextView age;
    public Birthday birthdayObj;
    public BirthdayDao birthdayDao;

    private BirthdayAdapter.NotifyInterface notifyInterface;
    private int position;

    public BirthdayItemHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name_list_item);
        birthday = itemView.findViewById(R.id.birthday_list_item);
        age = itemView.findViewById(R.id.age_list_item);

    }

    public void bindView(final int position, final BirthdayAdapter.NotifyInterface notifyInterface) {
//        itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                birthdayDao.deleteBirthday(birthdayObj);
//                notifyInterface.notifyEvent(position);
//                return true;
//            }
//        });
        this.notifyInterface = notifyInterface;
        this.position = position;
    }


}

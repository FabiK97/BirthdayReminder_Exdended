package com.example.birthdayreminder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class DateItemHolder extends RecyclerView.ViewHolder {

    protected TextView monthView;

    public DateItemHolder(@NonNull View itemView) {
        super(itemView);

        this.monthView = itemView.findViewById(R.id.monthView);
    }
}

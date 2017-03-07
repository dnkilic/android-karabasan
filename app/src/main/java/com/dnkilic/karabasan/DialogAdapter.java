package com.dnkilic.karabasan;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.ViewHolder>{

    private ArrayList<Dialog> dialogList;
    private Activity activity;

    public DialogAdapter(ArrayList<Dialog> errorDialogList, MainActivity activity) {
        this.dialogList = errorDialogList;
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuestion;
        TextView tvSign;

        public ViewHolder(View itemView) {
            super(itemView);

            Typeface font = Typeface.createFromAsset(activity.getApplicationContext().getAssets(), "terminal.ttf");

            tvQuestion = (TextView)itemView.findViewById(R.id.tvQuestion);
            tvQuestion.setTypeface(font);
            tvSign = (TextView)itemView.findViewById(R.id.tvSign);
            tvSign.setTypeface(font);
        }
    }

    @Override
    public DialogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dialog_item,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        /*if (holder.etAnswer != null && holder.etAnswer.getText().length() == 0) {
            holder.etAnswer.requestFocus();
        }*/
    }

    @Override
    public void onBindViewHolder(DialogAdapter.ViewHolder holder, int position) {
        holder.tvQuestion.setText(dialogList.get(position).getQuestion());

        if(dialogList.get(position).isYesNoQuestion())
        {
            holder.tvSign.setText(activity.getResources().getString(R.string.announce_yes_no));
        }
    }

    @Override
    public int getItemCount() {
        return dialogList.size();
    }


}
package com.dnkilic.karabasan;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.ViewHolder>{

    private ArrayList<Dialog> dialogList;
    private Activity activity;
    private DialogCallback dialogCallback;

    public DialogAdapter(ArrayList<Dialog> errorDialogList, MainActivity activity) {
        this.dialogList = errorDialogList;
        this.activity = activity;
        this.dialogCallback = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuestion;
        EditText etAnswer;
        TextView tvSign;

        public ViewHolder(View itemView) {
            super(itemView);

            Typeface font = Typeface.createFromAsset(activity.getApplicationContext().getAssets(), "terminal.ttf");

            tvQuestion = (TextView)itemView.findViewById(R.id.tvQuestion);
            tvQuestion.setTypeface(font);
            etAnswer = (EditText) itemView.findViewById(R.id.etAnswer);
            etAnswer.setTypeface(font);
            tvSign = (TextView)itemView.findViewById(R.id.tvSign);
            tvSign.setTypeface(font);

            etAnswer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_SEND) {
                        if(etAnswer.getText().length()>0)
                        {
                            dialogCallback.onTextSend(etAnswer.getText().toString());
                            handled = true;
                            etAnswer.setEnabled(false);
                        }
                    }
                    return handled;
                }
            });

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
        if (holder.etAnswer != null && holder.etAnswer.getText().length() == 0) {
            holder.etAnswer.requestFocus();
        }
    }

    @Override
    public void onBindViewHolder(DialogAdapter.ViewHolder holder, int position) {
        holder.tvQuestion.setText(dialogList.get(position).getQuestion());
        holder.etAnswer.setInputType(dialogList.get(position).getInputType());

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
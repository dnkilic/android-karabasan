package com.dnkilic.karabasan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;

import java.util.ArrayList;
import java.util.Random;

import static com.dnkilic.karabasan.Dialog.ACTION_EYES;
import static com.dnkilic.karabasan.Dialog.ACTION_FIFTY;
import static com.dnkilic.karabasan.Dialog.ACTION_GET_AGE;
import static com.dnkilic.karabasan.Dialog.ACTION_GET_HEIGHT;
import static com.dnkilic.karabasan.Dialog.ACTION_GET_NAME;
import static com.dnkilic.karabasan.Dialog.ACTION_GET_WEIGHT;
import static com.dnkilic.karabasan.Dialog.ACTION_HOW;
import static com.dnkilic.karabasan.Dialog.ACTION_MILK;
import static com.dnkilic.karabasan.Dialog.ACTION_STUDENT;
import static com.dnkilic.karabasan.Dialog.ACTION_VOTE;

public class MainActivity extends AppCompatActivity implements DialogCallback {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Dialog> mDataset;
    private Candidate mCandidate;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvDialog);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mDataset = new ArrayList<>();
        mAdapter = new DialogAdapter(mDataset, this);
        mRecyclerView.setAdapter(mAdapter);

        mCandidate = new Candidate();

        insertItemToDialog(new Dialog(getResources().getString(R.string.announce_intro), InputType.TYPE_CLASS_TEXT));
    }

    private void insertItemToDialog(Dialog dialog) {
        mDataset.add(dialog);
        mAdapter.notifyItemInserted(mDataset.size() - 1);
        mRecyclerView.scrollToPosition(mDataset.size() - 1);
    }

    @Override
    public void onTextSend(String text) {
        if(index == ACTION_GET_NAME) {
            mCandidate.setName(text);

            String announce = "";

            if(isSwearing(text))
            {
                announce = appendToAnnounce(announce, getRandomStringFromResources(R.array.swearing_response));
            }

            if(text.length() < 3) {
                announce = appendToAnnounce(announce, getResources().getString(R.string.announce_name_response_low, text.length(), text));
            } else if(text.length() > 7) {
                announce = appendToAnnounce(announce, getResources().getString(R.string.announce_name_response_high, getRandomStringFromResources(R.array.laugh), text));
            } else {
                announce = appendToAnnounce(announce, getResources().getString(R.string.announce_name_response, text));
            }

            insertItemToDialog(new Dialog(announce, InputType.TYPE_CLASS_NUMBER));
            index = ACTION_GET_AGE;
        }
        else if(index == ACTION_GET_AGE) {
            Integer age;

            try {
                age = Integer.parseInt(text);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                age = 100;
            }

            mCandidate.setAge(age);
            String announce;
            Dialog dialog;

            if(age < 5) {
                announce = getResources().getString(R.string.announce_age_level_one);
                index = ACTION_GET_HEIGHT;
                dialog = new Dialog(announce, InputType.TYPE_CLASS_NUMBER);
            } else if(age < 10){
                announce = getResources().getString(R.string.announce_age_level_two);
                index = ACTION_MILK;
                dialog = new Dialog(announce, InputType.TYPE_CLASS_TEXT, true);
            } else if(age < 18){
                announce = getResources().getString(R.string.announce_age_level_three);
                index = ACTION_GET_HEIGHT;
                dialog = new Dialog(announce, InputType.TYPE_CLASS_NUMBER);
            } else if(age < 25){
                announce = getResources().getString(R.string.announce_age_level_four);
                index = ACTION_VOTE;
                dialog = new Dialog(announce, InputType.TYPE_CLASS_TEXT, true);
            } else if(age < 40){
                announce = getResources().getString(R.string.announce_age_level_five);
                index = ACTION_GET_HEIGHT;
                dialog = new Dialog(announce, InputType.TYPE_CLASS_NUMBER);
            } else if(age < 60){
                announce = getResources().getString(R.string.announce_age_level_six);
                index = ACTION_GET_HEIGHT;
                dialog = new Dialog(announce, InputType.TYPE_CLASS_NUMBER);
            } else if(age < 99){
                announce = getResources().getString(R.string.announce_age_level_seven);
                index = ACTION_GET_HEIGHT;
                dialog = new Dialog(announce, InputType.TYPE_CLASS_NUMBER);
            } else {
                announce = getResources().getString(R.string.announce_age_level_eight);
                dialog = new Dialog(announce, InputType.TYPE_CLASS_NUMBER);
            }

            insertItemToDialog(dialog);
        } else if(index == ACTION_GET_HEIGHT) {
            Integer height;
            String announce;

            try {
                height = Integer.parseInt(text);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                height = 300;
            }

            mCandidate.setHeight(height);

            if(height < 100) {
                index = ACTION_GET_WEIGHT;
                announce = getResources().getString(R.string.announce_height_level_one);
            } else if(height < 150){
                index = ACTION_GET_WEIGHT;
                announce = getResources().getString(R.string.announce_height_level_two);
            } else if(height < 170){
                index = ACTION_GET_WEIGHT;
                announce = getResources().getString(R.string.announce_height_level_three);
            } else if(height < 190){
                index = ACTION_GET_WEIGHT;
                announce = getResources().getString(R.string.announce_height_level_four);
            } else if(height < 210){
                index = ACTION_GET_WEIGHT;
                announce = getResources().getString(R.string.announce_height_level_five);
            }  else {
                index = ACTION_GET_HEIGHT;
                announce = getResources().getString(R.string.announce_height_level_six);
            }

            insertItemToDialog(new Dialog(announce, InputType.TYPE_CLASS_NUMBER));
        } else if(index == ACTION_MILK) {
            String announce;
            if(text.equals(getResources().getString(R.string.announce_yes))){
                announce = getResources().getString(R.string.announce_milk_positive);
            } else {
                announce = getResources().getString(R.string.announce_milk_negative);
            }
            index = ACTION_GET_HEIGHT;
            insertItemToDialog(new Dialog(announce, InputType.TYPE_CLASS_NUMBER));
        } else if(index == ACTION_VOTE) {
            String announce;
            if(text.equals(getResources().getString(R.string.announce_yes))){
                announce = getResources().getString(R.string.announce_vote_positive);
            } else {
                announce = getResources().getString(R.string.announce_vote_negative);
            }
            index = ACTION_GET_HEIGHT;
            insertItemToDialog(new Dialog(announce, InputType.TYPE_CLASS_NUMBER));
        } else if(index == ACTION_GET_WEIGHT) {
            Integer weight;
            String announce;

            try {
                weight = Integer.parseInt(text);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                weight = 100;
            }

            mCandidate.setHeight(weight);

            if(weight < 40) {
                announce = getResources().getString(R.string.announce_weight_level_one);
            } else if(weight < 60){
                announce = getResources().getString(R.string.announce_weight_level_two);
            } else if(weight < 80){
                announce = getResources().getString(R.string.announce_weight_level_three);
            } else if(weight < 90){
                announce = getResources().getString(R.string.announce_weight_level_four);
            } else if(weight < 150) {
                announce = getResources().getString(R.string.announce_weight_level_five);
            } else {
                announce = getResources().getString(R.string.announce_weight_level_six);
            }

            Random random = new Random();

            if(random.nextBoolean())
            {
                announce = announce + "\n" + getRandomStringFromResources(R.array.laugh);
            }

            if(random.nextBoolean())
            {
                announce = announce + getResources().getString(R.string.announce_single_joke);
            }

            index = ACTION_FIFTY;
            announce = appendToAnnounce(announce, getResources().getString(R.string.announce_fifty_million_question, mCandidate.getName()));

            insertItemToDialog(new Dialog(announce, InputType.TYPE_CLASS_TEXT, true));
        } else if(index == ACTION_FIFTY) {
            String announce;
            if(text.equals(getResources().getString(R.string.announce_yes))){
                announce = getResources().getString(R.string.announce_fifty_positive);
            } else {
                announce = getResources().getString(R.string.announce_fifty_negative, getRandomStringFromResources(R.array.laugh));
            }

            announce = appendToAnnounce(announce, getString(R.string.announce_eyes_question));
            index = ACTION_EYES;
            insertItemToDialog(new Dialog(announce, InputType.TYPE_CLASS_TEXT, true));
        } else if(index == ACTION_EYES) {
            String announce;
            if(text.equals(getResources().getString(R.string.announce_yes))){
                announce = getResources().getString(R.string.announce_eyes_positive);
            } else {
                announce = getResources().getString(R.string.announce_eyes_negative);
            }

            announce = appendToAnnounce(announce, getString(R.string.announce_how_question));
            index = ACTION_HOW;
            insertItemToDialog(new Dialog(announce, InputType.TYPE_CLASS_TEXT, true));
        } else if(index == ACTION_HOW) {
            String announce;
            if(text.equals(getResources().getString(R.string.announce_yes))){
                announce = getResources().getString(R.string.announce_how_positive);
            } else {
                announce = getResources().getString(R.string.announce_how_negative_two);
            }

            announce = appendToAnnounce(announce, getString(R.string.announce_student_question));
            index = ACTION_STUDENT;
            insertItemToDialog(new Dialog(announce, InputType.TYPE_CLASS_TEXT, true));
        }else if(index == ACTION_STUDENT) {
            String announce;
            if(text.equals(getResources().getString(R.string.announce_yes))){
                announce = getResources().getString(R.string.announce_student_positive);
            } else {
                announce = getResources().getString(R.string.announce_student_negative);
            }

            announce = appendToAnnounce(announce, getString(R.string.announce_student_question));
            index = ACTION_STUDENT;
            insertItemToDialog(new Dialog(announce, InputType.TYPE_CLASS_TEXT, true));
        }
    }



  /*  private void makeJoke(ArrayList<Integer> jokeList) {
        Random random = new Random();
        switch (random.nextInt(jokeList.size() - 1))
        {
            case 0:
                break;
            case 1:
                break;
            case 2

        }
    }
*/
    private String appendToAnnounce(String announce, String append)
    {
        return announce + "\n" + append;
    }

    public String getRandomStringFromResources(int resourceId) {
        Random randomGenerator = new Random();
        String[] myString;
        myString = getResources().getStringArray(resourceId);
        return myString[randomGenerator.nextInt(myString.length)];
    }

    public boolean isSwearing(String text) {
        String[] myString = getResources().getStringArray(R.array.swearing);

        for(String item : myString)
        {
            if(text.equals(item))
            {
                return true;
            }
        }

        return false;
    }

    private void consumeNextDialog()
    {

    }
}

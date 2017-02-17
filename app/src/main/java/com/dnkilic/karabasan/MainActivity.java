package com.dnkilic.karabasan;

import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

import static com.dnkilic.karabasan.Dialog.ACTION_EYES;
import static com.dnkilic.karabasan.Dialog.ACTION_FIFTY;
import static com.dnkilic.karabasan.Dialog.ACTION_GET_AGE;
import static com.dnkilic.karabasan.Dialog.ACTION_GET_HEIGHT;
import static com.dnkilic.karabasan.Dialog.ACTION_GET_NAME;
import static com.dnkilic.karabasan.Dialog.ACTION_GET_WEIGHT;
import static com.dnkilic.karabasan.Dialog.ACTION_HOW;
import static com.dnkilic.karabasan.Dialog.ACTION_MILK;
import static com.dnkilic.karabasan.Dialog.ACTION_NAMING;
import static com.dnkilic.karabasan.Dialog.ACTION_SELECT_NUMBER;
import static com.dnkilic.karabasan.Dialog.ACTION_VOTE;
import static com.dnkilic.karabasan.Dialog.ACTION_YOUR_COUNTRY;
import static com.dnkilic.karabasan.Dialog.ACTION_YOUR_NAME;

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
    }

    public void insert1(View view) {
        mDataset.add(new Dialog(getResources().getString(R.string.announce_intro), InputType.TYPE_CLASS_TEXT));
        mAdapter.notifyItemInserted(mDataset.size() - 1);
    }

    @Override
    public void onTextSend(String text) {
        if(index == ACTION_GET_NAME) {
            mCandidate.setName(text);
            String announce;

            if(text.length() < 3) {
                announce = getResources().getString(R.string.announce_name_response_low, text.length(), text);
            } else if(text.length() > 7) {
                announce = getResources().getString(R.string.announce_name_response_high, getRandomStringFromResources(R.array.laugh),text);
            } else {
                announce = getResources().getString(R.string.announce_name_response, text);
            }

            mDataset.add(new Dialog(announce, InputType.TYPE_CLASS_NUMBER));
            mAdapter.notifyItemInserted(mDataset.size() - 1);

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

            mDataset.add(dialog);
            mAdapter.notifyItemInserted(mDataset.size() - 1);
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

            mDataset.add(new Dialog(announce, InputType.TYPE_CLASS_NUMBER));
            mAdapter.notifyItemInserted(mDataset.size() - 1);
        } else if(index == ACTION_MILK) {
            String announce;
            if(text.equals(getResources().getString(R.string.announce_yes))){
                announce = getResources().getString(R.string.announce_milk_positive);
            } else {
                announce = getResources().getString(R.string.announce_milk_negative);
            }
            index = ACTION_GET_HEIGHT;
            mDataset.add(new Dialog(announce, InputType.TYPE_CLASS_NUMBER));
            mAdapter.notifyItemInserted(mDataset.size() - 1);
        } else if(index == ACTION_VOTE) {
            String announce;
            if(text.equals(getResources().getString(R.string.announce_yes))){
                announce = getResources().getString(R.string.announce_vote_positive);
            } else {
                announce = getResources().getString(R.string.announce_vote_negative);
            }
            index = ACTION_GET_HEIGHT;
            mDataset.add(new Dialog(announce, InputType.TYPE_CLASS_NUMBER));
            mAdapter.notifyItemInserted(mDataset.size() - 1);
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

            mDataset.add(new Dialog(announce, InputType.TYPE_CLASS_TEXT));
            mAdapter.notifyItemInserted(mDataset.size() - 1);

           /* ArrayList<Integer> jokeList = new ArrayList<>();
            jokeList.add(ACTION_EYES);
            jokeList.add(ACTION_FIFTY);
            jokeList.add(ACTION_SELECT_NUMBER);
            jokeList.add(ACTION_YOUR_NAME);
            jokeList.add(ACTION_YOUR_COUNTRY);

            makeJoke(jokeList);



            //TODO naming e/h -> How are you -> Student



            switch (random.nextInt(5))
            {
                //ok + adın nereden geliyor/50million/number select
                case 0:
                    announce = announce + getResources().getString(R.string.announce_eyes_question);
                    mDataset.add(new Dialog(announce, InputType.TYPE_CLASS_TEXT, true));
                    index = ACTION_EYES;
                    break;
                // naming/your name
                case 1:
                    announce = announce + getResources().getString(R.string.announce_fifty_million_question);
                    mDataset.add(new Dialog(announce, InputType.TYPE_CLASS_TEXT, true));
                    index = ACTION_FIFTY;
                    break;
                case 2:
                    announce = announce + getResources().getString(R.string.announce_naming_question);
                    mDataset.add(new Dialog(announce, InputType.TYPE_CLASS_TEXT, true));
                    index = ACTION_NAMING;
                    break;
                case 3:
                    // naming
                    announce = announce + getResources().getString(R.string.announce_select_number_question);
                    mDataset.add(new Dialog(announce, InputType.TYPE_CLASS_TEXT, true));
                    index = ACTION_SELECT_NUMBER;
                    break;
                case 4:
                    announce = announce + getResources().getString(R.string.announce_your_name_question);
                    mDataset.add(new Dialog(announce, InputType.TYPE_CLASS_TEXT));
                    index = ACTION_YOUR_NAME;
                    break;
                default:
                    announce = announce + getResources().getString(R.string.announce_naming_question);
                    mDataset.add(new Dialog(announce, InputType.TYPE_CLASS_TEXT, true));
                    index = ACTION_NAMING;
                    break;
            }
            mAdapter.notifyItemInserted(mDataset.size() - 1);
        } else if(index == ACTION_EYES) {

        } else if(index == ACTION_FIFTY) {

        } else if(index == ACTION_NAMING) {
            //TODO How are you

        } else if(index == ACTION_SELECT_NUMBER) {

        } else if(index == ACTION_YOUR_NAME) {
            //TODO naming, select number
        } else if(index == ACTION_HOW) {*/
            //TODO student
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
    public String getRandomStringFromResources(int resourceId) {
        Random randomGenerator = new Random();
        String[] myString;
        myString = getResources().getStringArray(resourceId);
        return myString[randomGenerator.nextInt(myString.length)];
    }

    private void consumeNextDialog()
    {

    }
}

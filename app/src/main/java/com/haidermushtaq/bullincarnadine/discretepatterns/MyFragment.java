package com.haidermushtaq.bullincarnadine.discretepatterns;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Haider on 5/30/2015.
 */
public class MyFragment extends Fragment {

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    Button ClickMe;
    TextView tv,pattern;
    LinearLayout myLayout;
    ArrayList<Integer> NumLIst = new ArrayList<>();;
    int increment = 0;


    public MyFragment() {
    }

    public void changeText(String x){
        tv.setText(x);
    }

    public  void appendText(String x){
        tv.append(x + "\n");

    }

    public void setNumLIst(int[] numlist){
        increment = 0;
        NumLIst = new ArrayList<>();
        pattern.setText("");
        for(int x : numlist){
            NumLIst.add(x);
            pattern.append(x + " , ");
        }
        try {
            int x = DiscretePatterns.getNextInt(numlist,getActivity(),0);
            if(x != 0) ((MainActivity) getActivity()).speak("Pattern Recognised");
            NumLIst.add(x);
            pattern.append(""+x);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void changeBackgroundColor(){
        int r = ((int)(Math.random()*256));
        int g = ((int)(Math.random()*256));
        int b = ((int)(Math.random()*256));
        myLayout.setBackgroundColor(Color.rgb(r,g,b));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_fragment, container, false);
        ClickMe = (Button) rootView.findViewById(R.id.button);
        tv = (TextView) rootView.findViewById(R.id.textView2);
        pattern = (TextView) rootView.findViewById(R.id.textView3);
        myLayout = (LinearLayout) rootView.findViewById(R.id.myLayout);


        ClickMe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                try {
                    if (!NumLIst.isEmpty()) {
                        int[] lastFive = new int[5];
                        for (int x = 4; x >= 0; x--) {
                            lastFive[x] = NumLIst.get(NumLIst.size() - 1 - (4 - x));
                        }
                        int nextInt = DiscretePatterns.getNextInt(lastFive, getActivity(), ++increment);
                        pattern.append(" , " + nextInt);
                        NumLIst.add(nextInt);
                        if(increment < 3)
                        ((MainActivity) getActivity()).speak("Next Term: " + nextInt);
                        else
                            ((MainActivity) getActivity()).speak("" + nextInt);

                    }else {
                        tv.setText(Html.fromHtml("<html><h3>Please <sub>Enter</sub> <sup>Values</sup> First</h3></html>"));
                        ((MainActivity) getActivity()).speak("Are you a faggot? Can't you read! It says Enter Values First. I wish you die, you little piece of Shit!");
                    }
                }catch(Exception e){
                    tv.setText(Html.fromHtml("<html><h1>THERE <sub>IS</sub> <sup>SOME</sup> ERROR</h1></html>"));
                    pattern.setText(e.toString());
                    ((MainActivity) getActivity()).speak("Error has occurred, please don't tell Madam Seemab");
                }
            }
        });

        return rootView;

    }


}

package com.haidermushtaq.bullincarnadine.discretepatterns;

/**
 * Created by Haider on 5/20/2015.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceholderFragment() {

    }
    TextView introText;
    int[] num = new int[5];
    EditText[] tv =  new EditText[5];
    Button compButton, resetButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        tv[0] = (EditText) rootView.findViewById(R.id.editText);
        tv[1] = (EditText) rootView.findViewById(R.id.editText2);
        tv[2] = (EditText) rootView.findViewById(R.id.editText3);
        tv[3] = (EditText) rootView.findViewById(R.id.editText4);
        tv[4] = (EditText) rootView.findViewById(R.id.editText5);
        compButton = (Button) rootView.findViewById(R.id.button2);
        resetButton = (Button) rootView.findViewById(R.id.button3);
        introText = (TextView) rootView.findViewById(R.id.textView);

      Runnable r = new Runnable() {
          @Override
          public void run() {
              try {
                  Thread.sleep(2000);
                  ((MainActivity) getActivity()).speak("Hi There! " + introText.getText().toString());
              } catch (InterruptedException e) {
                  e.printStackTrace();
              } catch (Exception e ){
                  e.printStackTrace();
              }

          }
      }; Thread t = new Thread(r);
        t.start();



        compButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean caution = false;
                ArrayList<String> Answers = new ArrayList<String>();
                for(int x=0 ; x <5 ; x++)
                {
                    if(!tv[x].getText().toString().isEmpty())
                    num[x] = Integer.parseInt(tv[x].getText().toString());
                    else caution = true;
                }
                try {
                    if(!caution) {
                        Answers = DiscretePatterns.Start(num, getActivity());

                        ((MyFragment) ((MainActivity) getActivity()).getFrag2()).setNumLIst(num);
                        ((MyFragment) ((MainActivity) getActivity()).getFrag2()).changeText("");
                        for (String ans : Answers)
                            ((MyFragment) ((MainActivity) getActivity()).getFrag2()).appendText(ans);
                        ((MyFragment) ((MainActivity) getActivity()).getFrag2()).changeBackgroundColor();
                        ((MainActivity) getActivity()).setCurrentItem(1, true);
                    }else {
                        Toast.makeText(getActivity(), "Please Enter All The Values First",
                                Toast.LENGTH_SHORT).show();
                        ((MainActivity) getActivity()).speak("Please Enter All The Values First");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int x = 0 ; x < 5; x++)
                    tv[x].setText("");
                tv[0].requestFocus();
                ((MainActivity) getActivity()).speak("Reset");
                //((MainActivity) getActivity()).shutTTSDown();
            }
        });
        return rootView;
    }

    public int[] getNum(){
        return num;
    }

}

package com.haidermushtaq.bullincarnadine.discretepatterns;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DiscretePatterns {

    int[] numbers = new int[5];

    public static ArrayList<String> Start(int[] num, final Context context) throws MalformedURLException, FileNotFoundException, IOException {

        ArrayList<String> Answers = new ArrayList<>();
        final int[] input = num;
        //File formulasFile = formulaFile;
        //File userInput = userFile;
        //Scanner fileScanner = new Scanner(formulasFile);
        //Scanner fileScanner2 = new Scanner(userInput);

        /***************************************EXTERNAL FILE START******************/

        String TASK_DIRECTORY = Environment.getExternalStoragePublicDirectory("DiscretePatterns") + "/UserInput";
        File dir = new File(TASK_DIRECTORY);
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dir, "UserInput.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
                file.setReadable(true, false);

                OutputStream outStream = new FileOutputStream(file, true);
                outStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /***************************EXTERNAL FILE END*********************************/

        InputStream raw1 = new FileInputStream(file);
        BufferedReader formulaFile = new BufferedReader(new InputStreamReader(raw1));

        InputStream raw2 = context.getResources().openRawResource(R.raw.userinput);
        BufferedReader userFile = new BufferedReader(new InputStreamReader(raw2));

        List<String> formulas = new ArrayList<>();
        int[] values = new int[5];
        String currentFormula = null;

        if(input[0] == input[1] && input[1] == input[2] && input[2] == input[3] && input[3] == input[4]){
            Answers.add("All Numbers are Equal");
            Answers.add("Next Number is: " + input[3]);
        }
        else if (compareSets(input, fibonacciLoop(5)))//if fibonacci series
        {
            Answers.add("Fibonacci Series.\n Next Number is: " + 8);
        } else if (compareSets(input, computePrime())) {
            Answers.add("Prime Numbers.\n Next number is: " + 13);
        } else {
            int[] differences = new int[4];
            for (int i = 0; i < 4; i++) {
                differences[i] = input[i + 1] - input[i];
            }
            if (equalDifference(differences) ) {
                int x = input[4] + differences[0];
                Answers.add("Equal difference.\nNext number=" + x);
                String FormulaComputation = ("Formula: " + differences[0] + " * n");
                int omega = input[0] - differences[0];
                //Answers.add("OMEGA : " + omega );
                if(omega > 0) {
                    Answers.add(FormulaComputation + " +" + omega);
                }else if(omega < 0) {
                    Answers.add(FormulaComputation + " " + omega);
                }
                else  Answers.add(FormulaComputation);


            }
            else if (uniformlyChangingDifference(differences) ) {
                int x = input[4] + differences[3] + differences[3] - differences[2];
                Answers.add("Uniformly increasing differnce.\n Next number=" + x);
                double a,b,c;

                a = (0.3*input[3]) - (0.4*input[2]) - (0.1*input[1]) + (0.2*input[0]);
                b = (-0.75)*input[3] + (0.75)*input[2] + (1.75)*input[1] - (1.75)*input[0];
                c = (0.6)*input[3] - (0.8)*input[2] - (1.2) * input[1] + (2.4)*input[0];

                String ans;
                ans = "Formula: ";

                if(Math.round(a) != 0){
                    ans = ans + Math.round(a) +"x^2";
                }
                if(Math.round(b) != 0){
                    if(b > 0)
                    ans = ans + " +" + Math.round(b) + "x";
                    else ans = ans + " " + Math.round(b) + "x";
                }if(Math.round(c) != 0){
                    if(c > 0)
                    ans = ans + " +" + Math.round(c);
                    else ans = ans + " " + Math.round(c);
                }
                Answers.add(ans);

            } else if (increasingNumbers(input)) {//sum of all previous numbers
                int x = input[0] + input[1] + input[2] + input[3] + input[4];
                Answers.add("Increasing numbers.");
                Answers.add("Next number: " + x);
            }else if(increasingPowers(input)) { //TODO ADD THIS METHOD ABOVE
                int x = 1;
                int y;
                do{
                    y = (int) Math.pow((input[4]/input[3]),x);
                    x++;
                }while(y != input[4] && x < 30);
                Answers.add("Increasing Powers");
                Answers.add("FORMULA: " + (input[4]/input[3]) + " ^ n");
                Answers.add("Next Number: " + (int)Math.pow((input[4] / input[3]), x));

            }

            else {
                String data = formulaFile.readLine();
                while (data != null) {
                    currentFormula = data;
                    formulas.add(currentFormula);
                    data = formulaFile.readLine();
                    //StringTokenizer myTokenizer= new StringTokenizer(currentFormula);
                }
                String requiredFormula = null;
                for (String s : formulas) {
                    values = computeValues(s,0);
                    if (compareSets(input, values)) {
                        Answers.add("Found From Database...");
                        requiredFormula = s;
                        Answers.add("Required Formula: \n" + requiredFormula);
                        Answers.add("Next Number: " + computeNext(requiredFormula, 6));
                        //System.out.println("Formula used: "+requiredFormula);
                        break;
                    } else {
                        //System.out.println("Sets are NOT same..");
                        ;
                    }
                }
                /**************************Machine Learning Start**************************************/
                if(requiredFormula == null){
                    Answers.add("Formula Not Found From Database");
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Machine Learning");
                    builder.setMessage("" +
                                    "Please enter the correct Formula for this Pattern:\n\n" +
                                    "For Example if you want to enter 2n^2 + 3n +1\n" +
                                    "Please enter it like: \n" +
                                    "2 * n * n + 3 * n + 1\n"

                    );
                    final EditText et = new EditText(context);
                    et.setHint("e.g : 2 * n * n + 3 * n + 1");
                    builder.setView(et);
                    builder.setPositiveButton("Validate", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                int[] values = computeValues(et.getText().toString(), 0);
                                Toast.makeText(context, "Pattern: " + values[0] + "," + values[1] + "," + values[2] + "," + values[3] + "," + values[4],
                                        Toast.LENGTH_LONG).show();
                                if (compareSets(input, values)) {
                                    Toast.makeText(context, "Validated",
                                            Toast.LENGTH_SHORT).show();
                                    //((MainActivity) context).speak("Formula has been Validated.");

                                    // TODO if(formula.added)
                                    /*********ADDING FORMULA TO DATABASE START**************/
                                    String TASK_DIRECTORY = Environment.getExternalStoragePublicDirectory("DiscretePatterns") + "/UserInput";
                                    File dir = new File(TASK_DIRECTORY);
                                    if (!dir.exists()) dir.mkdirs();
                                    File file = new File(dir, "UserInput.txt");
                                    try {
                                        if (!file.exists()) {
                                            file.createNewFile();
                                            file.setReadable(true, false);
                                        }
                                        OutputStream outStream = new FileOutputStream(file, true);
                                        outStream.write((et.getText() + "\n").getBytes());
                                        outStream.close();

                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    /*********ADDING FORMULA TO DATABASE END****************/
                                    /***********LISTING ALL FORMULA FOR TESTING PURPOSES START**********/
                                    Toast.makeText(context, "Your Formula has been added to our Database",
                                            Toast.LENGTH_SHORT).show();
                                    ((MainActivity) context).speak("Your Formula has been added to our Database");
                                    AlertDialog.Builder FormulaList = new AlertDialog.Builder(context);
                                    FormulaList.setTitle("FORMULA LIST");

                                    InputStream raw1 = new FileInputStream(file);
                                    BufferedReader formulaFile = new BufferedReader(new InputStreamReader(raw1));

                                    String data = formulaFile.readLine();
                                    String completeFileString = "CONTENTS:";
                                    while (data != null) {
                                        completeFileString = completeFileString + "\n" + data;
                                        data = formulaFile.readLine();
                                    }
                                    FormulaList.setMessage(completeFileString);
                                    AlertDialog d = FormulaList.create();
                                    d.show();
                                    /***********LISTING ALL FORMULA FOR TESTING PURPOSES END***********/
                                } else {
                                    Toast.makeText(context, "Your Formula couldn't validate Your Pattern",
                                            Toast.LENGTH_SHORT).show();
                                    ((MainActivity) context).speak("Your Formula couldn't validate Your Pattern");
                                }
                            } catch (Exception e) {
                                Toast.makeText(context, e.toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    ((MainActivity) context).speak("I was unable to recognise your pattern. Please enter the correct Formula for this Pattern. I will validate this formula with your patten and if it gets validated, I will add it to my Database.");

                }
                /************************Machine Learning End************************************/
                 /*else {

                    int[] read = new int[5];
                    boolean found = false;
                    int i = 0;
                        while (fileScanner2.hasNextLine()) {
                            if (!fileScanner2.hasNextInt()) {
                                break;
                            }
                            read[i] = fileScanner2.nextInt();
                            i++;
                            if (i == 5) {
                                if (compareSets(input, read)) {
                                    System.out.println("Found the UNVERIFIED series from user input: " + fileScanner2.nextInt());
                                    found=true;
                                    break;
                                }
                                i = 0;
                            }
                        }
                     if(!found) {
                        System.out.println("The system has failed to find the next number. We would like to take user's input for the solution of this sequence.");
                        Scanner in = new Scanner(System.in);
                        System.out.print("Enter the next number in sequence: ");
                        int x = in.nextInt();
                        BufferedWriter bf = new BufferedWriter(new FileWriter(userInput));
                        for (i = 0; i < 5; i++) {
                            bf.write(input[i] + " ");
                        }
                        bf.write(x + "\n");
                        bf.close();
                    }
                }*/
            }
        }

        return Answers;
    }
    /********************************************************************/
    public static int getNextInt(int[] num, Context context, int increment) throws MalformedURLException, FileNotFoundException, IOException {


        int[] input = num;

        String TASK_DIRECTORY = Environment.getExternalStoragePublicDirectory("DiscretePatterns") + "/UserInput";
        File dir = new File(TASK_DIRECTORY);
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dir, "UserInput.txt");

        InputStream raw1 = new FileInputStream(file);
        BufferedReader formulaFile = new BufferedReader(new InputStreamReader(raw1));



        List<String> formulas = new ArrayList<>();
        int[] values = new int[5];
        String currentFormula = null;

        if(input[0] == input[1] && input[1] == input[2] && input[2] == input[3] && input[3] == input[4]){
            return input[4];
        }
        else if (compareSets(input, fibonacciLoop(5)))//if fibonacci series
        {
            return input[3] + input[4];
        } else if (compareSets(input, computePrime())) {
            return 13;
        } else {
            int[] differences = new int[4];
            for (int i = 0; i < 4; i++) {
                differences[i] = input[i + 1] - input[i];
            }
            if (equalDifference(differences)) {
                int x = input[4] + differences[0];
                return x;

            }
            if (uniformlyChangingDifference(differences)) {
                int x = input[4] + differences[3] + differences[3] - differences[2];
                return x;
            }/*else if(increasingPowers(input)) { //TODO ADD THIS METHOD ABOVE
                int x = 1;
                int y;
                do{
                    y = (int) Math.pow((input[4]/input[3]),x);
                    x++;
                }while(y != input[4]);
                return (int) Math.pow((input[4]/input[3]),x);

            }*/else if (increasingNumbers(input)) {//sum of all previous numbers
                int x = input[0] + input[1] + input[2] + input[3] + input[4];
                return x;
            } else {
                String data = formulaFile.readLine();
                while (data != null) {
                    currentFormula = data;
                    formulas.add(currentFormula);
                    data = formulaFile.readLine();
                    //StringTokenizer myTokenizer= new StringTokenizer(currentFormula);
                }
                String requiredFormula = null;
                for (String s : formulas) {
                    values = computeValues(s,increment);
                    if (compareSets(input, values)) {

                        requiredFormula = s;
                        //System.out.println("Formula used: "+requiredFormula);
                        break;
                    } else {
                        //System.out.println("Sets are NOT same..");
                        ;
                    }
                }
                if (requiredFormula != null) {
                    return computeNext(requiredFormula,6+increment);


                }
            }
        }

        return 0;
    }
    /******************************************************************/

    public static boolean increasingNumbers(int[] n) {
        if (n[2] == (n[0] + n[1])) {
            if (n[3] == (n[0] + n[1] + n[2])) {
                if (n[4] == (n[0] + n[1] + n[2] + n[3])) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean equalDifference(int[] n) {
        if ((n[0] == n[1]) && (n[1] == n[2]) && (n[2] == n[3])) {
            return true;
        }
        return false;
    }

    public static boolean uniformlyChangingDifference(int[] n) {
        for (int i = 0; i <= 100; i++) {
            if ((n[1] - n[0] == n[2] - n[1] + i) && (n[3] - n[2] == n[2] - n[1] + i)) {
                return true;
            }
        }
        return false;
    }

    // Java program for Fibonacci number using Loop.
    public static int[] fibonacciLoop(int number) {
        int[] n = new int[6];
        int fibo1 = 1, fibo2 = 1;
        int[] fibonacci = new int[5];
        fibonacci[0] = 1;
        fibonacci[1] = 1;
        for (int i = 2; i < number; i++) {
            fibonacci[i] = fibo1 + fibo2; //Fibonacci number is sum of previous two Fibonacci number
            fibo1 = fibo2;
            fibo2 = fibonacci[i];
        }
        return fibonacci; //Fibonacci number
    }



    public static int[] computePrime() {
        int[] nt = new int[5];
        int n = 5;
        int status = 0;
        int num = 2;
        nt[0] = 2;
        for (int count = 2; count <= n;) {
            for (int j = 2; j <= Math.sqrt(num); j++) {
                if (num % j == 0) {
                    status = 0;
                    break;
            }
            }
            if (status != 0) {
                //System.out.println(num);
                nt[count - 1] = num;
                count++;
            }
            status = 1;
            num++;
        }
        return nt;
    }

    public static int computeNext(String formula, int ValueOfN) {
        int next = 0;
        int value = 0;
        String dummyFormula = formula;
        //for (int i = 1; i < 6; i++) {
        //next = 0;
        dummyFormula = dummyFormula.replaceAll("n", "" + ValueOfN + '\0');
        StringTokenizer myTokenizer = new StringTokenizer(dummyFormula);
        while (myTokenizer.hasMoreTokens()) {
            String token = myTokenizer.nextToken();

            if (token.equals("+")) {
                next = next + Integer.parseInt(myTokenizer.nextToken());
            } else if (token.equals("*")) {
                next = next * Integer.parseInt(myTokenizer.nextToken().trim());
            } else if (token.equals("/")) {
                next = next / Integer.parseInt(myTokenizer.nextToken());
            } else if (token.equals("-")) {
                next = next - Integer.parseInt(myTokenizer.nextToken());
            } else if (token.equals("=")) {
                token = myTokenizer.nextToken();
                token = token.trim();
                next = Integer.parseInt(token);
            } else {
                next = Integer.parseInt(token.trim());
            }

            value = next;

        }

//        for (int i = 0; i < 5; i++) {
        //          System.out.print(values[i] + ",");
        //    }
        //System.out.println();
        return value;
    }

    public static int[] computeValues(String formula, int increment) {
        int next = 0;
        int[] values = new int[5];
        String dummyFormula = formula;
        for (int i = 1; i < 6; i++) {
            //next = 0;
            dummyFormula = dummyFormula.replaceAll("n", "" + (i+increment) + '\0');
            StringTokenizer myTokenizer = new StringTokenizer(dummyFormula);
            while (myTokenizer.hasMoreTokens()) {
                String token = myTokenizer.nextToken();

                if (token.equals("+")) {
                    next = next + Integer.parseInt(myTokenizer.nextToken());
                } else if (token.equals("*")) {
                    next = next * Integer.parseInt(myTokenizer.nextToken().trim());
                } else if (token.equals("/")) {
                    next = next / Integer.parseInt(myTokenizer.nextToken());
                } else if (token.equals("-")) {
                    next = next - Integer.parseInt(myTokenizer.nextToken());
                } else if (token.equals("=")) {
                    token = myTokenizer.nextToken();
                    token = token.trim();
                    next = Integer.parseInt(token);
                } else {
                    next = Integer.parseInt(token.trim());
                }
            }
            values[i - 1] = next;
            //System.out.print(dummyFormula + ": ");
            //System.out.println(next);
            dummyFormula = formula;
        }

//        for (int i = 0; i < 5; i++) {
        //          System.out.print(values[i] + ",");
        //    }
        //System.out.println();
        return values;
    }

    public static boolean compareSets(int[] a, int[] b) {
        for (int i = 0; i < 5; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean increasingPowers(int[] input){
        try {
            if(input[4] > input[3] && input[3] > input[2] && input[2] > input[1] && input[1] > input[0])
            if (((input[4] / input[3]) == (input[3] / input[2])) && ((input[3] / input[2]) == (input[2] / input[1]))) {

                return true;

            } else return false;
            else return false;
        }catch(Exception e){
            return  false;
        }

    }
}

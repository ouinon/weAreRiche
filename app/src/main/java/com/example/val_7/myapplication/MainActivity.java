package com.example.val_7.myapplication;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Random;



public class MainActivity extends AppCompatActivity {

    private TextView textResult;
    private TextView textCoins;
    private EditText etBetting;

    private int bet;
    private double x;
    private double coins = 10000.00;
    private Handler handler;

    private Button button;
    private Button start;

    private String s;
    private int etBettingValue;

    private int numGame = 0;
    private double t = 0;
    private double valueOnTap;

    private double i;
    private CountDownTimer cdt;

    private volatile boolean clicked = false;

    private TextView[] tabT = new TextView[20];

    private int nbHisto = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = ((Button) findViewById(R.id.button));

        start = ((Button) findViewById(R.id.button2));
        initTabHisto();

        final EditText etBetting = ((EditText) findViewById(R.id.betting));

        textResult = ((TextView) findViewById(R.id.textResult));
        textResult.setText("Résultat");

        textCoins = ((TextView) findViewById(R.id.textCoins));
        coinAmount();
        button.setText("x");
        button.setEnabled(false);
        handler = new Handler();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sButton = button.getText().toString();
                int indexX = sButton.indexOf("x");
                valueOnTap = Double.valueOf(sButton.substring(0,indexX));
                textResult.setText("Win : " +String.format("%.2f",(valueOnTap * etBettingValue)));
                coins += etBettingValue * valueOnTap;
                coinAmount();
                etBetting.setKeyListener(DigitsKeyListener.getInstance());
                clicked = true;
                button.setEnabled(false);
                start.setEnabled(true);
                majHistory();
                cdt.cancel();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!etBetting.getText().toString().equals("")) {
                    s = etBetting.getText().toString();
                    etBettingValue = Integer.valueOf(s);
                    if (!s.equals("") && etBettingValue <= coins) {
                        numGame++;
                        clicked = false;
                        button.setBackgroundColor(Color.parseColor("#00ff00"));
                        coins -= etBettingValue;
                        coinAmount();
                        etBetting.setKeyListener(null);
                        start.setEnabled(false);
                        button.setEnabled(true);
                        Random rand = new Random();
                        x = rand.nextInt(100);
                        if(x ==  0) t = 0;
                        if(x <= 53) t = myRandom(10,20);
                        if(x >  53) t = myRandom(20,100);
                        textResult.setText("t : " + t);
                        if(t >= 1 || t==0) {
                            i = 1;
                            cdt = new CountDownTimer(60000,200) {
                                public void onTick(long l){
                                    button.setText(String.format(Locale.US,"%.2f",i) + "x");
                                    i += 0.01;
                                    if(i>=t) {
                                        button.setText(String.format(Locale.US,"%.2f",i) + "x");
                                        button.setBackgroundColor(Color.RED);
                                        button.setEnabled(false);
                                        start.setEnabled(true);
                                        majHistory();
                                        cdt.cancel();
                                    }
                                }
                                public void onFinish() {
                                    if(!clicked) button.setBackgroundColor(Color.RED);
                                    button.setText(String.format(Locale.US,"%.2f",i) + "x");
                                }
                            }.start();
                        }
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("Not enough coins available");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("No bet amount");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });
    }



    public double getCoins() { return this.coins; }

    public void coinAmount() { textCoins.setText("c:" + String.format("%.2f",coins));}

    public double myRandom(double min, double max) {
        Random r = new Random();
        return (r.nextInt((int)((max-min)*10+1))+min*10) / 100.0;
    }

    public void majHistory() {
        //String textHisto = String.format("%-3s",String.valueOf(numGame)) + String.format("%-5s","Me") + String.format("%-6s",String.valueOf(etBettingValue)) + String.format("-5s",s) + String.format("%-.2f",(valueOnTap * etBettingValue));
        String textHisto = numGame + "       ||" + "                 Me    ||      " + etBettingValue + " ||               " + String.format("%.2f",valueOnTap) + " || " + String.format("%.2f",(valueOnTap * etBettingValue));
        if(nbHisto > 11) nbHisto = 1;
        if(!clicked) {
            textHisto = numGame + "       ||" + "                 Me    ||      " + etBettingValue + " ||               " + String.format("%.2f",t) + " || " ;
            tabT[nbHisto].setTextColor(0xFFFF0000);
        } else {
            tabT[nbHisto].setTextColor(Color.parseColor("#00ff00"));
        }
        tabT[nbHisto].setText(textHisto);
        nbHisto++;

    }

    public void initTabHisto() {
        tabT[1] = (TextView) findViewById(R.id.history1);
        tabT[2] = (TextView) findViewById(R.id.history2);
        tabT[3] = (TextView) findViewById(R.id.history3);
        tabT[4] = (TextView) findViewById(R.id.history4);
        tabT[5] = (TextView) findViewById(R.id.history5);
        tabT[6] = (TextView) findViewById(R.id.history6);
        tabT[7] = (TextView) findViewById(R.id.history7);
        tabT[8] = (TextView) findViewById(R.id.history8);
        tabT[9] = (TextView) findViewById(R.id.history9);
        tabT[10] = (TextView) findViewById(R.id.history10);
        tabT[11] = (TextView) findViewById(R.id.history11);
        //git
        double popo;
    }

}

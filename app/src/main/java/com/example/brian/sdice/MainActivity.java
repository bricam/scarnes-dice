package com.example.brian.sdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import java.util.Random;
import android.os.Handler;


public class MainActivity extends AppCompatActivity {

    int playerTotal = 0;
    int playerTurn = 0;
    int compTotal = 0;
    int compTurn = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button roll = (Button) findViewById(R.id.rollButton);
        final Button hold = (Button) findViewById(R.id.holdButton);
        final Button reset = (Button) findViewById(R.id.resetButton);

        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollHandler();
            }
        });

        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holdHandler();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                resetHandler();
            }
        });

    }

    public boolean rollHandler(){ //INCOMPLETE
        /*
        Generate Random Number 1-6 and update image.
        If 1, rest turn variables and trigger computer's turn
        */
        Random r = new Random();
        int rolledNum = (1 + r.nextInt(6));
        TextView gameStatsView = (TextView) findViewById(R.id.gameStats);

        //Update image
        updateDice(rolledNum);
        if(rolledNum != 1){ //Didn't roll a 1
            playerTurn += rolledNum;
            gameStatsView.setText("Your score: " + Integer.toString(playerTotal) +
                    "  Computer's Score :" + Integer.toString(compTotal) +
                    "  Your turn score: " + Integer.toString(playerTurn));
        }
        else{ //Rolled a 1
            playerTurn = 0;
            gameStatsView.setText("Your score: " + Integer.toString(playerTotal) +
                    "  Computer's Score: " + Integer.toString(compTotal) +
                    "  Your turn score: " + Integer.toString(playerTurn));

            //Trigger Computer's Turn
            computerTurn();
        }

        return true;
    }

    public boolean holdHandler(){ //INCOMPLETE
        //Update the total score, reset turn variable
        playerTotal += playerTurn;
        playerTurn = 0;
        //Update UI
        TextView gameStatsView = (TextView) findViewById(R.id.gameStats);
        gameStatsView.setText("Your score: " + Integer.toString(playerTotal) +
                "  Computer's Score: " + Integer.toString(compTotal));
        //Determine if game has been won
        if(playerTotal < 100) //Player hasnt won yet
            computerTurn();
        else{ //Player has won
            final Button roll = (Button) findViewById(R.id.rollButton);
            roll.setEnabled(false);
            final Button hold = (Button) findViewById(R.id.holdButton);
            hold.setEnabled(false);
            gameStatsView.setText("Your score: " + Integer.toString(playerTotal) +
                    "  Computer's Score: " + Integer.toString(compTotal)
            + " [You Win!]");


        }

        return true;
    }

    public boolean resetHandler(){ //COMPLETE
        //Reset our variables
        playerTotal = 0;
        playerTurn = 0;
        compTotal = 0;
        compTurn = 0;
        //Update UI Label
        TextView gameStatsView = (TextView) findViewById(R.id.gameStats);
        gameStatsView.setText(getResources().getString(R.string.defaultScore));
        //Re-enable buttons in case user resets while computer rolls
        final Button roll = (Button) findViewById(R.id.rollButton);
        roll.setEnabled(true);
        final Button hold = (Button) findViewById(R.id.holdButton);
        hold.setEnabled(true);
        //End
        return true;
    }

    private boolean computerTurn(){
        //Disable Buttons
        final Button roll = (Button) findViewById(R.id.rollButton);
        roll.setEnabled(false); //Needs to be re-enabled after computer is done!
        final Button hold = (Button) findViewById(R.id.holdButton);
        hold.setEnabled(false); //Needs to be re-enabled after computer is done!

        //Computer's AI
            //Generate a dice roll
            Random r = new Random();
            int rolledNum = (1 + r.nextInt(6));
            TextView gameStatsView = (TextView) findViewById(R.id.gameStats);
            //Update image
            updateDice(rolledNum);
            if (rolledNum != 1) { //Didnt roll a 1
                compTurn += rolledNum;
                gameStatsView.setText("Your score: " + Integer.toString(playerTotal) +
                        "  Computer's Score :" + Integer.toString(compTotal) +
                        "  Computer turn score: " + Integer.toString(compTurn));
                //Call itself again if turn <20, with delay, otherwise hold
                if(compTurn < 20){ //implement delay
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 1s = 1000ms
                            computerTurn();

                        }
                    }, 1000);
                    return true;
                }
                else{
                    //Computer Holds at this point
                    compTotal += compTurn;
                    compTurn = 0;
                    //Update UI
                    gameStatsView.setText("Your score: " + Integer.toString(playerTotal) +
                            "  Computer's Score: " + Integer.toString(compTotal));
                }
            } else { //Rolled a 1
                compTurn = 0;
                gameStatsView.setText("Your score: " + Integer.toString(playerTotal) +
                        "  Computer's Score: " + Integer.toString(compTotal) +
                        "  Computer turn score: " + Integer.toString(compTurn));
            }



        if(compTotal < 100){ //Computer hasnt won yet
        //Re-enable Buttons
        roll.setEnabled(true);
        hold.setEnabled(true);
        }
        else{//Computer has won
            gameStatsView.setText("Your score: " + Integer.toString(playerTotal) +
                    "  Computer's Score: " + Integer.toString(compTotal)
            + "[Computer Wins!]");
        }

        return true;
    }






    private void updateDice(int x){
        ImageView dice = (ImageView) findViewById(R.id.diceFace);
        if(x==1)
            dice.setImageResource(R.drawable.dice1);
        else if(x==2)
            dice.setImageResource(R.drawable.dice2);
        else if(x==3)
            dice.setImageResource(R.drawable.dice3);
        else if(x==4)
            dice.setImageResource(R.drawable.dice4);
        else if(x==5)
            dice.setImageResource(R.drawable.dice5);
        else
            dice.setImageResource(R.drawable.dice6);
    }




}

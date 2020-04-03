package com.gdevs.connectthreegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static java.sql.Types.NULL;

public class MainActivity extends AppCompatActivity {

    //0 = yellow, 1 = red, 2 = empty
    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2}; //to track each spects
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    boolean gameActive = true;

    GridLayout glBoard;
    TextView tvWinner;
    Button btnWinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        glBoard = findViewById(R.id.glBoard);
        tvWinner = findViewById(R.id.tvWinner);
        btnWinner = findViewById(R.id.btnWinner);

        btnWinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });
    }

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        Log.i("TAG", "dropIn: " + counter.getTag().toString());
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameActive) { //to check if a cell is filled with a counter or not to avoid place another counter
            //and if the game is still active

            gameState[tappedCounter] = activePlayer;

            Log.d("GAME STATE", "dropIn: " + gameState[tappedCounter]);

            counter.setTranslationY(-1500);

            if (activePlayer == 0) {

                counter.setImageResource(R.mipmap.ic_yellow_foreground);
                activePlayer = 1;

            } else {

                counter.setImageResource(R.mipmap.ic_red_foreground);
                activePlayer = 0;

            }

            counter.animate().translationYBy(1500).setDuration(300);

            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2) {
                    //Someone has won!

                    gameActive = false;

                    String winner = "";

                    if (activePlayer == 1) {
                        winner = "Yellow";
                    } else {
                        winner = "Red";
                    }

                    tvWinner.setVisibility(View.VISIBLE);
                    btnWinner.setVisibility(View.VISIBLE);
                    tvWinner.setText(winner + " has won!");
                }
            }
        }
    }

    public void newGame() {

        for (int i = 0; i < glBoard.getChildCount(); i++) {

            ImageView counterChild = (ImageView) glBoard.getChildAt(i);
            counterChild.setImageDrawable(null);
        }

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        //Setting defaults
        activePlayer = 0;
        gameActive = true;

        tvWinner.setVisibility(View.INVISIBLE);
        btnWinner.setVisibility(View.INVISIBLE);

    }
}

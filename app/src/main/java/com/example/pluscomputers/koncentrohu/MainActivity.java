package com.example.pluscomputers.koncentrohu;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.pluscomputers.koncentrohu.model.Photo;
import com.example.pluscomputers.koncentrohu.utilities.NetHelper;
import com.example.pluscomputers.koncentrohu.utilities.PhotoJsonUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    static final String FLICKER_REQUEST_URL = "https://api.flickr.com/services/rest/";
    static final String FLICKER_API_KEY = "85ab1b165f424c4aae31ac171a820364";
    private static final String FLICKER_SEARCH_TAG = "puppy";
    private String mTag;

    // List of photos objects that we will take from API
    List<Photo> photos;

    @BindView(R.id.txt_player_one)TextView playerOneTextView;
    @BindView(R.id.txt_player_two) TextView playerTwoTextView;

    @BindView(R.id.img_11) ImageView img_11;
    @BindView(R.id.img_12) ImageView img_12;
    @BindView(R.id.img_13) ImageView img_13;
    @BindView(R.id.img_14) ImageView img_14;
    @BindView(R.id.img_21) ImageView img_21;
    @BindView(R.id.img_22) ImageView img_22;
    @BindView(R.id.img_23) ImageView img_23;
    @BindView(R.id.img_24) ImageView img_24;
    @BindView(R.id.img_31) ImageView img_31;
    @BindView(R.id.img_32) ImageView img_32;
    @BindView(R.id.img_33) ImageView img_33;
    @BindView(R.id.img_34) ImageView img_34;

    ImageView[] imgViews;

    Integer[] cardsArray = {101, 102, 103, 104, 105, 106,
            201, 202, 203, 204, 205, 206};

    int firstCard, secondCard;
    int clickedFirst, clickedSecond;
    int cardNumber = 1;

    int turn = 1;
    int playerOnePoints = 0, playerTwoPoints = 0;

    Boolean singlePlayerMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getPhotosFromApi();
        setupSharedPreferences();

        Intent intent = getIntent();
        singlePlayerMode = intent.getBooleanExtra("singlePlayer", false);

        ButterKnife.bind(this);

        if (singlePlayerMode) {
            playerTwoTextView.setVisibility(View.GONE);
        }

        img_11.setTag("0");
        img_12.setTag("1");
        img_13.setTag("2");
        img_14.setTag("3");
        img_21.setTag("4");
        img_22.setTag("5");
        img_23.setTag("6");
        img_24.setTag("7");
        img_31.setTag("8");
        img_32.setTag("9");
        img_33.setTag("10");
        img_34.setTag("11");

        imgViews = new ImageView[]{img_11, img_12, img_13, img_14, img_21, img_22, img_23, img_24, img_31,
                img_32, img_33, img_34};

        // shuffle dhe images
        Collections.shuffle(Arrays.asList(cardsArray));

        // changing the color of the second player(inactive)
        playerTwoTextView.setTextColor(Color.GRAY);

        // listeners for every imageView
        for (final ImageView imgView : imgViews) {
            imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int theCard = Integer.parseInt((String) v.getTag());
                    showImage(imgView, theCard);
                }
            });
        }
    }

    private void showImage(ImageView img, int card) {
        // load images for specific views after clicked
        if (cardsArray[card] == 101) {
            Picasso.with(this).load(photos.get(0).getImgUrl()).into(img);
        } else if (cardsArray[card] == 102) {
            Picasso.with(this).load(photos.get(1).getImgUrl()).into(img);
        } else if (cardsArray[card] == 103) {
            Picasso.with(this).load(photos.get(2).getImgUrl()).into(img);
        } else if (cardsArray[card] == 104) {
            Picasso.with(this).load(photos.get(3).getImgUrl()).into(img);
        } else if (cardsArray[card] == 105) {
            Picasso.with(this).load(photos.get(4).getImgUrl()).into(img);
        } else if (cardsArray[card] == 106) {
            Picasso.with(this).load(photos.get(5).getImgUrl()).into(img);
        } else if (cardsArray[card] == 201) {
            Picasso.with(this).load(photos.get(0).getImgUrl()).into(img);
        } else if (cardsArray[card] == 202) {
            Picasso.with(this).load(photos.get(1).getImgUrl()).into(img);
        } else if (cardsArray[card] == 203) {
            Picasso.with(this).load(photos.get(2).getImgUrl()).into(img);
        } else if (cardsArray[card] == 204) {
            Picasso.with(this).load(photos.get(3).getImgUrl()).into(img);
        } else if (cardsArray[card] == 205) {
            Picasso.with(this).load(photos.get(4).getImgUrl()).into(img);
        } else if (cardsArray[card] == 206) {
            Picasso.with(this).load(photos.get(5).getImgUrl()).into(img);
        }

        //check which image is selected and save it to temporary variable
        if (cardNumber == 1) {
            firstCard = cardsArray[card];
            if (firstCard > 200) {
                firstCard = firstCard - 100;
            }
            cardNumber = 2;
            clickedFirst = card;
            img.setEnabled(false);
        } else if (cardNumber == 2) {
            secondCard = cardsArray[card];
            if (secondCard > 200) {
                secondCard = secondCard - 100;
            }
            cardNumber = 1;
            clickedSecond = card;

            // all imageviews are set disabled (only two cards can be opened)
            for (ImageView imgView : imgViews) {
                imgView.setEnabled(false);
            }

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //check if the selected images are equal
                    checkSuccess();
                }
            }, 500);

        }
    }

    private void checkSuccess() {
        if (firstCard == secondCard) {
            // make the first image invisible
            imgViews[clickedFirst].setVisibility(View.INVISIBLE);

            // make the second image invisible
            imgViews[clickedSecond].setVisibility(View.INVISIBLE);

            // change players turn
            if (singlePlayerMode) {
                playerOnePoints++;
                playerOneTextView.setText("P1: " + playerOnePoints);
                playerTwoTextView.setVisibility(View.INVISIBLE);
            } else {
                if (turn == 1) {
                    playerOnePoints++;
                    playerOneTextView.setText("P1: " + playerOnePoints);
                } else if (turn == 2) {
                    playerTwoPoints++;
                    playerTwoTextView.setText("P2: " + playerTwoPoints);
                }
            }
        }
        // -----------
        else {
            // if images are not equal than all imageViews are the same (boxicon image)
            if (singlePlayerMode) {
                for (ImageView imgView : imgViews) {
                    imgView.setImageResource(R.drawable.boxicon);
                }
            } else {
                for (ImageView imgView : imgViews) {
                    imgView.setImageResource(R.drawable.boxicon);
                }
                // change the player turn
                if (turn == 1) {
                    turn = 2;
                    playerOneTextView.setTextColor(Color.GRAY);
                    playerTwoTextView.setTextColor(Color.BLACK);
                } else if (turn == 2) {
                    turn = 1;
                    playerOneTextView.setTextColor(Color.GRAY);
                    playerTwoTextView.setTextColor(Color.BLACK);
                }
            }
        }

        // if the same images are not same then set all the imageViews to enabled (that can be clicked)
        for (ImageView imgView : imgViews) {
            imgView.setEnabled(true);
        }

        //check if the game is over
        checkEndGame();
    }

    private void checkEndGame() {

        boolean allAreInvisibile = true;

        // check if any imageView is visible
        for (ImageView imgView : imgViews) {
            if (imgView.getVisibility() == View.VISIBLE) {
                allAreInvisibile = false;
            }
        }

        // if they all are invisible then alertDialog is displayed
        if (allAreInvisibile) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            if (singlePlayerMode) {
                alertDialogBuilder.setMessage("GAME OVER! \nYour Points: " + playerOnePoints);
            } else {
                alertDialogBuilder.setMessage("GAME OVER! \nP1: " + playerOnePoints + "\nP2:" + playerTwoPoints);
            }

            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("NEW", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
    }

    private void getPhotosFromApi(String tag) {

        Uri baseUri = Uri.parse(FLICKER_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("method", "flickr.photos.search");
        uriBuilder.appendQueryParameter("api_key", FLICKER_API_KEY);
        uriBuilder.appendQueryParameter("tags", tag);
        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("nojsoncallback", "1");

        JsonObjectRequest photoRequest = new JsonObjectRequest
                (Request.Method.GET, uriBuilder.toString(), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        photos = PhotoJsonUtils.extractPhotos(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        NetHelper.getInstance(this).addToRequestQueue(photoRequest);
    }

    private void setupSharedPreferences() {
        // Get all of the values from shared preferences to set it up
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        loadTagSearchOptions(sharedPreferences);

        // Register the listener
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    // Take the value from settings
    private void loadTagSearchOptions(SharedPreferences sharedPreferences) {
        mTag = sharedPreferences.getString(getString(R.string.pref_tag_key),
                getString(R.string.pref_tag_dog_value));
        getPhotosFromApi(mTag);
    }

    // The value of settings is changed ( search Tag)
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_tag_key))) {
            loadTagSearchOptions(sharedPreferences);
        }
    }

}

package de.sopa.manager;

import android.app.Activity;

import android.os.Bundle;

import android.util.Log;

import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;

import com.google.example.games.basegameutils.BaseGameUtils;

import de.sopa.GameActivity;
import de.sopa.R;


public class GoogleService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_UNUSED = 5001;
    private final GoogleApiClient mGoogleApiClient;
    private final Activity activity;

    public GoogleService(Activity activity) {

        this.activity = activity;
        mGoogleApiClient = new GoogleApiClient.Builder(activity).addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(Plus.API)
            .addScope(Plus.SCOPE_PLUS_LOGIN)
            .addApi(Games.API)
            .addScope(Games.SCOPE_GAMES)
            .build();
    }

    public void connect() {

        mGoogleApiClient.connect();
    }


    public void disconnect() {

        mGoogleApiClient.disconnect();
    }


    public boolean isConnected() {

        return mGoogleApiClient.isConnected();
    }


    public void submitScore(long score) {

        if (mGoogleApiClient.isConnected()) {
            Games.Leaderboards.submitScore(mGoogleApiClient, activity.getString(R.string.leaderboard_highscore), score);
        }
    }


    public void showLeaderboard() {

        if (this.isConnected()) {
            activity.startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(mGoogleApiClient), RC_UNUSED);
        } else {
            activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        Toast toast = Toast.makeText(activity, "You have to connect to Google Play. Go to settings.",
                                Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
        }
    }


    @Override
    public void onConnected(Bundle bundle) {

        Log.i("Google Service", "connection to google play successful");
        activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    Toast toast = Toast.makeText(activity, "Connection to Google Play successful.", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
    }


    @Override
    public void onConnectionSuspended(int i) {

        Log.i("Game activity", "onConnectionSuspended()");
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        BaseGameUtils.resolveConnectionFailure(activity, mGoogleApiClient, connectionResult, GameActivity.RC_SIGN_IN,
            "Failed hard");
        Log.i("Game activity", "connection resolving status " + connectionResult.toString());
    }
}

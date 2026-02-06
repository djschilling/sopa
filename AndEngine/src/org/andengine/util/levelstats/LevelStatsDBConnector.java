package org.andengine.util.levelstats;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.andengine.util.StreamUtils;
import org.andengine.util.call.Callback;
import org.andengine.util.debug.Debug;
import org.andengine.util.math.MathUtils;
import org.andengine.util.preferences.SimplePreferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 21:13:55 - 18.10.2010
 */
public class LevelStatsDBConnector {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final String PREFERENCES_LEVELSTATSDBCONNECTOR_PLAYERID_ID = "preferences.levelstatsdbconnector.playerid";

	// ===========================================================
	// Fields
	// ===========================================================

	private final String mSecret;
	private final String mSubmitURL;
	private final int mPlayerID;

	// ===========================================================
	// Constructors
	// ===========================================================

	public LevelStatsDBConnector(final Context pContext, final String pSecret, final String pSubmitURL) {
		this.mSecret = pSecret;
		this.mSubmitURL = pSubmitURL;

		final SharedPreferences simplePreferences = SimplePreferences.getInstance(pContext);
		final int playerID = simplePreferences.getInt(PREFERENCES_LEVELSTATSDBCONNECTOR_PLAYERID_ID, -1);
		if(playerID != -1) {
			this.mPlayerID = playerID;
		} else {
			this.mPlayerID = MathUtils.random(1000000000, Integer.MAX_VALUE);
			SimplePreferences.getEditorInstance(pContext).putInt(PREFERENCES_LEVELSTATSDBCONNECTOR_PLAYERID_ID, this.mPlayerID).commit();
		}
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	public void submitAsync(final int pLevelID, final boolean pSolved, final int pSecondsElapsed) {
		this.submitAsync(pLevelID, pSolved, pSecondsElapsed, null);
	}

	public void submitAsync(final int pLevelID, final boolean pSolved, final int pSecondsElapsed, final Callback<Boolean> pCallback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try{
					final String postData =
							"level_id=" + URLEncoder.encode(String.valueOf(pLevelID), "UTF-8")
									+ "&solved=" + URLEncoder.encode((pSolved) ? "1" : "0", "UTF-8")
									+ "&secondsplayed=" + URLEncoder.encode(String.valueOf(pSecondsElapsed), "UTF-8")
									+ "&player_id=" + URLEncoder.encode(String.valueOf(LevelStatsDBConnector.this.mPlayerID), "UTF-8")
									+ "&secret=" + URLEncoder.encode(String.valueOf(LevelStatsDBConnector.this.mSecret), "UTF-8");

					connection = (HttpURLConnection) new URL(LevelStatsDBConnector.this.mSubmitURL).openConnection();
					connection.setRequestMethod("POST");
					connection.setDoOutput(true);
					connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

					final OutputStream outputStream = connection.getOutputStream();
					outputStream.write(postData.getBytes("UTF-8"));
					outputStream.flush();
					outputStream.close();

					final int statusCode = connection.getResponseCode();
					if(statusCode == HttpURLConnection.HTTP_OK) {
						final String response = StreamUtils.readFully(connection.getInputStream());

						if(response.equals("<success/>")) {
							if(pCallback != null) {
								pCallback.onCallback(true);
							}
						} else {
							if(pCallback != null) {
								pCallback.onCallback(false);
							}
						}
					} else {
						if(pCallback != null) {
							pCallback.onCallback(false);
						}
					}
				}catch(final IOException e) {
					Debug.e(e);
					if(pCallback != null) {
						pCallback.onCallback(false);
					}
				} finally {
					if(connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}

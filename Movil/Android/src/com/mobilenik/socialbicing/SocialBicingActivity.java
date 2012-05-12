package com.mobilenik.socialbicing;

import android.app.Activity;
import android.os.Bundle;

import com.mobilenik.socialbicing.ui.LoginScreen;
import com.mobilenik.socialbicing.ui.Screen;
import com.mobilenik.socialbicing.ui.SplashScreen;

public class SocialBicingActivity extends Activity {
	
	private static SocialBicingActivity instance; 

	private Screen currentScreen;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.instance = this;
		super.onCreate(savedInstanceState);

	}

	public void init() {
		// TODO: Ir al Splash
		currentScreen = new SplashScreen();

		currentScreen = new LoginScreen();
	}

	public Screen getCurrentScreen() {
		return currentScreen;
	}

	
	//Lamar para setear la pantalla actual
	public void setCurrentScreen(Screen currentScreen) {
		this.currentScreen = currentScreen;
		setContentView(currentScreen.getScreenID());
	}
	
	public static SocialBicingActivity instance(){
		return instance;
	}

}
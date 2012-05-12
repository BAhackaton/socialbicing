package com.mobilenik.socialBicing.ui.screens;

import com.mobilenik.core.ui.screens.MKScreen;
import com.mobilenik.socialBicing.logic.MKScreenTypesE;

/**
 * Factory
 */
public class ScreenFactoryBBGen implements ScreenFactory
{
    public MKScreen createScreen(int screenType)
    {
    	MKScreen screen = null;

        switch(screenType)
        {
            case MKScreenTypesE.LOADING:
                screen = new LoadingScreen();
            break;
            case MKScreenTypesE.LOGIN:
                screen = new LoginScreen();
            break;
//            case MKScreenTypesE.BIKE_SEARCH:
//            	screen = new BikeSearchScreen();
//            break;
        }//end switch
        return screen;
    }//createScreen
}

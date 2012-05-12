package com.mobilenik.socialBicing.logic;

import com.mobilenik.socialBicing.ui.screens.ScreenFactory;
import com.mobilenik.socialBicing.ui.screens.ScreenFactoryBBGen;
import com.mobilenik.util.device.Logger;
import com.mobilenik.util.device.MKDevice;

/**
 * Factory provider
 * provee fábrica de pantallas según dispositivo
 */
public class MKScreenFactoryProvider 
{
    public static ScreenFactory getScreenFactory()
    {
        ScreenFactory screenFactory;
        
        String deviceName = MKDevice.getDeviceName();
        
        Logger.log("MKScreenFactoryProvider.getScreenFactory()  deviceName: "+deviceName);
        screenFactory = new ScreenFactoryBBGen();
               
        return screenFactory;
    }
    
} 

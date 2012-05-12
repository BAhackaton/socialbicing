/*
 * ScreenFactory.java
 *
 * © <your company here>, 2003-2005
 * Confidential and proprietary.
 */

package com.mobilenik.socialBicing.ui.screens;

import com.mobilenik.core.ui.screens.MKScreen;
import com.mobilenik.core.ui.screens.MKScreenFactoryException;

/**
 * 
 */
public interface ScreenFactory 
{
    public MKScreen createScreen(int screenType) throws MKScreenFactoryException;
} 

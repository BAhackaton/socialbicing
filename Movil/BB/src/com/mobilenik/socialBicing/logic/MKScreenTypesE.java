package com.mobilenik.socialBicing.logic;

import com.mobilenik.core.ui.screens.MKScreenTypes;

/**
 * Enum tipos de pantallas que devuelve MKScreenFactory
 * De 0 a 100: pantallas de sistema
 * De 101 a 200: pantallas de empresas
 */
public abstract class MKScreenTypesE extends MKScreenTypes
{        
	public static final int LOADING = 1;
	public static final int LOGIN = 101;
	public static final int BIKE_SEARCH = 102;
	public static final int BICI = 0;
	
} 

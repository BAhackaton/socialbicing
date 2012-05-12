package com.mobilenik.socialbicing;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class StationsItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> overlays;
	private Context context;

	public StationsItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		this.context = context;
		this.overlays = new ArrayList<OverlayItem>();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return overlays.get(i);
	}

	@Override
	public int size() {
		return overlays.size();
	}

	public void addOverlay(OverlayItem item) {
		overlays.add(item);
		populate();
	}

	@Override
	protected boolean onTap(int index) {
		OverlayItem item = overlays.get(index);
		context.startActivity(new Intent(context, BikeActivity.class));
		return true;
	}

}

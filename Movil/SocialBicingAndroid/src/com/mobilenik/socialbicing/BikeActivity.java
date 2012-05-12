package com.mobilenik.socialbicing;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BikeActivity extends Activity {

	private Button btnReserve;
	private Button btnCancel;
	private Button btnTake;
	private Button btnRelease;
	private Button btnDeliver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bike);

		btnReserve = (Button) findViewById(R.id.btn_reserve);
		btnReserve.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				btnReserve.setVisibility(View.GONE);
				btnTake.setVisibility(View.VISIBLE);
				btnCancel.setVisibility(View.VISIBLE);
			}
		});

		btnCancel = (Button) findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				btnTake.setVisibility(View.GONE);
				btnCancel.setVisibility(View.GONE);
				btnReserve.setVisibility(View.VISIBLE);
			}
		});

		btnTake = (Button) findViewById(R.id.btn_take);
		btnTake.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				btnTake.setVisibility(View.GONE);
				btnCancel.setVisibility(View.GONE);
				btnRelease.setVisibility(View.VISIBLE);
			}
		});

		btnRelease = (Button) findViewById(R.id.btn_release);
		btnRelease.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				btnRelease.setVisibility(View.GONE);
				btnDeliver.setVisibility(View.VISIBLE);
			}
		});

		btnDeliver = (Button) findViewById(R.id.btn_deliver);
		btnDeliver.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				btnDeliver.setVisibility(View.GONE);
			}
		});

	}

}

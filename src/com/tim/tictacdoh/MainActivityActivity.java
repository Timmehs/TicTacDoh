package com.tim.tictacdoh;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MainActivityActivity extends Activity {
	private static final String TAG = MainActivityActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		Log.d(TAG, "Loggin it");
		ImageButton starter = (ImageButton) findViewById(R.id.startButton);
		
		starter.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0){
				final MediaPlayer cock = MediaPlayer.create(getApplicationContext(), R.raw.shot);
				cock.setOnCompletionListener(new OnCompletionListener() {
					public void onCompletion(MediaPlayer mp) {
						Intent startGame = new Intent(getApplicationContext(), TicTacDoh.class );
						startActivity(startGame);
						overridePendingTransition(R.animator.right_slide_in,R.animator.right_slide_out);
						
					}
				});
				cock.start();
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity, menu);
		return true;
	}
	
	

}

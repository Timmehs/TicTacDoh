package com.tim.tictacdoh;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class TicTacDoh extends Activity {
	private static final String TAG = TicTacDoh.class.getSimpleName();
	public String theWinner = null;
	BoardPiece sp1;
	BoardPiece sp2;
	BoardPiece sp3;
	BoardPiece sp4;
	BoardPiece sp5;
	BoardPiece sp6;
	BoardPiece sp7;
	BoardPiece sp8;
	BoardPiece sp9;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.cock);
		mp.setOnCompletionListener(new OnCompletionListener(){
			public void onCompletion(MediaPlayer mp){
				mp.reset();
			}
		});
		mp.start();
		Log.d(TAG, "Starting new game");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tic_tac_doh);
		sp1 = new BoardPiece(this, R.id.sp1);
		sp2 = new BoardPiece(this, R.id.sp2);
		sp3 = new BoardPiece(this, R.id.sp3);
		sp4 = new BoardPiece(this, R.id.sp4);
		sp5 = new BoardPiece(this, R.id.sp5);
		sp6 = new BoardPiece(this, R.id.sp6);
		sp7 = new BoardPiece(this, R.id.sp7);
		sp8 = new BoardPiece(this, R.id.sp8);
		sp9 = new BoardPiece(this, R.id.sp9);
		addListener(sp1); addListener(sp2); addListener(sp3);
		addListener(sp4); addListener(sp5); addListener(sp6);
		addListener(sp7); addListener(sp8); addListener(sp9);
	}
	
	
	public void gameCycle(final BoardPiece bp){
		
		if(bp.isPlayable()){
			bp.btn.setImageResource(R.drawable.x);
			final MediaPlayer punch = MediaPlayer.create(getApplicationContext(), R.raw.punch);
			punch.setOnCompletionListener(new OnCompletionListener(){
				public void onCompletion(MediaPlayer mp){
					bp.setState(1);
					checkEnd();
					if(theWinner != null){
						return;
					} else {
						BoardPiece winningMoveCheck = winningMove();
						computerMove(winningMoveCheck);
						checkEnd();
					}
				}
			});
			punch.start();
			
		} else{
			
			Context context = getApplicationContext();
			CharSequence invalid = "Not a valid move";
			Toast toast = Toast.makeText(context, invalid, Toast.LENGTH_SHORT);
			toast.show();
			
		}
	
	}
	
	
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tic_tac_doh, menu);
		return true;
	}
	
	
	public void computerMove(BoardPiece winningmove){
		BoardPiece[] pieces = {sp1, sp2, sp3, sp4, sp5, sp6 , sp7, sp8, sp9};
		BoardPiece[] corners = {sp1, sp3, sp7, sp9};
		BoardPiece winner = winningmove;
		final MediaPlayer glass = MediaPlayer.create(getApplicationContext(), R.raw.glass);
		glass.setOnCompletionListener(new OnCompletionListener(){
			public void onCompletion(MediaPlayer mp){
				mp.reset();
			}
		});
		// BLOCK OR TAKE WINNING MOVES FIRST
		if(winner != null){
			winner.btn.setImageResource(R.drawable.o);
			glass.start();
			winner.setState(4);
			return;	
		}
		//CHECK CENTER, PLAY IF AVAILABLE
		for(int j = 0; j < 4;j++){ 
			if(sp5.isPlayable()){
				sp5.btn.setImageResource(R.drawable.o);
				glass.start();
				sp5.setState(4);
				return;
			}
			//PLAY AVAILABLE CORNERS
			else if(corners[j].isPlayable()){
				corners[j].btn.setImageResource(R.drawable.o);
				glass.start();
				corners[j].setState(4);
				return;
			} else {
				//SCAN FOR AVAILABLE MOVE
				for(int i = 0; i < 8; i++){
					if(pieces[i].isPlayable()){
						pieces[i].btn.setImageResource(R.drawable.o);
						glass.start();
						pieces[i].setState(4);
						Log.d(TAG, "Computer Played Next Available Move");
						return;
					}	
				}
			}
		
		}
	}
	
	
	//CHECK FOR POSSIBLE WINNING MOVES
	public BoardPiece winningMove(){
		BoardPiece[] play1 = {sp1,sp2,sp3};
		BoardPiece[] play2 = {sp4,sp5,sp6};
		BoardPiece[] play3 = {sp7,sp8,sp9};
		BoardPiece[] play4 = {sp1,sp4,sp7};
		BoardPiece[] play5 = {sp2,sp5,sp8};
		BoardPiece[] play6 = {sp3,sp6,sp9};
		BoardPiece[] play7 = {sp1,sp5,sp9};
		BoardPiece[] play8 = {sp7,sp5,sp3};
		BoardPiece[][] playBook = {play1, play2, play3, play4,play5,play6,play7,play8};
		for(int i = 0; i<8;i++){
			BoardPiece[] thisPlay = playBook[i];
			int rowSum = thisPlay[0].state + thisPlay[1].state + thisPlay[2].state;
			if(rowSum == 2){
				for(int j = 0; j < 3;j++){
					if(thisPlay[j].isPlayable()){
						return thisPlay[j];
					}
				}	
			} else if (rowSum == 8){
				for(int j = 0; j < 3;j++){
					if(thisPlay[j].isPlayable()){
						return thisPlay[j];
					}
				}
			}
		}
		return null;
	}
	
	
	public void addListener(final BoardPiece bp){
			
			bp.btn.setOnClickListener(new View.OnClickListener(){
				public void onClick(View v){
					gameCycle(bp);
					
				
				}
				
			});
			
		}
	
	//SEE IF 
	public void checkEnd(){
		BoardPiece[] play1 = {sp1,sp2,sp3};
		BoardPiece[] play2 = {sp4,sp5,sp6};
		BoardPiece[] play3 = {sp7,sp8,sp9};
		BoardPiece[] play4 = {sp1,sp4,sp7};
		BoardPiece[] play5 = {sp2,sp5,sp8};
		BoardPiece[] play6 = {sp3,sp6,sp9};
		BoardPiece[] play7 = {sp1,sp5,sp9};
		BoardPiece[] play8 = {sp7,sp5,sp3};
		BoardPiece[] pieces = {sp1, sp2, sp3, sp4, sp5, sp6 , sp7, sp8, sp9};
		BoardPiece[][] playBook = {play1, play2, play3, play4,play5,play6,play7,play8};
		for(int i = 0; i<8;i++){
			BoardPiece[] thisPlay = playBook[i];
			int rowSum = thisPlay[0].state + thisPlay[1].state + thisPlay[2].state;

			//CHECK FOR HUMAN WIN
			if(rowSum == 3){
				theWinner = "human";
				announceWinner();
				    
			} //CHECK EACH POSSIBLE PLAY FOR CPU WIN
				else if (rowSum == 12){
				theWinner = "computer";
				announceWinner();
				
			}
		}
		//CHECK TO SEE IF BOARD IS FULL
		Boolean movesLeft = false;
		for(int x = 0; x<9; x++){
			if(pieces[x].state == 0){
				movesLeft = true;
				return;
			}
		}
		if(!movesLeft){
			announceWinner();
			/*this.finish();
		    overridePendingTransition  (R.animator.right_slide_in, R.animator.right_slide_out);
		    return;*/
		}
	}

	
	@Override
	public void onBackPressed() 
	{
	    this.finish();
	    overridePendingTransition  (R.animator.right_slide_in, R.animator.right_slide_out);
	    return;
	}

	
	public void announceWinner(){
		String entity = "";
		String consolation = "";
		String title = "";
		int icon = 0;
		AlertDialog.Builder theWinnerIs = new AlertDialog.Builder(this);
		if("human".equals(theWinner)){
			entity = "You've won against the computer!";
			consolation = "There is still hope.";
			title = "Humans have prevailed";
			icon = R.drawable.won;
		} else if ("computer".equals(theWinner)){
			entity = "The machines have prevailed over their creators.";
			consolation = "It has begun.";
			title = "You BLEW it!";
			icon = R.drawable.lost;
		} else {
			entity = "A stalemate.";
			consolation = "";
			title = "CATS GAME!";
			icon = R.drawable.lost;
		}
		
		theWinnerIs.setPositiveButton("OK", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id){
				Intent closeGame = new Intent(getApplicationContext(), MainActivityActivity.class );
				startActivity(closeGame);
				overridePendingTransition(R.animator.right_slide_in,R.animator.right_slide_out);
			}
		});
		theWinnerIs.setNegativeButton("Quit", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id){
				Intent closeGame = new Intent(getApplicationContext(), MainActivityActivity.class );
				startActivity(closeGame);
				overridePendingTransition(R.animator.right_slide_in,R.animator.right_slide_out);
			}
		});
		theWinnerIs.setTitle(title);
		theWinnerIs.setMessage(entity + " " + consolation);
		theWinnerIs.setIcon(icon);
		theWinnerIs.show();
	}

}

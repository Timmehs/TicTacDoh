package com.tim.tictacdoh;

import android.app.Activity;
import android.widget.ImageButton;

public class BoardPiece {
	ImageButton btn;
	int state = 0; // 0-Blank 1-X 2-O
	String position; 
	
	public int getState(){
		return this.state;
		
	}
	public void setState(int state){
		this.state = state;
	}
	public BoardPiece(){
	}
	
	public BoardPiece(Activity activity, int id){
		this.btn = (ImageButton) activity.findViewById(id);
	}
	
	public Boolean isPlayable(){
		if(this.state == 0){
			return true;
		} else {
			return false;
		}
	}
	
}
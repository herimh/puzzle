package heri.games.puzzle;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

class PuzzleButton
{
	private Bitmap bitmap;
	private int x, y;
	private int initialX, initialY;
	private int N;
	public boolean isEmpty = false;
	
	public PuzzleButton(){}
	
	public PuzzleButton(Resources r, int d){
		this.bitmap = BitmapFactory.decodeResource(r, d);
	}
	
	public void setBitmap(Bitmap bitmap){
		this.bitmap = bitmap;
	}
	public Bitmap getBitmap(){
		return this.bitmap;
	}
	public void setX(int x){
		this.x = x;
	}
	public int getX(){
		return this.x;
	}
	public void setY(int y){
		this.y = y;
	}
	public int getY(){
		return this.y;
	}
	public void backupInitPosition(){
		this.initialX = this.x;
		this.initialY = this.y;
	}
	public int getInitialX(){
		return this.initialX;
	}
	public int getInitialY(){
		return this.initialY;
	}
	public void setNumber(int N){
		this.N = N;
	}
	public int getNumber(){
		return this.N;
	}
}

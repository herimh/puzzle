package heri.games.puzzle;

import java.util.*;
import android.content.*;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameArea extends View implements OnTouchListener
{
	Paint paint = new Paint();
	Paint bAreaPaint;
	Point lastTouchPoint = null;
	Bitmap[] emptyButton;
	Bitmap background;
	Bitmap state_bar;
	PuzzleButton[][] button = new PuzzleButton[4][4];
	GameActivity gameReference;
	
	private final int[] initOrder = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};
	private int[] loadedOrder;
	private int screenWidth, screenHeight;				//Screen Width				
	private int bsCol, bsRow;				//Button selected Column and Row
	private int beCol, beRow;				//Button Empty column and Row
	private boolean isValidMoving = true;
	
	public GameArea(Context context)
	{
		super(context);
		setFocusable(true);
		setFocusableInTouchMode(true);
		
		gameReference = (GameActivity)context;
		this.setPaints();
		
		this.setOnTouchListener(this);
		this.startGame();
		
	}
	
	private void startGame(){
		
		if(gameReference.resumeGame){ //Resume game
			if((loadedOrder = GameData.loadGame())!=null){
				loadButtonImages(loadedOrder);
			}else{
				loadButtonImages(initOrder);
			}
		}
		else{ //Start new game
			loadButtonImages(initOrder);
			mixButtons();
		}
	}
	
	private void loadButtonImages(int[] order)
	{
		int cont=0;
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				button[i][j] = this.getButtonIn(order[cont]);
				cont++;
			}
		}
		
		this.background = BitmapFactory.decodeResource(this.getResources(), R.drawable.game_back_2);
		this.state_bar = BitmapFactory.decodeResource(this.getResources(), R.drawable.state_bar);
	}
	
	private PuzzleButton getButtonIn(int nButton)
	{
		PuzzleButton puzzleButton = new PuzzleButton();
		puzzleButton.setNumber(nButton);
		
		switch (nButton)
		{
			case 0:
				puzzleButton.setBitmap(Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888));
				puzzleButton.isEmpty = true;
			break;
			case 1:
				puzzleButton.setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.button_1));
			break;
			case 2:
				puzzleButton.setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.button_2));
			break;
			case 3:
				puzzleButton.setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.button_3));
			break;
			case 4:
				puzzleButton.setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.button_4));
			break;
			case 5:
				puzzleButton.setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.button_5));
			break;
			case 6:
				puzzleButton.setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.button_6));
			break;
			case 7:
				puzzleButton.setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.button_7));
			break;
			case 8:
				puzzleButton.setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.button_8));
			break;
			case 9:
				puzzleButton.setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.button_9));
			break;
			case 10:
				puzzleButton.setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.button_10));
			break;
			case 11:
				puzzleButton.setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.button_11));
			break;
			case 12:
				puzzleButton.setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.button_12));
			break;
			case 13:
				puzzleButton.setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.button_13));
			break;
			case 14:
				puzzleButton.setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.button_14));
			break;
			case 15:
				puzzleButton.setBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.button_15));
			break;
		}
		
		return puzzleButton;
	}
	
	private void mixButtons()
	{
		Random r= new Random();
		int rI, rJ;
		
		for(int i=0 ; i<4 ; i++){
			for(int j=0; j<4; j++){
				rI = r.nextInt(4);
				rJ = r.nextInt(4);
				
				PuzzleButton auxButton = button[i][j];
				button[i][j] = button[rI][rJ];
				button[rI][rJ] = auxButton;
			}
		}
	}
	
	@Override
	public void onSizeChanged(int xNew, int yNew, int xOld, int yOld)
	{
		super.onSizeChanged(xNew, yNew, xOld, yOld);
		
		this.screenWidth = xNew;
		this.screenHeight = yNew;
		this.scaleButtonImages();
	}
	
	private void scaleButtonImages()
	{
		int scale = (int)(screenWidth/4);
		
		for(int i=0 ; i<4 ; i++){
			for(int j=0; j<4; j++){
				button[i][j].setBitmap(Bitmap.createScaledBitmap(button[i][j].getBitmap(), scale, scale, true));
				button[i][j].setX(j*scale);
				button[i][j].setY(i*scale);
			}
		}
		background = Bitmap.createScaledBitmap(background, screenWidth, screenHeight, true);
		state_bar = Bitmap.createScaledBitmap(state_bar, screenWidth+4, screenHeight-(scale*4), true);
	}
	
	@Override
	public void onDraw(Canvas canvas)
	{
		canvas.drawBitmap(background, 0, 0, paint);
		
		for(int i=0 ; i<4 ; i++){
			for(int j=0; j<4; j++){
				canvas.drawBitmap(button[i][j].getBitmap(), button[i][j].getX(), button[i][j].getY(), paint);
			}
		}
		
		canvas.drawBitmap(this.state_bar, -3, screenWidth+3, paint);
		canvas.drawText((String)this.gameReference.timer.getText(), 30, screenWidth+10, paint);
	}
	
	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		Point p = new Point();
		
		p.x=(int)event.getX();
		p.y=(int)event.getY();
		
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			this.setButtonSelected(p);
			try{
				button[bsRow][bsCol].backupInitPosition();
				this.validateMovement();
			}
			catch(Exception ex){
				isValidMoving = false;
			}
		}
		
		if(event.getAction()==MotionEvent.ACTION_UP && isValidMoving)
		{
			this.localizateEmptyButton();		//Update de "Empty Button Axis" saved in beRow and beCol as global variables
			
			PuzzleButton empty = button[beRow][beCol];
			
			button[bsRow][bsCol].setX(empty.getX());
			button[bsRow][bsCol].setY(empty.getY());
			
			
			empty.setX(button[bsRow][bsCol].getInitialX());
			empty.setY(button[bsRow][bsCol].getInitialY());
			
			button[beRow][beCol] = button[bsRow][bsCol];
			button[bsRow][bsCol] = empty;
		}
		
		if(isValidMoving){
			moveButton(p);
		}
		
		invalidate();
		
		return true;
	}
	
	public void moveButton(Point p)
	{
		int incremX=0, incremY=0;
		
		if(lastTouchPoint!=null){
			incremX = p.x-lastTouchPoint.x;
			incremY = p.y-lastTouchPoint.y;
			
			if((incremX<=10 && incremX>=-10)&&(incremY<=10&&incremY>=-10)){
				button[bsRow][bsCol].setX(button[bsRow][bsCol].getX()+incremX);
				button[bsRow][bsCol].setY(button[bsRow][bsCol].getY()+incremY);
			}
		}
		
		this.lastTouchPoint = p;
	}
	
	private void validateMovement()
	{
		if(button[bsRow][bsCol].isEmpty){
			this.isValidMoving = false;
		}
		else
		{
			this.localizateEmptyButton();
			
			int difRow = beRow - bsRow; if(difRow<0) difRow*=-1;
			int difCol = beCol - bsCol; if(difCol<0) difCol*=-1;
			
			if((difRow==0 && difCol==1)||(difRow==1 && difCol==0)){
				this.isValidMoving = true;
			}
			else{
				this.isValidMoving = false;
			}
		}	
	}
	
	/**
	 * Localize the empty button position and update the variables: beRow y beCol
	 * @return void
	 */
	private void localizateEmptyButton(){
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				if(this.button[i][j].isEmpty){
					beRow = i;
					beCol = j;
				}
			}
		}
	}
	
	private void setButtonSelected(Point p)
	{
		bsCol = (int)(p.x/(screenWidth/4));
		bsRow = (int)(p.y/(screenWidth/4));
	}
	
	/**
	 * Get the current order of Buttons, build a String with this order separated by \n
	 * and return this to save on disc
	 * 
	 * @return String
	 */
	public String getGameState()
	{
		String gameOrder="";
		
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				if(gameOrder!=""){
					gameOrder+="\n";
				}
				gameOrder+=button[i][j].getNumber();
			}
		}
		
		return gameOrder;
	}
	
	private void setPaints()
	{
		bAreaPaint = new Paint();
		bAreaPaint.setColor(Color.parseColor("#000000"));
		bAreaPaint.setAlpha(190);
		
		paint.setColor(Color.parseColor("#0066CC"));
		paint.setAntiAlias(true);
		paint.setStrokeWidth(0);
	}
}

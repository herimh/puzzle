package heri.games.puzzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;

public class PuzzleActivity extends Activity implements OnClickListener, OnTouchListener{
    /** Called when the activity is first created. */
	
	Button btnStart, btnResume, btnExit;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        
        btnStart = (Button)this.findViewById(R.id.btnNew);
        btnStart.setOnClickListener(this);
        btnStart.setOnTouchListener(this);
        
        btnResume = (Button)this.findViewById(R.id.btnResume);
        btnResume.setOnClickListener(this);
        btnResume.setOnTouchListener(this);
        
        btnExit = (Button)this.findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this); 
        btnExit.setOnTouchListener(this);
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
        btnResume.setEnabled(GameData.savedGameExist());
        btnStart.setBackgroundResource(R.drawable.button);
        btnResume.setBackgroundResource(R.drawable.button);
        btnExit.setBackgroundResource(R.drawable.button);
    }
    
    @Override
    public void onClick(View v)
    {
    	if(v.getId()==btnStart.getId())
    	{
    		try
    		{
    			Intent game = new Intent(this, GameActivity.class);
    			startActivity(game);
    		}
    		catch(Exception ex){
    			System.out.println(ex.getMessage());
    		}
    	}
    	if(v.getId()==btnResume.getId()){
    		try
    		{
    			Intent game = new Intent(this, GameActivity.class);
    			game.putExtra(GameActivity.RESUME_GAME, true);
    			startActivity(game);
    		}
    		catch(Exception ex){
    			System.out.println(ex.getMessage());
    		}
    	}
    	if(v.getId()==btnExit.getId()){
    		this.finish();
    		System.exit(0);
    	}
    }

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			v.setBackgroundResource(R.drawable.button_presed);
		}
		
		return false;
	}
}

package heri.games.puzzle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnKeyListener;
import android.widget.TextView;

public class GameActivity extends Activity implements OnKeyListener{
	
	GameArea gameArea;
	public static final String RESUME_GAME = "heri.games.puzzle.game_state";
	public boolean resumeGame;

	TextView timer;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{	
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		resumeGame = this.getIntent().getBooleanExtra(RESUME_GAME, false);
		timer = new TextView(this);
		
		gameArea = new GameArea(this);
		setContentView(gameArea);
		gameArea.requestFocus();
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(keyCode == KeyEvent.KEYCODE_BACK){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Go to menu?")
			.setPositiveButton("Yes", dlgClickListener)
			.setNeutralButton("Cancel", dlgClickListener)
			.show();
		}
		
		return true;
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
	DialogInterface.OnClickListener dlgClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			switch(which){
				case DialogInterface.BUTTON_POSITIVE:
					GameData.saveGame(gameArea.getGameState());
					finish();
				break;
				case DialogInterface.BUTTON_NEUTRAL:
				break;
			}
		}
	};
}

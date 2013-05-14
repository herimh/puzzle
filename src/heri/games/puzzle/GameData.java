package heri.games.puzzle;

import java.io.*;
import android.util.Log;

public class GameData
{
	private static final String DATA_PATH = "sdcard/Android/data/com.herisoft.puzzle/data/";
	private static String fileName= "order_1.bin";
	
	public GameData()
	{
	}
	
	public static void saveGame(String gameState)
	{
		createDataDirectoryIfNotExist();
		
		try
		{
			FileWriter gameFile = new FileWriter(DATA_PATH+fileName);
			BufferedWriter game = new BufferedWriter(gameFile);
			
			game.write(gameState);
			game.flush();
			game.close();
		}
		catch(Exception ex){
			Log.e("Data writting error::", ex.getMessage());
		}
	}
	
	public static int[] loadGame()
	{
		String sData;
		int[] data=new int[16];
		int cont=0;
		try{
			FileReader gameFile = new FileReader(DATA_PATH+fileName);
			BufferedReader game = new BufferedReader(gameFile);
			
			while((sData = game.readLine())!=null){
				data[cont] = Integer.parseInt(sData);
				cont++;
			};
			
			game.close();
			gameFile.close();
			
			return data;
		}catch(Exception ex){
			Log.e("Data loading error::", ex.getMessage());
		}
		
		return null;
	}
	
	private static void createDataDirectoryIfNotExist()
	{
		File dataDir = new File(DATA_PATH);
		
		if(!dataDir.exists()){
			if(!dataDir.mkdirs()){
				Log.e("TravellerLog :: ", "Problem creating data folder");
			}
		}
	}
	
	public static boolean savedGameExist(){
		try{
			FileReader gameFile = new FileReader(DATA_PATH+fileName);
			BufferedReader game = new BufferedReader(gameFile);
			if(game!=null){
				return true;
			}
		}catch(Exception ex){
			Log.e("TravellerLog :: ", "File not exists");
		}
		
		return false;
	}
}

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

public class Bomb extends NonCollectableItem{

	private static final String BOMB_PATH = "ItemImages/Bomb/";
	private static final String PNG = ".png";
	
	private int countDown = 4;
	private Timer countDownTimer;
	
	public Bomb(int x, int y) {
		super(BOMB_PATH + 4 + PNG, x, y);
	}
	
	private void countDown() {
		switch (countDown) {
			case 0:
				explode();
				break;
			default:
				updateImageView(BOMB_PATH + countDown + PNG);
				countDown--;
		}
	}
	
	public void explode() {
		Platform.runLater(new Runnable() {
			@Override public void run() {
			stopTimer();
			removeItem();
			String[] itemData = {"Explosion", "" + getxLocation(), "" + getyLocation(), "Centre"};
			LevelControl.createExplosion(itemData);
			PlayerApplication.updateScene();
			}
		});
	}
	
	public void startCountDown() {
		if (countDown == 4) {
			countDownTimer = new Timer();
			countDownTimer.scheduleAtFixedRate(new TimerTask(){

		          @Override
		          public void run() {
		        	  countDown();
		          }
		    }, 0, 2000);
			
			countDown--;
			updateImageView(BOMB_PATH + countDown + PNG);
		}
	}
	
	public void stopTimer() {
		if(countDownTimer != null) {
			countDownTimer.cancel();
		}
	}

}

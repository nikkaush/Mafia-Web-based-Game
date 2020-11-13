package com.mafia.application.data;

public class Worker extends Thread {
	GamePlay gp;
	
	public Worker(GamePlay gp) {
		this.gp = gp;
	}
	
	public void run() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gp.startGame();
	}

}

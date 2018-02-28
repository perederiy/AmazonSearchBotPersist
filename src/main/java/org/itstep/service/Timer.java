package org.itstep.service;

public class Timer {

	public static void getTimeInSeconds(int second) {
		
		Thread thread = new Thread();
		try {
			thread.sleep(1000*second);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
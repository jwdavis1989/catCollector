package com.revature.catCollector.util;

public class Dowloader extends Thread{
	@Override
	public void run() {
		for(int i = 0; i <1000000; i++) {
			System.out.println("Downloading " + i + "......");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

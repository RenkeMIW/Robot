package nl.hva.miw.robot.cohort12;

import lejos.hardware.Brick;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.utility.Delay;
import models.*;

public class Marvin {
	
	Brick brick;
	
	public Marvin() {
		super();
		brick = LocalEV3.get();
	}
	
	public static void main(String[] args) {
		Marvin marvin = new Marvin();
		
		marvin.run();
		
	}
	
	private void run() {
		Verplaatsen verplaatsen = new Verplaatsen();
	//	GrijperMotor grijperMotor = new GrijperMotor();
		TextLCD display = brick.getTextLCD();
		display.drawString("Welkom!", 0, 3);
		display.drawString("Team D", 0, 4);
		waitForKey(Button.ENTER);
//		grijperMotor.open();
//		grijperMotor.sluit();
//		grijperMotor.open();
//		grijperMotor.sluit();
//		waitForKey(Button.ENTER);
		verplaatsen.motorPower(60, 60);
		verplaatsen.rijVooruit();
		Delay.msDelay(4000);
//		verplaatsen.motorPower(30,30);
//		Delay.msDelay(2000);
		verplaatsen.motorPower(0, 0);
		verplaatsen.rijVooruit();
		//waitForKey(Button.ENTER);
		verplaatsen.motorPower(50,50);
		verplaatsen.rijAchteruit();
		Delay.msDelay(2000);
		verplaatsen.motorPower(0, 0);
		//waitForKey(Button.ENTER);
		verplaatsen.motorPower(50, 50);
		verplaatsen.draaiRechts();
		Delay.msDelay(2000);
		verplaatsen.motorPower(0, 0);
		//waitForKey(Button.ENTER);
		verplaatsen.motorPower(70,30);
		verplaatsen.draaiLinks();
		Delay.msDelay(2000);
		verplaatsen.motorPower(0, 0);
		//waitForKey(Button.ENTER);
		
	}
	
	public void waitForKey(Key key) {
		while(key.isUp()) {
			Delay.msDelay(100);
		}
		while(key.isDown()) {
			Delay.msDelay(100);
		}
	}
	
	
}

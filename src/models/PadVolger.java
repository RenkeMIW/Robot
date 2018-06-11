package models;
import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.robotics.Color;
import lejos.utility.Delay;
/**
 *  author: Renke/Basti�n
 */
import tools.ColorTools;

public class PadVolger extends TakenModule {
	
	ColorTools padSensor;
	double intensiteit;
	Verplaatsen verplaatsen;
	int vermogen;
	static final int MAX_INTENSITEIT = 60;
	static final int INTENSITEITSPRIMER = 200;
	static final int MAX_POWER = 100;
	static final double MAX_DONKER = 0.25; //maximale hoeveelheid donker voor we het zwart noemen
										//Hiertussen ligt de sweetspot waar Robbie continu naar moet streven
	static final double MIN_LICHT = 0.42; //de minste hoeveelheid intensiteit voor we het wit noemen
	double maxLicht;
	
	public Verplaatsen getVerplaatsen() {
		return verplaatsen;
	}

	public PadVolger() {
		this.padSensor = new ColorTools(SensorPort.S1);
		verplaatsen = new Verplaatsen();
		verplaatsen.motorPower(70, 70);
		padSensor.setMode("Red");
	}
	
	public void voerUit() {
		System.out.println("Calibreer wit");
		Button.ENTER.waitForPress();
		this.maxLicht = intensiteit;
		System.out.printf("Witcalibratie = %f\n", intensiteit);
		System.out.println("Druk op enter bij de start");
		Button.ENTER.waitForPress();
		System.out.println("Druk naar beneden om te stoppen");
		while (Button.DOWN.isUp()){
			leesLicht();
			setVermogen(50);
			rijPadDelta();
		}
		stop();
	}
	
	public void leesLicht() {
		intensiteit = padSensor.getRed();	
	}
	
	//Deze method wordt momenteel niet meer gebruikt en is eigenlijk vervangen door rijPadBeta
//	public void rijPad() {
//		if(intensiteit < MAX_DONKER) {
//			verplaatsen.draaiRechts();
//			leesLicht();
//		}
//		if(intensiteit > MIN_LICHT) {
//			verplaatsen.draaiLinks();
//			leesLicht();
//		}
//		else {
//			verplaatsen.rijVooruit();
//			leesLicht();
//		}
//	}
	
	//Deze method wordt momenteel niet meer gebruikt en is eigenlijk vervangen door rijPadDelta
//	public void rijPadBeta() {
//		leesLicht();
//		verplaatsen.motorPower(((int)(intensiteit * vermogen)), (int)((MIN_LICHT - intensiteit * MAX_POWER) * vermogen / MAX_POWER));
//		verplaatsen.rijVooruit();
//	}
	
	public void rijPadDelta() {
		leesLicht();
		if(intensiteit < MAX_DONKER) {
			verplaatsen.motorPower(vermogen, (int)((vermogen/100) * (intensiteit * INTENSITEITSPRIMER / MAX_DONKER) - MAX_POWER));
			verplaatsen.rijVooruit();
		}
		if(intensiteit > MIN_LICHT) {
			verplaatsen.motorPower((int)((vermogen/MAX_POWER)-((((intensiteit-MIN_LICHT)/(maxLicht-MIN_LICHT)) * INTENSITEITSPRIMER) - MAX_POWER)), vermogen);
			verplaatsen.rijVooruit();
		}
		else {
			verplaatsen.motorPower(vermogen, vermogen);
			verplaatsen.rijVooruit();
		}
	}
	
	// deze method zou ervoor moeten kunnen zorgen dan Robbie weer richting het pad gaat. Nog niet gebruikt/getest
	// deze method zouden we kunnen gebruiken om te starten
	public void rijNaarPad() {
		while(intensiteit > MIN_LICHT) {
			verplaatsen.rijVooruit();
			leesLicht();
		}		
	}
	
	public void stop() {
		verplaatsen.stop();
		padSensor.close();
		
	}
	
	public void printLicht() {
		leesLicht();
		System.out.println(intensiteit);
	}
	
	public void setVermogen(int vermogen) {
		this.vermogen = vermogen;
	}

	public double getIntensiteit() {
		return intensiteit;
	}
}

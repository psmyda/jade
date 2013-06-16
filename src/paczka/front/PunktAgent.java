package paczka.front;

import java.util.Random;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;

public class PunktAgent extends Agent {
	private int bomby;
	private int granaty;
	private int bandaze;
	private int healthPoints = 100;
	
	protected void setup() {	
		Random random = new Random();
		
		setBomby(random.nextInt(100));
		setGranaty(random.nextInt(100));
		setBandaze(random.nextInt(100));
		
		System.out.println("[" + getLocalName().toUpperCase() + "] Mamy " + getBomby() + " bomb " + getGranaty() + " granatów " + getBandaze() + " bandazy");
		
		addBehaviour(new TickerBehaviour(this, 1000) {
			private static final long serialVersionUID = -5325346252799012289L;

			protected void onTick() {
				prowadzDzialaniaWojenne();
			}

		});
	}
	
	private void prowadzDzialaniaWojenne() {
		Random random = new Random();
		
		setBomby(getBomby() - random.nextInt(4));
		if (getBomby() < 0) { setBomby(0); }
		
		setGranaty(getGranaty() - random.nextInt(4));
		if (getGranaty() < 0) { setGranaty(0); }
		
		setBandaze(getBandaze() - random.nextInt(4));
		if (getBandaze() < 0) { setBandaze(0); }
		
		setHealthPoints(getHealthPoints() - random.nextInt(15));
		if (getHealthPoints() < 0) { 
			ZniszczAgenta();
		}
	}
	
	private void ZniszczAgenta() {
		doDelete();
	}
	
	protected void takeDown() {
		System.out.println("[" + getLocalName().toUpperCase() + "] Zniszczony!");
	}
	
	public int getBomby() {
		return bomby;
	}
	
	public void setBomby(int bomby) {
		this.bomby = bomby;
	}
	
	public int getGranaty() {
		return granaty;
	}
	
	public void setGranaty(int granaty) {
		this.granaty = granaty;
	}
	
	public int getBandaze() {
		return bandaze;
	}
	
	public void setBandaze(int bandaze) {
		this.bandaze = bandaze;
	}
	
	public int getHealthPoints() {
		return healthPoints;
	}
	
	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}
	
	private static final long serialVersionUID = -7802439854342069747L;

}

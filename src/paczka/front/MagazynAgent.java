package paczka.front;

import java.util.Random;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class MagazynAgent extends Agent {
	private int bomby;
	private int granaty;
	private int bandaze;
	private int healthPoints = 100;
	
	protected void setup() {		
		Random random = new Random();
		
		setBomby(random.nextInt(100));
		setGranaty(random.nextInt(100));
		setBandaze(random.nextInt(100));
		
		System.out.println("[MAGAZYN] Zape³nianie magazynu ukoñczone! Mamy " + getBomby() + " bomb " + getGranaty() + " granatów " + getBandaze() + " bandazy");
		
		addBehaviour(new TickerBehaviour(this, 60000) {
			private static final long serialVersionUID = 4285462356665418559L;

			protected void onTick() {
				
			}
		});
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

	private static final long serialVersionUID = -2476461798904296939L;
}

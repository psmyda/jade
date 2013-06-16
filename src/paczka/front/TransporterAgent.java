package paczka.front;

import java.util.Random;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class TransporterAgent extends Agent {
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
				
		rejestrujUsluge();
		
		addBehaviour(new TickerBehaviour(this, 1000) {
			private static final long serialVersionUID = 6987384032276634673L;

			protected void onTick() {
				prowadzDzialaniaWojenne();
			}
		});
	}
	
	private void rejestrujUsluge() {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("communication-service");
		sd.setName("transporter-hello");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
	
	private void wyrejestrujUslugeZniszczAgenta() {
		try {
			DFService.deregister(this);
			doDelete();
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
	
	private void prowadzDzialaniaWojenne() {
		Random random = new Random();
		
		setHealthPoints(getHealthPoints() - random.nextInt(15));
		if (getHealthPoints() < 0) { 
			wyrejestrujUslugeZniszczAgenta();
		}
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
		
	private static final long serialVersionUID = 6708328666787197884L;
}

package paczka.front;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
//cel i koncepcja
//za³o¿enia ograniczaj¹ce
//co uproszczone
//opis u¿ywania
//opis testowania

//prezentacja
//klasy zastosowañ agentowych
public class SztabAgent extends Agent {
	private int liczbaTransporterow;
	private int liczbaPunktow;
	private AID[] dostepneTransportery;
	
	protected void setup() {
		ContainerController container = getContainerController();
		AgentController agentController;
		
		System.out.println("Agent " + getName() + " gotowy");
		
		System.out.println("[SZTAB]Budowa magazynu centralnego...");
		budujMagazyn(container);
		wystartujMagazyn(container);
		
		System.out.println("[SZTAB]Budowa transporterów...");	
		
		czytajArgumentyWejscioweAgenta();		
		budujTransportery(container);
		wystartujTransportery(container);
		
		System.out.println("[SZTAB]Zakoñczono budowê transporterow");
		System.out.println("[SZTAB]Tworzenie punktów na linii frontu...");
						
		budujPunkty(container);
		wystartujPunkty(container);
		
		System.out.println("[SZTAB]Przebieg linii frontu ustalony!");
		
		addBehaviour(new TickerBehaviour(this, 10000) {
			private static final long serialVersionUID = -6346412157916930204L;

			protected void onTick() {
				szukajTransporterow();
			}
			
			private void szukajTransporterow() {
				DFAgentDescription template = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setType("communication-service");
				sd.setName("transporter-hello");
				template.addServices(sd);
				try {
					DFAgentDescription[] result = DFService.search(myAgent, template); 
					dostepneTransportery = new AID[result.length];
					if (dostepneTransportery.length > 0) {
						System.out.println("[SZTAB] Znalezione transportery: " + dostepneTransportery.length);
						/*for (int i = 0; i < result.length; ++i) {
							dostepneTransportery[i] = result[i].getName();
							System.out.println("\t[" + dostepneTransportery[i].getLocalName().toUpperCase() + "]");
						}*/
					} else {
						System.out.println("[SZTAB] Brak dostêpnych transporterów!");
					}
				}
				catch (FIPAException fe) {
					fe.printStackTrace();
				}
			}
		});
	}
	
	private void budujMagazyn(ContainerController container) {
		AgentController agentController;
		try {
			agentController = container.createNewAgent("magazynCentralny", "paczka.front.MagazynAgent", null);
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
	}
	
	private void wystartujMagazyn(ContainerController container) {
			try {
				container.getAgent("magazynCentralny").start();
			} catch (ControllerException e) {
				e.printStackTrace();
			}
	}

	private void budujPunkty(ContainerController container) {
		AgentController agentController;
		for (int i = 0; i < liczbaPunktow; i++ ) {
			try {
				agentController = container.createNewAgent("punkt" + i, "paczka.front.PunktAgent", null);
			} catch (StaleProxyException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void wystartujPunkty(ContainerController container) {
		for (int i = 0; i < liczbaPunktow; i++) {
			try {
				container.getAgent("punkt" + i).start();
			} catch (ControllerException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void budujTransportery(ContainerController container) {
		AgentController agentController;
		for (int i = 0; i < liczbaTransporterow; i++ ) {
			try {				
				agentController = container.createNewAgent("transporter" + i, "paczka.front.TransporterAgent", null);
				try {
					container.getAgent("transporter" + i).start();
				} catch (ControllerException e) {
					e.printStackTrace();
				}
			} catch (StaleProxyException e) {
				e.printStackTrace();
			} finally {
			}
		}
	}
	
	private void wystartujTransportery(ContainerController container) {
		for (int i = 0; i < liczbaTransporterow; i++) {
			try {
				container.getAgent("transporter" + i).start();
			} catch (ControllerException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void czytajArgumentyWejscioweAgenta() {
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			liczbaTransporterow = Integer.parseInt((String) args[0]);
			liczbaPunktow = Integer.parseInt((String) args[1]);
		}
	}
	
	protected void takeDown() {
		System.out.println("[SZTAB] Koniec pracy!");
	}
		
	public int getLiczbaTransporterow() {
		return liczbaTransporterow;
	}
	
	public void setLiczbaTransporterow(int liczbaTransporterow) {
		this.liczbaTransporterow = liczbaTransporterow;
	}
	
	public int getLiczbaPunktow() {
		return liczbaPunktow;
	}
	
	public void setLiczbaPunktow(int liczbaPunktow) {
		this.liczbaPunktow = liczbaPunktow;
	}

	private static final long serialVersionUID = 5390211234896077151L;

}

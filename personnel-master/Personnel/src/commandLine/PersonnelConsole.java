package commandLine;

import personnel.*;
import commandLineMenus.*;
import personnel.GestionPersonnel;

import static commandLineMenus.rendering.examples.util.InOut.*;

public class PersonnelConsole {
	private GestionPersonnel gestionPersonnel;
	commandLine.LigueConsole ligueConsole;
	commandLine.EmployeConsole employeConsole;

	public PersonnelConsole(personnel.GestionPersonnel gestionPersonnel) {
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = new commandLine.EmployeConsole();
		this.ligueConsole = new commandLine.LigueConsole(gestionPersonnel, employeConsole);
	}

	public void start() {
		menuPrincipal().start();
	}

	private Menu menuPrincipal() {
		Menu menu = new Menu("Gestion du personnel des ligues");
		menu.add(employeConsole.editerEmploye(gestionPersonnel.getRoot()));
		menu.add(ligueConsole.menuLigues());
		menu.add(menuQuitter());
		return menu;
	}

	private Menu menuQuitter() {
		Menu menu = new Menu("Quitter", "q");
		menu.add(quitterEtEnregistrer());
		menu.add(quitterSansEnregistrer());
		menu.addBack("r");
		return menu;
	}

	private Option quitterEtEnregistrer() {
		return new Option("Quitter et enregistrer", "q",
				() ->
				{
					try {
						gestionPersonnel.sauvegarder();
						Action.QUIT.optionSelected();
					} catch (personnel.SauvegardeImpossible e) {
						System.out.println("Impossible d'effectuer la sauvegarde");
					}
				}
		);
	}

	private Option quitterSansEnregistrer() {
		return new Option("Quitter sans enregistrer", "a", Action.QUIT);
	}

	public boolean verifiePassword() {
		boolean ok = gestionPersonnel.getRoot().checkPassword(getString("password : "));
		if (!ok)
			System.out.println("Password incorrect.");
		return ok;
	}
}
	


package cloudstore.views;

import cloudstore.controllers.analisi.cloudstore.AnalisiCloudStoreController;
import cloudstore.controllers.BasicController;
import cloudstore.controllers.Controller;
import cloudstore.controllers.analisi.utente.AnalisiUtenteController;
import cloudstore.controllers.utente.UtenteController;

import java.util.function.Supplier;

/** The pages of the application. */
public enum Pages {

  /** Home screen. */
  HOME("home", BasicController::new),
  ANALISI_CLOUDSTORE("analisi-cloudstore", AnalisiCloudStoreController::new),
  ANALISI_UTENTE("analisi-utente", AnalisiUtenteController::new),
  UTENTI("utenti", UtenteController::new);

  private final String name;
  private final Supplier<Controller> controllerGenerator;

  Pages(final String name, final Supplier<Controller> controllerGenerator) {
    this.name = name;
    this.controllerGenerator = controllerGenerator;
  }

  public String getName() {
    return this.name;
  }

  public Controller getNewControllerInstance() {
    return this.controllerGenerator.get();
  }
}

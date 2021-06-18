package cloudstore.views.home;

import cloudstore.views.AbstractJavaFXView;
import cloudstore.views.PageLoader;
import cloudstore.views.Pages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HomeView extends AbstractJavaFXView {

  @Override
  public void init() {}

  @FXML
  public void goToDatabaseUtenti(final ActionEvent event) {
    PageLoader.getInstance()
        .switchPage(this.getStage(), Pages.UTENTI, this.getController().getModel());
  }

  @FXML
  public void goToAnalisiCloudStore(final ActionEvent event) {
    PageLoader.getInstance()
            .switchPage(this.getStage(), Pages.ANALISI_CLOUDSTORE, this.getController().getModel());
  }

  @FXML
  public void goToAnalisiUtenti(final ActionEvent event) {
    PageLoader.getInstance()
            .switchPage(this.getStage(), Pages.ANALISI_UTENTE, this.getController().getModel());
  }
}

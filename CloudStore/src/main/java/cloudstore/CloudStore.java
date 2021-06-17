package cloudstore;

import cloudstore.model.ApplicationInstance;
import cloudstore.views.PageLoader;
import cloudstore.views.Pages;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/** The main JavaFX Application. */
public final class CloudStore extends Application {

  public static void main(final String[] args) {
    launch(args);
  }

  @Override
  public void start(final Stage primaryStage) throws IOException {
    PageLoader.getInstance().switchPage(primaryStage, Pages.HOME, new ApplicationInstance());
  }
}

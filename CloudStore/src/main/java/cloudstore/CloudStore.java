package cloudstore;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import cloudstore.model.ApplicationInstance;
import cloudstore.views.pages.PageLoader;
import cloudstore.views.pages.Pages;

/**
 * The main JavaFX Application.
 */
public final class CloudStore extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {
        PageLoader.getInstance().switchPage(primaryStage, Pages.LOADING, new ApplicationInstance());
    }

}

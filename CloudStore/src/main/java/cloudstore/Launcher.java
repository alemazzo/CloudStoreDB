package cloudstore;

import cloudstore.controllers.login.LoginController;
import cloudstore.controllers.login.LoginControllerImpl;
import cloudstore.model.ApplicationInstance;
import cloudstore.model.Model;
import cloudstore.views.login.CommandLineLoginView;

/**
 * The launcher of the application.
 */
public final class Launcher {

    private Launcher() {

    }
    public static void main(final String[] args) {
        CloudStore.main(args);
    }

}

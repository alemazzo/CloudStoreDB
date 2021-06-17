package cloudstore.views.pages;

import java.util.function.Supplier;

import cloudstore.controllers.BasicController;
import cloudstore.controllers.Controller;

/**
 * The pages of the application.
 */
public enum Pages {

    /**
     * Loading screen.
     */
    // LOADING("loading", LoadingControllerImpl::new);
            ;

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

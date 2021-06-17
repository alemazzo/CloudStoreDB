package cloudstore.views;

import cloudstore.controllers.Controller;

/** Basic implementation of the interface View. */
public class BasicView implements View {

  private Controller controller;

  /** {@inheritDoc} */
  @Override
  public final Controller getController() {
    return this.controller;
  }

  /** {@inheritDoc} */
  @Override
  public final void setController(final Controller controller) {
    this.controller = controller;
  }
}

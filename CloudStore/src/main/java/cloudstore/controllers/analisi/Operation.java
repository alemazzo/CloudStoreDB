package cloudstore.controllers.analisi;

import java.util.List;

public class Operation {

  private static final Operation OPERATION_16 = new Operation(16, "Visualizzare l’operatore che ha chiuso più interventi");
  public Integer codice;
  public String operazione;

  private Operation(final Integer codice, final String operazione) {
    this.codice = codice;
    this.operazione = operazione;
  }

  public static List<Operation> operations(){
    return List.of(OPERATION_16);
  }
}

package model;

/**
 * Modelo de Vaga simplificado, sem dependência de JPA.
 * Utilizado com JDBC puro no backend Spark Java.
 */
public class Vaga {

  private Long id;
  private Tipo tipo;
  private String setor;
  private Status status;
  private Carro ocupante;

  public enum Tipo {
    COMUM, PCD, FUNCIONARIO, VIP
  }

  public enum Status {
    LIVRE, OCUPADA, BLOQUEADA
  }

  public Vaga() {
  }

  public Vaga(Long id, Tipo tipo, String setor, Status status, Carro ocupante) {
    this.id = id;
    this.tipo = tipo;
    this.setor = setor;
    this.status = status;
    this.ocupante = ocupante;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Tipo getTipo() {
    return tipo;
  }

  public void setTipo(Tipo tipo) {
    this.tipo = tipo;
  }

  public String getSetor() {
    return setor;
  }

  public void setSetor(String setor) {
    this.setor = setor;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Carro getOcupante() {
    return ocupante;
  }

  public void setOcupante(Carro ocupante) {
    this.ocupante = ocupante;
  }
}

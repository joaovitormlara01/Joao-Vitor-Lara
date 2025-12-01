package model;

/**
 * Modelo de Visitante sem dependência de JPA.
 * Utilizado com JDBC puro no backend Spark Java.
 */
public class Visitante {

  private String cpf;
  private Pessoa pessoa;
  private String documento;

  public Visitante() {
  }

  public Visitante(String cpf, Pessoa pessoa, String documento) {
    this.cpf = cpf;
    this.pessoa = pessoa;
    this.documento = documento;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public Pessoa getPessoa() {
    return pessoa;
  }

  public void setPessoa(Pessoa pessoa) {
    this.pessoa = pessoa;
  }

  public String getDocumento() {
    return documento;
  }

  public void setDocumento(String documento) {
    this.documento = documento;
  }
}

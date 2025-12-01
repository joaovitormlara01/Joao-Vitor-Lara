package model;

public class Pessoa {

  private String cpe; // identificador principal (equivalente ao CPF)
  private String nome;
  private TipoPessoa tipo;

  public enum TipoPessoa {
    USUARIO, VISITANTE
  }

  // Construtor padrão
  public Pessoa() {
  }

  // Construtor completo
  public Pessoa(String cpe, String nome, TipoPessoa tipo) {
    this.cpe = cpe;
    this.nome = nome;
    this.tipo = tipo;
  }

  // Getters e Setters
  public String getCpe() {
    return cpe;
  }

  public void setCpe(String cpe) {
    this.cpe = cpe;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public TipoPessoa getTipo() {
    return tipo;
  }

  public void setTipo(TipoPessoa tipo) {
    this.tipo = tipo;
  }
}

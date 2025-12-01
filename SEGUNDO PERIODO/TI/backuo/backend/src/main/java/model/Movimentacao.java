package model;

import java.time.OffsetDateTime;

/**
 * Modelo de Movimentação sem dependência de JPA.
 * Utilizado com JDBC puro no backend Spark Java.
 */
public class Movimentacao {

  private Long id;
  private String placa;
  private Vaga vaga;
  private OffsetDateTime entrada;
  private OffsetDateTime saida;
  private TipoEvento tipo;

  public enum TipoEvento {
    ENTRADA, SAIDA, AJUSTE
  }

  public Movimentacao() {
  }

  public Movimentacao(Long id, String placa, Vaga vaga, OffsetDateTime entrada, OffsetDateTime saida, TipoEvento tipo) {
    this.id = id;
    this.placa = placa;
    this.vaga = vaga;
    this.entrada = entrada;
    this.saida = saida;
    this.tipo = tipo;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPlaca() {
    return placa;
  }

  public void setPlaca(String placa) {
    this.placa = placa;
  }

  public Vaga getVaga() {
    return vaga;
  }

  public void setVaga(Vaga vaga) {
    this.vaga = vaga;
  }

  public OffsetDateTime getEntrada() {
    return entrada;
  }

  public void setEntrada(OffsetDateTime entrada) {
    this.entrada = entrada;
  }

  public OffsetDateTime getSaida() {
    return saida;
  }

  public void setSaida(OffsetDateTime saida) {
    this.saida = saida;
  }

  public TipoEvento getTipo() {
    return tipo;
  }

  public void setTipo(TipoEvento tipo) {
    this.tipo = tipo;
  }
}

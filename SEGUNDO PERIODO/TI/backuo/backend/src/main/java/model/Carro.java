package model;

public class Carro {

  private String placa;
  private String modelo;
  private Pessoa dono;

  public Carro() {
  }

  public Carro(String placa, String modelo, Pessoa dono) {
    this.placa = placa;
    this.modelo = modelo;
    this.dono = dono;
  }

  public String getPlaca() {
    return placa;
  }

  public void setPlaca(String placa) {
    this.placa = placa;
  }

  public String getModelo() {
    return modelo;
  }

  public void setModelo(String modelo) {
    this.modelo = modelo;
  }

  public Pessoa getDono() {
    return dono;
  }

  public void setDono(Pessoa dono) {
    this.dono = dono;
  }
}

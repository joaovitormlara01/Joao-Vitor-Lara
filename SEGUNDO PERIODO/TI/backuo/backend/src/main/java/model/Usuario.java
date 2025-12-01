package model;

public class Usuario {

  private String id; // substitui a matrícula como chave principal
  private String nome;
  private String email;
  private String senhaHash;
  private Perfil perfil;
  private Pessoa pessoa;

  public enum Perfil {
    ADMIN, ATENDENTE, OPERADOR, PADRAO
  }

  public Usuario() {
  }

  public Usuario(String id, String nome, String email, String senhaHash, Perfil perfil) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.senhaHash = senhaHash;
    this.perfil = perfil;
  }

  // 🔹 Getters e Setters
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenhaHash() {
    return senhaHash;
  }

  public void setSenhaHash(String senhaHash) {
    this.senhaHash = senhaHash;
  }

  public Perfil getPerfil() {
    return perfil;
  }

  public void setPerfil(Perfil perfil) {
    this.perfil = perfil;
  }

  public Pessoa getPessoa() {
    return pessoa;
  }

  public void setPessoa(Pessoa pessoa) {
    this.pessoa = pessoa;
  }
}

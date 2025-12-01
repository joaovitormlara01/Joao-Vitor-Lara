package dao;

import model.Usuario;
import model.Pessoa;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.*;

/**
 * DAO de Usuario com JDBC puro — compatível com Spark Java e autenticação via
 * BCrypt.
 * Agora alinhado ao modelo com campo "id" em vez de "matricula".
 */
public class UsuarioDAO {

  private static final String URL = "jdbc:postgresql://dpg-d41rv13uibrs73flsed0-a.oregon-postgres.render.com/unipark_db";
  private static final String USER = "unipark_db_user";
  private static final String PASS = "PrM6UMSTfuATWxpC1emUhlAi8dci5ZJr";

  private Connection conectar() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASS);
  }

  // 🔹 Inserir usuário
  public Usuario insert(Usuario u) throws SQLException {
    String sql = "INSERT INTO usuario (id, nome, email, senhahash, perfil) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, u.getId());
      stmt.setString(2, u.getNome());
      stmt.setString(3, u.getEmail());
      stmt.setString(4, u.getSenhaHash());
      stmt.setString(5, u.getPerfil().name());
      stmt.executeUpdate();
      return u;
    }
  }

  // 🔹 Atualizar usuário
  public Usuario update(Usuario u) throws SQLException {
    String sql = "UPDATE usuario SET nome=?, email=?, senhahash=?, perfil=? WHERE id=?";
    try (Connection conn = conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, u.getNome());
      stmt.setString(2, u.getEmail());
      stmt.setString(3, u.getSenhaHash());
      stmt.setString(4, u.getPerfil().name());
      stmt.setString(5, u.getId());
      stmt.executeUpdate();
      return u;
    }
  }

  // 🔹 Remover usuário
  public void remove(String id) throws SQLException {
    String sql = "DELETE FROM usuario WHERE id=?";
    try (Connection conn = conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, id);
      stmt.executeUpdate();
    }
  }

  // 🔹 Buscar por ID
  public Optional<Usuario> get(String id) throws SQLException {
    String sql = "SELECT * FROM usuario WHERE id=?";
    try (Connection conn = conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return Optional.of(mapUsuario(rs));
      }
    }
    return Optional.empty();
  }

  // 🔹 Listar todos
  public List<Usuario> listar() throws SQLException {
    List<Usuario> lista = new ArrayList<>();
    String sql = "SELECT * FROM usuario ORDER BY nome ASC";
    try (Connection conn = conectar();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
      while (rs.next()) {
        lista.add(mapUsuario(rs));
      }
    }
    return lista;
  }

  // 🔹 Autenticar usuário (login)
  public Optional<Usuario> autenticar(String email, String senha) throws SQLException {
    String sql = "SELECT * FROM usuario WHERE email = ?";
    try (Connection conn = conectar();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, email);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        String hash = rs.getString("senhahash");
        if (hash != null && BCrypt.checkpw(senha, hash)) {
          Usuario u = mapUsuario(rs);
          return Optional.of(u);
        }
      }
    }
    return Optional.empty();
  }

  // 🔹 Mapeia ResultSet → Usuario
  private Usuario mapUsuario(ResultSet rs) throws SQLException {
    Usuario u = new Usuario();
    u.setId(rs.getString("id"));
    u.setNome(rs.getString("nome"));
    u.setEmail(rs.getString("email"));
    u.setSenhaHash(rs.getString("senhahash"));
    u.setPerfil(Usuario.Perfil.valueOf(rs.getString("perfil")));
    return u;
  }
}

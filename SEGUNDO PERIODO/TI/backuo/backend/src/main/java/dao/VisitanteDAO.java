package dao;

import model.Visitante;
import model.Pessoa;
import java.sql.*;
import java.util.*;

/**
 * DAO de Visitante — versão JDBC puro.
 * Compatível com o modelo atual (cpf, pessoa, documento).
 */
public class VisitanteDAO {

  // 🔹 Conexão com banco (Azure PostgreSQL)
  private Connection conectarBanco() throws SQLException {
    String url = "jdbc:postgresql://dpg-d41rv13uibrs73flsed0-a.oregon-postgres.render.com/unipark_db";
    String usuario = "unipark_db_user";
    String senha = "PrM6UMSTfuATWxpC1emUhlAi8dci5ZJr";
    return DriverManager.getConnection(url, usuario, senha);
  }

  // 🔹 Inserir novo visitante
  public Visitante insert(Visitante v) throws SQLException {
    String sql = "INSERT INTO visitante (cpf, documento) VALUES (?, ?)";
    try (Connection conn = conectarBanco();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, v.getCpf());
      stmt.setString(2, v.getDocumento());
      stmt.executeUpdate();
      return v;
    }
  }

  // 🔹 Atualizar visitante
  public Visitante update(Visitante v) throws SQLException {
    String sql = "UPDATE visitante SET documento=? WHERE cpf=?";
    try (Connection conn = conectarBanco();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, v.getDocumento());
      stmt.setString(2, v.getCpf());
      stmt.executeUpdate();
      return v;
    }
  }

  // 🔹 Remover visitante
  public void remove(String cpf) throws SQLException {
    String sql = "DELETE FROM visitante WHERE cpf=?";
    try (Connection conn = conectarBanco();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, cpf);
      stmt.executeUpdate();
    }
  }

  // 🔹 Buscar visitante por CPF
  public Optional<Visitante> get(String cpf) throws SQLException {
    String sql = """
          SELECT v.cpf, v.documento, p.nome
          FROM visitante v
          JOIN pessoa p ON v.cpf = p.cpe
          WHERE v.cpf = ?
        """;
    try (Connection conn = conectarBanco();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, cpf);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return Optional.of(mapVisitante(rs));
      }
    }
    return Optional.empty();
  }

  // 🔹 Listar todos os visitantes
  public List<Visitante> listar() throws SQLException {
    List<Visitante> lista = new ArrayList<>();
    String sql = """
          SELECT v.cpf, v.documento, p.nome
          FROM visitante v
          JOIN pessoa p ON v.cpf = p.cpe
          ORDER BY p.nome ASC
        """;
    try (Connection conn = conectarBanco();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
      while (rs.next()) {
        lista.add(mapVisitante(rs));
      }
    }
    return lista;
  }

  // 🔹 Mapear ResultSet → Visitante
  private Visitante mapVisitante(ResultSet rs) throws SQLException {
    Visitante v = new Visitante();
    Pessoa p = new Pessoa();

    p.setCpe(rs.getString("cpf"));
    p.setNome(rs.getString("nome"));

    v.setCpf(rs.getString("cpf"));
    v.setPessoa(p);
    v.setDocumento(rs.getString("documento"));
    return v;
  }
}

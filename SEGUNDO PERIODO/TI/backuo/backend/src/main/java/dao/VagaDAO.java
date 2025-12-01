package dao;

import model.Vaga;
import model.Carro;
import java.sql.*;
import java.util.*;

/**
 * DAO de Vaga — versão JDBC puro.
 * Compatível com o modelo que usa Carro como ocupante.
 */
public class VagaDAO {

  // 🔹 Conexão com banco (Azure PostgreSQL)
  private Connection conectarBanco() throws SQLException {
    String url = "jdbc:postgresql://dpg-d41rv13uibrs73flsed0-a.oregon-postgres.render.com/unipark_db";
    String usuario = "unipark_db_user";
    String senha = "PrM6UMSTfuATWxpC1emUhlAi8dci5ZJr";
    return DriverManager.getConnection(url, usuario, senha);
  }

  // 🔹 Inserir nova vaga
  public Vaga insert(Vaga v) throws SQLException {
    String sql = "INSERT INTO vaga (tipo, setor, status, placa_ocupante) VALUES (?, ?, ?, ?) RETURNING id";
    try (Connection conn = conectarBanco();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, v.getTipo().name());
      stmt.setString(2, v.getSetor());
      stmt.setString(3, v.getStatus().name());
      stmt.setString(4, v.getOcupante() != null ? v.getOcupante().getPlaca() : null);

      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        v.setId(rs.getLong("id"));
      }
      return v;
    }
  }

  // 🔹 Atualizar vaga existente
  public Vaga update(Vaga v) throws SQLException {
    String sql = "UPDATE vaga SET tipo=?, setor=?, status=?, placa_ocupante=? WHERE id=?";
    try (Connection conn = conectarBanco();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, v.getTipo().name());
      stmt.setString(2, v.getSetor());
      stmt.setString(3, v.getStatus().name());
      stmt.setString(4, v.getOcupante() != null ? v.getOcupante().getPlaca() : null);
      stmt.setLong(5, v.getId());
      stmt.executeUpdate();
      return v;
    }
  }

  // 🔹 Remover vaga
  public void remove(Long id) throws SQLException {
    String sql = "DELETE FROM vaga WHERE id=?";
    try (Connection conn = conectarBanco();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setLong(1, id);
      stmt.executeUpdate();
    }
  }

  // 🔹 Buscar vaga por ID
  public Optional<Vaga> get(Long id) throws SQLException {
    String sql = "SELECT * FROM vaga WHERE id=?";
    try (Connection conn = conectarBanco();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setLong(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return Optional.of(mapVaga(rs));
      }
    }
    return Optional.empty();
  }

  // 🔹 Listar todas as vagas
  public List<Vaga> listar() throws SQLException {
    List<Vaga> lista = new ArrayList<>();
    String sql = "SELECT * FROM vaga ORDER BY id ASC";
    try (Connection conn = conectarBanco();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
      while (rs.next()) {
        lista.add(mapVaga(rs));
      }
    }
    return lista;
  }

  // 🔹 Contar todas as vagas
  public long count() throws SQLException {
    String sql = "SELECT COUNT(*) FROM vaga";
    try (Connection conn = conectarBanco();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
      return rs.next() ? rs.getLong(1) : 0;
    }
  }

  // 🔹 Contar vagas por status (LIVRE / OCUPADA)
  public long contarPorStatus(Vaga.Status s) throws SQLException {
    String sql = "SELECT COUNT(*) FROM vaga WHERE status = ?";
    try (Connection conn = conectarBanco();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, s.name());
      ResultSet rs = stmt.executeQuery();
      return rs.next() ? rs.getLong(1) : 0;
    }
  }

  // 🔹 Contar vagas por tipo (COMUM / PCD / FUNCIONARIO / VIP)
  public long contarPorTipo(Vaga.Tipo t) throws SQLException {
    String sql = "SELECT COUNT(*) FROM vaga WHERE tipo = ?";
    try (Connection conn = conectarBanco();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, t.name());
      ResultSet rs = stmt.executeQuery();
      return rs.next() ? rs.getLong(1) : 0;
    }
  }

  // 🔹 Mapear ResultSet → Vaga
  private Vaga mapVaga(ResultSet rs) throws SQLException {
    Vaga v = new Vaga();
    v.setId(rs.getLong("id"));
    v.setSetor(rs.getString("setor"));
    v.setTipo(Vaga.Tipo.valueOf(rs.getString("tipo")));
    v.setStatus(Vaga.Status.valueOf(rs.getString("status")));

    String placa = rs.getString("placa_ocupante");
    if (placa != null) {
      Carro c = new Carro();
      c.setPlaca(placa);
      v.setOcupante(c);
    }

    return v;
  }
}

package dao;

import model.Movimentacao;
import model.Vaga;
import java.sql.*;
import java.time.OffsetDateTime;
import java.util.*;

/**
 * DAO de Movimentacao (versão JDBC puro).
 * Compatível com o modelo atual que usa entrada, saída e tipo.
 */
public class MovimentacaoDAO {

  // 🔹 Conexão com o banco (Azure PostgreSQL)
  private Connection conectarBanco() throws SQLException {
    String url = "jdbc:postgresql://dpg-d41rv13uibrs73flsed0-a.oregon-postgres.render.com/unipark_db";
    String usuario = "unipark_db_user";
    String senha = "PrM6UMSTfuATWxpC1emUhlAi8dci5ZJr";
    return DriverManager.getConnection(url, usuario, senha);
  }

  // 🔹 Inserir nova movimentação
  public Movimentacao insert(Movimentacao m) throws SQLException {
    String sql = "INSERT INTO movimentacao (placa, vaga_id, entrada, saida, tipo) VALUES (?, ?, ?, ?, ?) RETURNING id";
    try (Connection conn = conectarBanco();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, m.getPlaca());
      stmt.setLong(2, m.getVaga() != null ? m.getVaga().getId() : null);
      stmt.setObject(3, m.getEntrada());
      stmt.setObject(4, m.getSaida());
      stmt.setString(5, m.getTipo().name());

      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        m.setId(rs.getLong("id"));
      }
      return m;
    }
  }

  // 🔹 Atualizar movimentação
  public Movimentacao update(Movimentacao m) throws SQLException {
    String sql = "UPDATE movimentacao SET placa=?, vaga_id=?, entrada=?, saida=?, tipo=? WHERE id=?";
    try (Connection conn = conectarBanco();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, m.getPlaca());
      stmt.setLong(2, m.getVaga() != null ? m.getVaga().getId() : null);
      stmt.setObject(3, m.getEntrada());
      stmt.setObject(4, m.getSaida());
      stmt.setString(5, m.getTipo().name());
      stmt.setLong(6, m.getId());

      stmt.executeUpdate();
      return m;
    }
  }

  // 🔹 Remover movimentação
  public void remove(Long id) throws SQLException {
    String sql = "DELETE FROM movimentacao WHERE id=?";
    try (Connection conn = conectarBanco();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setLong(1, id);
      stmt.executeUpdate();
    }
  }

  // 🔹 Buscar por ID
  public Optional<Movimentacao> get(Long id) throws SQLException {
    String sql = "SELECT * FROM movimentacao WHERE id=?";
    try (Connection conn = conectarBanco();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setLong(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return Optional.of(mapMovimentacao(rs));
      }
    }
    return Optional.empty();
  }

  // 🔹 Listar todas
  public List<Movimentacao> listar() throws SQLException {
    List<Movimentacao> lista = new ArrayList<>();
    String sql = "SELECT * FROM movimentacao ORDER BY id ASC";
    try (Connection conn = conectarBanco();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
      while (rs.next()) {
        lista.add(mapMovimentacao(rs));
      }
    }
    return lista;
  }

  // 🔹 Listar as 20 mais recentes
  public List<Movimentacao> listarRecentes() throws SQLException {
    List<Movimentacao> lista = new ArrayList<>();
    String sql = "SELECT * FROM movimentacao ORDER BY id DESC LIMIT 20";
    try (Connection conn = conectarBanco();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
      while (rs.next()) {
        lista.add(mapMovimentacao(rs));
      }
    }
    return lista;
  }

  // 🔹 Mapear ResultSet → Movimentacao
  private Movimentacao mapMovimentacao(ResultSet rs) throws SQLException {
    Movimentacao m = new Movimentacao();
    m.setId(rs.getLong("id"));
    m.setPlaca(rs.getString("placa"));

    // Vaga associada (somente ID)
    Vaga v = new Vaga();
    v.setId(rs.getLong("vaga_id"));
    m.setVaga(v);

    Timestamp entradaTs = rs.getTimestamp("entrada");
    Timestamp saidaTs = rs.getTimestamp("saida");
    m.setEntrada(entradaTs != null ? entradaTs.toLocalDateTime().atOffset(OffsetDateTime.now().getOffset()) : null);
    m.setSaida(saidaTs != null ? saidaTs.toLocalDateTime().atOffset(OffsetDateTime.now().getOffset()) : null);

    m.setTipo(Movimentacao.TipoEvento.valueOf(rs.getString("tipo")));
    return m;
  }
}

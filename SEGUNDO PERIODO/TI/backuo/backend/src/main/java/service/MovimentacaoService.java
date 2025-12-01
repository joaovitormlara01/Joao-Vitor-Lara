package service;

import dao.MovimentacaoDAO;
import model.Movimentacao;
import model.Vaga;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Serviço de Movimentação sem dependência do Spring.
 * Utiliza o MovimentacaoDAO para operações no banco via JDBC.
 */
public class MovimentacaoService {

  private final MovimentacaoDAO dao;

  public MovimentacaoService() {
    this.dao = new MovimentacaoDAO();
  }

  public Movimentacao insert(Movimentacao m) {
    try {
      return dao.insert(m);
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao inserir movimentação: " + e.getMessage(), e);
    }
  }

  public Movimentacao get(Long id) {
    try {
      return dao.get(id).orElseThrow(() -> new RuntimeException("Movimentação não encontrada"));
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao buscar movimentação: " + e.getMessage(), e);
    }
  }

  public List<Movimentacao> listar() {
    try {
      return dao.listar();
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao listar movimentações: " + e.getMessage(), e);
    }
  }

  public void remove(Long id) {
    try {
      dao.remove(id);
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao remover movimentação: " + e.getMessage(), e);
    }
  }

  public void registrarEntrada(String placa, Vaga v) {
    try {
      Movimentacao m = new Movimentacao(
          null,
          placa,
          v,
          OffsetDateTime.now(),
          null,
          Movimentacao.TipoEvento.ENTRADA);
      dao.insert(m);
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao registrar entrada: " + e.getMessage(), e);
    }
  }

  public void registrarSaida(Vaga v) {
    try {
      String placa = (v.getOcupante() != null) ? v.getOcupante().getPlaca() : null;
      Movimentacao m = new Movimentacao(
          null,
          placa,
          v,
          null,
          OffsetDateTime.now(),
          Movimentacao.TipoEvento.SAIDA);
      dao.insert(m);
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao registrar saída: " + e.getMessage(), e);
    }
  }
}

package service;

import dao.VagaDAO;
import model.Vaga;
import java.sql.SQLException;
import java.util.List;

/**
 * Serviço de Vaga sem dependência do Spring.
 * Utiliza VagaDAO e MovimentacaoService com JDBC puro.
 */
public class VagaService {

  private final VagaDAO dao;
  private final MovimentacaoService movs;

  public VagaService() {
    this.dao = new VagaDAO();
    this.movs = new MovimentacaoService();
  }

  // 🔹 Inserir nova vaga
  public Vaga insert(Vaga v) {
    try {
      return dao.insert(v);
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao inserir vaga: " + e.getMessage(), e);
    }
  }

  // 🔹 Atualizar vaga existente
  public Vaga update(Long id, Vaga v) {
    try {
      v.setId(id);
      return dao.update(v);
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao atualizar vaga: " + e.getMessage(), e);
    }
  }

  // 🔹 Remover vaga
  public void remove(Long id) {
    try {
      dao.remove(id);
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao remover vaga: " + e.getMessage(), e);
    }
  }

  // 🔹 Buscar vaga por ID
  public Vaga get(Long id) {
    try {
      return dao.get(id).orElseThrow(() -> new RuntimeException("Vaga não encontrada"));
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao buscar vaga: " + e.getMessage(), e);
    }
  }

  // 🔹 Listar todas as vagas
  public List<Vaga> listar() {
    try {
      return dao.listar();
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao listar vagas: " + e.getMessage(), e);
    }
  }

  // 🔹 Ocupar vaga
  public Vaga ocupar(Long id, String placa) {
    try {
      Vaga v = get(id);
      v.setStatus(Vaga.Status.OCUPADA);
      movs.registrarEntrada(placa, v);
      return dao.update(v);
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao ocupar vaga: " + e.getMessage(), e);
    }
  }

  // 🔹 Liberar vaga
  public Vaga liberar(Long id) {
    try {
      Vaga v = get(id);
      v.setStatus(Vaga.Status.LIVRE);
      movs.registrarSaida(v);
      v.setOcupante(null);
      return dao.update(v);
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao liberar vaga: " + e.getMessage(), e);
    }
  }

  // 🔹 Estrutura de resumo das vagas
  public record Resumo(long total, long livres, long ocupadas, long especiais) {
  }

  // 🔹 Gerar resumo de vagas
  public Resumo resumo() {
    try {
      long total = dao.count();
      long ocupadas = dao.contarPorStatus(Vaga.Status.OCUPADA);
      long livres = dao.contarPorStatus(Vaga.Status.LIVRE);
      long especiais = dao.contarPorTipo(Vaga.Tipo.PCD)
          + dao.contarPorTipo(Vaga.Tipo.FUNCIONARIO)
          + dao.contarPorTipo(Vaga.Tipo.VIP);
      return new Resumo(total, livres, ocupadas, especiais);
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao gerar resumo: " + e.getMessage(), e);
    }
  }
}

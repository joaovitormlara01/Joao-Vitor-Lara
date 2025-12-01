package service;

import dao.VisitanteDAO;
import model.Visitante;
import java.sql.SQLException;
import java.util.List;

/**
 * Serviço de Visitante sem dependência do Spring.
 * Utiliza VisitanteDAO para operações via JDBC puro.
 */
public class VisitanteService {

  private final VisitanteDAO dao;

  public VisitanteService() {
    this.dao = new VisitanteDAO();
  }

  // 🔹 Inserir novo visitante
  public Visitante insert(Visitante v) {
    try {
      return dao.insert(v);
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao inserir visitante: " + e.getMessage(), e);
    }
  }

  // 🔹 Atualizar visitante existente
  public Visitante update(String id, Visitante v) {
    try {
      v.setCpf(id);
      return dao.update(v);
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao atualizar visitante: " + e.getMessage(), e);
    }
  }

  // 🔹 Remover visitante
  public void remove(String id) {
    try {
      dao.remove(id);
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao remover visitante: " + e.getMessage(), e);
    }
  }

  // 🔹 Buscar visitante por ID
  public Visitante get(String id) {
    try {
      return dao.get(id).orElseThrow(() -> new RuntimeException("Visitante não encontrado"));
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao buscar visitante: " + e.getMessage(), e);
    }
  }

  // 🔹 Listar todos os visitantes
  public List<Visitante> listar() {
    try {
      return dao.listar();
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao listar visitantes: " + e.getMessage(), e);
    }
  }
}

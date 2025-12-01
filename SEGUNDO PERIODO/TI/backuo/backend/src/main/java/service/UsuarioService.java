package service;

import dao.UsuarioDAO;
import model.Usuario;
import java.sql.SQLException;
import java.util.List;

/**
 * Serviço de Usuário sem dependência do Spring.
 * Utiliza o UsuarioDAO via JDBC puro.
 */
public class UsuarioService {

  private final UsuarioDAO dao;

  public UsuarioService() {
    this.dao = new UsuarioDAO();
  }

  // 🔹 Inserir novo usuário
  public Usuario insert(Usuario u) {
    try {
      return dao.insert(u);
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao inserir usuário: " + e.getMessage(), e);
    }
  }

  // 🔹 Atualizar usuário existente
  public Usuario update(String id, Usuario u) {
    try {
      u.setId(id); // ✅ substitui o antigo setMatricula()
      return dao.update(u);
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage(), e);
    }
  }

  // 🔹 Remover usuário
  public void remove(String id) {
    try {
      dao.remove(id);
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao remover usuário: " + e.getMessage(), e);
    }
  }

  // 🔹 Buscar usuário por ID
  public Usuario get(String id) {
    try {
      return dao.get(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao buscar usuário: " + e.getMessage(), e);
    }
  }

  // 🔹 Listar todos os usuários
  public List<Usuario> listar() {
    try {
      return dao.listar();
    } catch (SQLException e) {
      throw new RuntimeException("Erro ao listar usuários: " + e.getMessage(), e);
    }
  }
}

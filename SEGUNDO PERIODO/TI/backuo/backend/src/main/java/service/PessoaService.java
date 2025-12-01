package service;

import dao.PessoaDAO;
import model.Pessoa;
import java.sql.SQLException;
import java.util.List;

/**
 * Serviço de Pessoa sem dependência de Spring.
 * Utiliza o PessoaDAO para operações JDBC.
 */
public class PessoaService {

    private final PessoaDAO dao;

    public PessoaService() {
        this.dao = new PessoaDAO();
    }

    // 🔹 Inserir pessoa
    public boolean insert(Pessoa p) {
        try {
            return dao.insert(p);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir pessoa: " + e.getMessage(), e);
        }
    }

    // 🔹 Listar todas as pessoas
    public List<Pessoa> findAll() {
        try {
            return dao.listar();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pessoas: " + e.getMessage(), e);
        }
    }

    // 🔹 Buscar pessoa por CPE
    public Pessoa findById(String cpe) {
        try {
            return dao.get(cpe).orElse(null);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pessoa: " + e.getMessage(), e);
        }
    }

    // 🔹 Deletar pessoa
    public void delete(String cpe) {
        try {
            dao.remove(cpe);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar pessoa: " + e.getMessage(), e);
        }
    }
}

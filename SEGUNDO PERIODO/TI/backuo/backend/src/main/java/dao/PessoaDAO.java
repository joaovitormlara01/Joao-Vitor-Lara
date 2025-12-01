package dao;

import model.Pessoa;
import java.sql.*;
import java.util.*;

/**
 * DAO de Pessoa — versão JDBC puro.
 * Conecta diretamente ao banco PostgreSQL (Azure).
 */
public class PessoaDAO {

    // 🔹 Configurações de conexão com o banco
    private static final String URL = "jdbc:postgresql://dpg-d41rv13uibrs73flsed0-a.oregon-postgres.render.com/unipark_db";
    private static final String USER = "unipark_db_user";
    private static final String PASS = "PrM6UMSTfuATWxpC1emUhlAi8dci5ZJr";

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // 🔹 Inserir nova pessoa
    public boolean insert(Pessoa p) throws SQLException {
        String sql = "INSERT INTO pessoa (cpe, nome, tipo) VALUES (?, ?, ?)";
        try (Connection conn = conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getCpe());
            stmt.setString(2, p.getNome());
            stmt.setString(3, p.getTipo().name());
            return stmt.executeUpdate() > 0;
        }
    }

    // 🔹 Atualizar pessoa
    public boolean update(Pessoa p) throws SQLException {
        String sql = "UPDATE pessoa SET nome = ?, tipo = ? WHERE cpe = ?";
        try (Connection conn = conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getTipo().name());
            stmt.setString(3, p.getCpe());
            return stmt.executeUpdate() > 0;
        }
    }

    // 🔹 Remover pessoa
    public boolean remove(String cpe) throws SQLException {
        String sql = "DELETE FROM pessoa WHERE cpe = ?";
        try (Connection conn = conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpe);
            return stmt.executeUpdate() > 0;
        }
    }

    // 🔹 Buscar por CPE
    public Optional<Pessoa> get(String cpe) throws SQLException {
        String sql = "SELECT cpe, nome, tipo FROM pessoa WHERE cpe = ?";
        try (Connection conn = conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpe);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapPessoa(rs));
                }
            }
        }
        return Optional.empty();
    }

    // 🔹 Listar todas as pessoas
    public List<Pessoa> listar() throws SQLException {
        List<Pessoa> lista = new ArrayList<>();
        String sql = "SELECT cpe, nome, tipo FROM pessoa ORDER BY nome ASC";
        try (Connection conn = conectar();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapPessoa(rs));
            }
        }
        return lista;
    }

    // 🔹 Mapeia uma linha do ResultSet para o objeto Pessoa
    private Pessoa mapPessoa(ResultSet rs) throws SQLException {
        Pessoa p = new Pessoa();
        p.setCpe(rs.getString("cpe"));
        p.setNome(rs.getString("nome"));
        p.setTipo(Pessoa.TipoPessoa.valueOf(rs.getString("tipo")));
        return p;
    }
}

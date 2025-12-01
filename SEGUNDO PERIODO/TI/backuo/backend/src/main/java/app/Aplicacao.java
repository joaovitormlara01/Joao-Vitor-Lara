package app;

import static spark.Spark.*;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Aplicacao {

  // 🔹 Conexão com o banco de dados Azure PostgreSQL
  private static Connection conectarBanco() throws SQLException {
    String url = "jdbc:postgresql://dpg-d41rv13uibrs73flsed0-a.oregon-postgres.render.com/unipark_db";
    String usuario = "unipark_db_user";
    String senha = "PrM6UMSTfuATWxpC1emUhlAi8dci5ZJr";
    return DriverManager.getConnection(url, usuario, senha);
  }

  public static void main(String[] args) {

    // 🔹 Define a porta do servidor
    port(8080);

    // 🔹 Habilita CORS (configuração global)
    CorsConfig.habilitarCors();

    // 🔹 Segurança básica (opcional, pode comentar se ainda não quiser tokens)
    // SecurityConfig.aplicarSeguranca();

    // 🔹 Mensagem de inicialização
    System.out.println("=========================================");
    System.out.println("🚀 Servidor Spark iniciado na porta 8080");
    System.out.println("🌐 Acesse: http://localhost:8080/");
    System.out.println("=========================================");

    // 🔹 Rota principal
    get("/", (req, res) -> {
      res.type("application/json; charset=UTF-8");
      return new Gson().toJson("🚗 API UniPark rodando com Spark Java e conectada ao Azure!");
    });

    // 🔹 Teste de conexão com o banco
    get("/testar-conexao", (req, res) -> {
      res.type("application/json");
      try (Connection conn = conectarBanco()) {
        if (conn != null && !conn.isClosed()) {
          return new Gson().toJson("✅ Conexão com o banco Azure bem-sucedida!");
        } else {
          res.status(500);
          return new Gson().toJson("❌ Conexão falhou: conexão nula ou encerrada.");
        }
      } catch (SQLException e) {
        res.status(500);
        return new Gson().toJson("❌ Erro ao conectar ao banco: " + e.getMessage());
      }
    });

    // 🔹 Registro de controladores (rotas principais)
    HomeController.registrarRotas();
    AuthController.registrarRotas();
    DashboardController.registrarRotas();
    MovimentacaoController.registrarRotas();
    UsuariosController.registrarRotas();
    VagasController.registrarRotas();
    VisitantesController.registrarRotas();

    // 🔹 Mensagem final
    System.out.println("✅ Todas as rotas foram registradas com sucesso!");
  }
}

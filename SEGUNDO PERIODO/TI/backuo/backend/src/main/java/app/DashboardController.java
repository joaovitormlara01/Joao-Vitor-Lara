package app;

import static spark.Spark.*;
import com.google.gson.Gson;
import service.VagaService;

/**
 * Controlador do dashboard convertido para Spark Java.
 * Mantém o endpoint /api/dashboard e retorna o resumo das vagas.
 */
public class DashboardController {

  private static final Gson gson = new Gson();
  private static final VagaService vagas = new VagaService();

  public static void registrarRotas() {

    // Rota GET: /api/dashboard
    get("/api/dashboard", (req, res) -> {
      res.type("application/json");
      try {
        var resumo = vagas.resumo(); // chama o serviço
        return gson.toJson(resumo);
      } catch (Exception e) {
        res.status(500);
        return gson.toJson("Erro ao obter resumo: " + e.getMessage());
      }
    });
  }
}

package app;

import static spark.Spark.*;
import com.google.gson.Gson;
import model.Movimentacao;
import service.MovimentacaoService;
import java.util.List;

/**
 * Controlador de movimentações convertido de Spring Boot para Spark Java.
 * Mantém os mesmos endpoints:
 * - GET /api/movimentacao
 * - GET /api/movimentacao/:id
 * - POST /api/movimentacao
 * - DELETE /api/movimentacao/:id
 */
public class MovimentacaoController {

  private static final Gson gson = new Gson();
  private static final MovimentacaoService svc = new MovimentacaoService();

  public static void registrarRotas() {

    // 🔹 Listar todas as movimentações
    get("/api/movimentacao", (req, res) -> {
      res.type("application/json");
      try {
        List<Movimentacao> lista = svc.listar();
        return gson.toJson(lista);
      } catch (Exception e) {
        res.status(500);
        return gson.toJson("Erro ao listar movimentações: " + e.getMessage());
      }
    });

    // 🔹 Obter movimentação por ID
    get("/api/movimentacao/:id", (req, res) -> {
      res.type("application/json");
      try {
        Long id = Long.parseLong(req.params(":id"));
        Movimentacao m = svc.get(id);
        if (m != null) {
          return gson.toJson(m);
        } else {
          res.status(404);
          return gson.toJson("Movimentação não encontrada");
        }
      } catch (Exception e) {
        res.status(500);
        return gson.toJson("Erro ao buscar movimentação: " + e.getMessage());
      }
    });

    // 🔹 Inserir nova movimentação
    post("/api/movimentacao", (req, res) -> {
      res.type("application/json");
      try {
        Movimentacao m = gson.fromJson(req.body(), Movimentacao.class);
        Movimentacao criada = svc.insert(m);
        res.status(201);
        return gson.toJson(criada);
      } catch (Exception e) {
        res.status(500);
        return gson.toJson("Erro ao inserir movimentação: " + e.getMessage());
      }
    });

    // 🔹 Excluir movimentação por ID
    delete("/api/movimentacao/:id", (req, res) -> {
      res.type("application/json");
      try {
        Long id = Long.parseLong(req.params(":id"));
        svc.remove(id);
        res.status(204);
        return "";
      } catch (Exception e) {
        res.status(500);
        return gson.toJson("Erro ao excluir movimentação: " + e.getMessage());
      }
    });
  }
}

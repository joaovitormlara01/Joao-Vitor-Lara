package app;

import static spark.Spark.*;
import com.google.gson.Gson;
import model.Visitante;
import service.VisitanteService;
import java.util.List;

/**
 * Controlador de visitantes convertido de Spring Boot para Spark Java.
 * Endpoints:
 * - GET /api/visitantes
 * - GET /api/visitantes/:id
 * - POST /api/visitantes
 * - PUT /api/visitantes/:id
 * - DELETE /api/visitantes/:id
 */
public class VisitantesController {

  private static final Gson gson = new Gson();
  private static final VisitanteService svc = new VisitanteService();

  public static void registrarRotas() {

    // 🔹 Listar todos os visitantes
    get("/api/visitantes", (req, res) -> {
      res.type("application/json");
      try {
        List<Visitante> visitantes = svc.listar();
        return gson.toJson(visitantes);
      } catch (Exception e) {
        res.status(500);
        return gson.toJson("Erro ao listar visitantes: " + e.getMessage());
      }
    });

    // 🔹 Buscar visitante por ID
    get("/api/visitantes/:id", (req, res) -> {
      res.type("application/json");
      try {
        String id = req.params(":id");
        Visitante v = svc.get(id);
        if (v != null) {
          return gson.toJson(v);
        } else {
          res.status(404);
          return gson.toJson("Visitante não encontrado");
        }
      } catch (Exception e) {
        res.status(500);
        return gson.toJson("Erro ao buscar visitante: " + e.getMessage());
      }
    });

    // 🔹 Criar novo visitante
    post("/api/visitantes", (req, res) -> {
      res.type("application/json");
      try {
        Visitante novo = gson.fromJson(req.body(), Visitante.class);
        Visitante criado = svc.insert(novo);
        res.status(201);
        return gson.toJson(criado);
      } catch (Exception e) {
        res.status(500);
        return gson.toJson("Erro ao inserir visitante: " + e.getMessage());
      }
    });

    // 🔹 Atualizar visitante existente
    put("/api/visitantes/:id", (req, res) -> {
      res.type("application/json");
      try {
        String id = req.params(":id");
        Visitante atualizado = gson.fromJson(req.body(), Visitante.class);
        Visitante v = svc.update(id, atualizado);
        if (v != null) {
          return gson.toJson(v);
        } else {
          res.status(404);
          return gson.toJson("Visitante não encontrado para atualização");
        }
      } catch (Exception e) {
        res.status(500);
        return gson.toJson("Erro ao atualizar visitante: " + e.getMessage());
      }
    });

    // 🔹 Remover visitante
    delete("/api/visitantes/:id", (req, res) -> {
      res.type("application/json");
      try {
        String id = req.params(":id");
        svc.remove(id);
        res.status(204);
        return "";
      } catch (Exception e) {
        res.status(500);
        return gson.toJson("Erro ao excluir visitante: " + e.getMessage());
      }
    });
  }
}

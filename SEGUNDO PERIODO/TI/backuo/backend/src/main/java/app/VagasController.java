package app;

import static spark.Spark.*;
import com.google.gson.Gson;
import model.Vaga;
import service.VagaService;
import java.util.List;

/**
 * Controlador de vagas convertido de Spring Boot para Spark Java.
 * Mantém todos os endpoints originais:
 * - GET /api/vagas
 * - GET /api/vagas/:id
 * - POST /api/vagas
 * - PUT /api/vagas/:id
 * - DELETE /api/vagas/:id
 * - POST /api/vagas/:id/ocupar/:placa
 * - POST /api/vagas/:id/liberar
 */
public class VagasController {

  private static final Gson gson = new Gson();
  private static final VagaService svc = new VagaService();

  public static void registrarRotas() {

    // 🔹 Listar todas as vagas
    get("/api/vagas", (req, res) -> {
      res.type("application/json");
      try {
        List<Vaga> vagas = svc.listar();
        return gson.toJson(vagas);
      } catch (Exception e) {
        res.status(500);
        return gson.toJson("Erro ao listar vagas: " + e.getMessage());
      }
    });

    // 🔹 Buscar vaga por ID
    get("/api/vagas/:id", (req, res) -> {
      res.type("application/json");
      try {
        Long id = Long.parseLong(req.params(":id"));
        Vaga v = svc.get(id);
        if (v != null) {
          return gson.toJson(v);
        } else {
          res.status(404);
          return gson.toJson("Vaga não encontrada");
        }
      } catch (Exception e) {
        res.status(500);
        return gson.toJson("Erro ao buscar vaga: " + e.getMessage());
      }
    });

    // 🔹 Criar nova vaga
    post("/api/vagas", (req, res) -> {
      res.type("application/json");
      try {
        Vaga nova = gson.fromJson(req.body(), Vaga.class);
        Vaga criada = svc.insert(nova);
        res.status(201);
        return gson.toJson(criada);
      } catch (Exception e) {
        res.status(500);
        return gson.toJson("Erro ao criar vaga: " + e.getMessage());
      }
    });

    // 🔹 Atualizar vaga existente
    put("/api/vagas/:id", (req, res) -> {
      res.type("application/json");
      try {
        Long id = Long.parseLong(req.params(":id"));
        Vaga atualizada = gson.fromJson(req.body(), Vaga.class);
        Vaga v = svc.update(id, atualizada);
        if (v != null) {
          return gson.toJson(v);
        } else {
          res.status(404);
          return gson.toJson("Vaga não encontrada para atualização");
        }
      } catch (Exception e) {
        res.status(500);
        return gson.toJson("Erro ao atualizar vaga: " + e.getMessage());
      }
    });

    // 🔹 Excluir vaga
    delete("/api/vagas/:id", (req, res) -> {
      res.type("application/json");
      try {
        Long id = Long.parseLong(req.params(":id"));
        svc.remove(id);
        res.status(204);
        return "";
      } catch (Exception e) {
        res.status(500);
        return gson.toJson("Erro ao excluir vaga: " + e.getMessage());
      }
    });

    // 🔹 Ocupar vaga
    post("/api/vagas/:id/ocupar/:placa", (req, res) -> {
      res.type("application/json");
      try {
        Long id = Long.parseLong(req.params(":id"));
        String placa = req.params(":placa");
        Vaga v = svc.ocupar(id, placa);
        return gson.toJson(v);
      } catch (Exception e) {
        res.status(500);
        return gson.toJson("Erro ao ocupar vaga: " + e.getMessage());
      }
    });

    // 🔹 Liberar vaga
    post("/api/vagas/:id/liberar", (req, res) -> {
      res.type("application/json");
      try {
        Long id = Long.parseLong(req.params(":id"));
        Vaga v = svc.liberar(id);
        return gson.toJson(v);
      } catch (Exception e) {
        res.status(500);
        return gson.toJson("Erro ao liberar vaga: " + e.getMessage());
      }
    });
  }
}

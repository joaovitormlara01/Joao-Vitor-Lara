package app;

import static spark.Spark.*;
import com.google.gson.Gson;
import model.Usuario;
import service.UsuarioService;
import java.util.List;

/**
 * Controlador de usuários convertido de Spring Boot para Spark Java.
 * Endpoints:
 * - GET /api/usuarios
 * - GET /api/usuarios/:id
 * - POST /api/usuarios
 * - PUT /api/usuarios/:id
 * - DELETE /api/usuarios/:id
 */
public class UsuariosController {

  private static final Gson gson = new Gson();
  private static final UsuarioService svc = new UsuarioService();

  public static void registrarRotas() {

    // 🔹 Listar todos os usuários
    get("/api/usuarios", (req, res) -> {
      res.type("application/json");
      try {
        List<Usuario> usuarios = svc.listar();
        return gson.toJson(usuarios);
      } catch (Exception e) {
        res.status(500);
        return gson.toJson("Erro ao listar usuários: " + e.getMessage());
      }
    });

    // 🔹 Buscar usuário por ID
    get("/api/usuarios/:id", (req, res) -> {
      res.type("application/json");
      try {
        String id = req.params(":id");
        Usuario u = svc.get(id);
        if (u != null) {
          return gson.toJson(u);
        } else {
          res.status(404);
          return gson.toJson("Usuário não encontrado");
        }
      } catch (Exception e) {
        res.status(500);
        return gson.toJson("Erro ao buscar usuário: " + e.getMessage());
      }
    });

    // 🔹 Inserir novo usuário
    post("/api/usuarios", (req, res) -> {
      res.type("application/json");
      try {
        Usuario novo = gson.fromJson(req.body(), Usuario.class);
        Usuario criado = svc.insert(novo);
        res.status(201);
        return gson.toJson(criado);
      } catch (Exception e) {
        res.status(500);
        return gson.toJson("Erro ao inserir usuário: " + e.getMessage());
      }
    });

    // 🔹 Atualizar usuário existente
    put("/api/usuarios/:id", (req, res) -> {
      res.type("application/json");
      try {
        String id = req.params(":id");
        Usuario atualizado = gson.fromJson(req.body(), Usuario.class);
        Usuario u = svc.update(id, atualizado);
        if (u != null) {
          return gson.toJson(u);
        } else {
          res.status(404);
          return gson.toJson("Usuário não encontrado para atualização");
        }
      } catch (Exception e) {
        res.status(500);
        return gson.toJson("Erro ao atualizar usuário: " + e.getMessage());
      }
    });

    // 🔹 Remover usuário
    delete("/api/usuarios/:id", (req, res) -> {
      res.type("application/json");
      try {
        String id = req.params(":id");
        svc.remove(id);
        res.status(204);
        return "";
      } catch (Exception e) {
        res.status(500);
        return gson.toJson("Erro ao excluir usuário: " + e.getMessage());
      }
    });
  }
}

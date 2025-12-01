package app;

import static spark.Spark.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.UsuarioDAO;
import model.Usuario;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class AuthController {

  private static final Gson gson = new Gson();
  private static final UsuarioDAO usuarios = new UsuarioDAO();

  public static void registrarRotas() {

    post("/login", (req, res) -> {
      res.type("application/json");

      try {
        // ✅ Converte o corpo JSON em mapa tipado corretamente
        Map<String, String> dados = gson.fromJson(
            req.body(),
            new TypeToken<Map<String, String>>() {
            }.getType());

        String email = dados.get("email");
        String senha = dados.get("senha");

        // ✅ Autentica o usuário via DAO (usa método atualizado)
        Optional<Usuario> usuarioOpt = usuarios.autenticar(email, senha);

        if (usuarioOpt.isPresent()) {
          Usuario u = usuarioOpt.get();
          String token = UUID.randomUUID().toString();

          Map<String, String> resposta = new HashMap<>();
          resposta.put("id", u.getId());
          resposta.put("nome", u.getNome());
          resposta.put("email", u.getEmail());
          resposta.put("perfil", u.getPerfil().name());
          resposta.put("token", token);

          res.status(200);
          return gson.toJson(resposta);

        } else {
          res.status(401);
          return gson.toJson(Map.of("error", "Email ou senha incorretos"));
        }

      } catch (Exception e) {
        res.status(500);
        return gson.toJson(Map.of("error", "Erro interno: " + e.getMessage()));
      }
    });
  }
}

package app;

import static spark.Spark.*;

/**
 * Substitui o antigo SecurityConfig do Spring Security.
 * Implementa controle básico de rotas e liberação de endpoints públicos.
 */
public class SecurityConfig {

    public static void aplicarSeguranca() {

        before((req, res) -> {
            String path = req.pathInfo();
            String method = req.requestMethod();

            // ✅ Libera o login e as requisições OPTIONS (CORS preflight)
            if (path.equals("/login") || "OPTIONS".equalsIgnoreCase(method)) {
                return; // permitido
            }

            // ✅ Libera o endpoint raiz
            if (path.equals("/")) {
                return;
            }

            // ⚠️ Exemplo de verificação simples (pode ser substituído por token real)
            String authHeader = req.headers("Authorization");
            if (authHeader == null || authHeader.isEmpty()) {
                halt(401, "Acesso negado: token ausente ou inválido");
            }

            // Aqui você pode validar o token JWT, sessão, ou outro mecanismo no futuro.
        });
    }
}

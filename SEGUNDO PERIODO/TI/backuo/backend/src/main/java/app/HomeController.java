package app;

import static spark.Spark.*;

/**
 * Controlador raiz que substitui o antigo HomeController do Spring Boot.
 * Retorna uma mensagem simples confirmando que o servidor está ativo.
 */
public class HomeController {

    public static void registrarRotas() {

        // Endpoint GET /
        get("/", (req, res) -> {
            res.type("text/plain; charset=UTF-8");
            return "Servidor ativo 🚀 — API UniPark rodando com sucesso!";
        });
    }
}

package app;

import static spark.Spark.*;

/**
 * Configuração global de CORS para o Spark Java.
 * Substitui o antigo CorsConfig do Spring Boot.
 */
public class CorsConfig {

  public static void habilitarCors() {

    // Intercepta todas as requisições antes do processamento
    before((req, res) -> {
      String origem = req.headers("Origin");

      // Permite apenas domínios específicos
      if ("https://unipark-frontend-yvtb.onrender.com".equals(origem) ||
          "http://localhost:5500".equals(origem)) {
        res.header("Access-Control-Allow-Origin", origem);
      }

      res.header("Access-Control-Allow-Methods", "GET,POST,PUT,PATCH,DELETE,OPTIONS");
      res.header("Access-Control-Allow-Headers", "Content-Type,Authorization");
      res.header("Access-Control-Allow-Credentials", "true");
    });

    // Lida com requisições OPTIONS (pré-flight do CORS)
    options("/*", (req, res) -> {
      String headers = req.headers("Access-Control-Request-Headers");
      if (headers != null)
        res.header("Access-Control-Allow-Headers", headers);

      String methods = req.headers("Access-Control-Request-Method");
      if (methods != null)
        res.header("Access-Control-Allow-Methods", methods);

      return "OK";
    });
  }
}

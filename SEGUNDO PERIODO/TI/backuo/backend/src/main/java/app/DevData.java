package app;

import model.Pessoa;
import model.Usuario;
import model.Vaga;
import service.PessoaService;
import service.UsuarioService;
import service.VagaService;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Classe de carga inicial de dados (substitui o antigo CommandLineRunner do
 * Spring Boot).
 * Pode ser chamada no início da aplicação para popular o banco.
 */
public class DevData {

  public static void seed(PessoaService pessoas, UsuarioService usuarios, VagaService vagas) {
    try {
      // ===============================
      // CRIA PESSOA BASE (ADMIN)
      // ===============================
      Pessoa p1 = new Pessoa("11122233344", "Admin Unipark", Pessoa.TipoPessoa.USUARIO);
      pessoas.insert(p1);

      // ===============================
      // CRIA USUÁRIO ADMIN
      // ===============================
      String senhaCriptografada = BCrypt.hashpw("admin123", BCrypt.gensalt());
      Usuario u1 = new Usuario();
      u1.setId("A0001");
      u1.setNome("Administrador");
      u1.setEmail("admin@unipark.com");
      u1.setSenhaHash(senhaCriptografada);
      u1.setPerfil(Usuario.Perfil.ADMIN);
      usuarios.insert(u1);

      // ===============================
      // CRIA VAGAS AUTOMATICAMENTE
      // ===============================
      for (int i = 1; i <= 30; i++) {
        Vaga.Tipo tipo = (i % 10 == 0) ? Vaga.Tipo.PCD : Vaga.Tipo.COMUM;
        String setor = "A" + ((i - 1) / 10 + 1);
        Vaga.Status status = Vaga.Status.LIVRE;

        Vaga v = new Vaga(null, tipo, setor, status, null);
        vagas.insert(v);
      }

      System.out.println("✅ Dados iniciais inseridos com sucesso!");
      System.out.println("👤 Usuário admin criado: admin@unipark.com / senha: admin123");

    } catch (Exception e) {
      System.out.println("⚠️ Erro ao inserir dados iniciais: " + e.getMessage());
      e.printStackTrace();
    }
  }
}

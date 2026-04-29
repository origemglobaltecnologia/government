import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<ALL> grandeMuseu = new ArrayList<>();

        // Criação de Entidades Base
        ALL nacao = new ALL("Nação: Brasil");
        ALL cidade = new ALL("São Paulo");
        ALL dev = new ALL("Profissão: Dev");

        grandeMuseu.add(nacao);
        grandeMuseu.add(cidade);
        grandeMuseu.add(dev);

        // Nascimento do Indivíduo
        ALL usuario = new ALL("Cristiano");

        // Conexões de Identidade
        usuario.addAttribute(nacao.getIdentification());
        usuario.addAttribute(cidade.getIdentification());
        usuario.addAttribute(dev.getIdentification());

        grandeMuseu.add(usuario);

        // Relatório
        System.out.println("================================================================================");
        System.out.println("SISTEMA DE REPRESENTAÇÃO DIRETA - AMBIENTE LOCAL");
        System.out.println("================================================================================");
        
        for (ALL ente : grandeMuseu) {
            System.out.println(ente.toString());
            if (!ente.getAttributes().isEmpty()) {
                System.out.println("   -> Conexões (UUID): " + ente.getAttributes());
            }
        }
        
        System.out.println("================================================================================");
        System.out.println("Total de entes catalogados: " + grandeMuseu.size());
    }
}

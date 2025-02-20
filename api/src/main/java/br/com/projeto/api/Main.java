package br.com.projeto.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.projeto.api.modelo.DTOFilme;
import br.com.projeto.api.modelo.Filme;
import java.util.Scanner;

public class Main {
    private static Scanner scanner;
    private static RestTemplate restTemplate;
    private static TMDBClient tmdbClient;
    private static final String url = "http://localhost:8080/api";

    public static void cadastrar() {
        System.out.println("Digite o código do filme pelo TMDB:");
        Integer id = scanner.nextInt();
        scanner.nextLine(); // Consume the leftover newline
        DTOFilme f = tmdbClient.getMovieDetails(id);

        try {
            String response = restTemplate.postForObject(url, f, String.class);
            System.out.println("Filme cadastrado com sucesso!");
            System.out.println("Dados do filme " + response);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar filme: " + e.getMessage());
        }
        System.out.println();
    }

    public static void selecionar() {
        try {
            ResponseEntity<Filme[]> response = restTemplate.getForEntity(url, Filme[].class);
            Filme[] filmes = response.getBody();
            for (Filme filme : filmes) {
                printFilme(filme);
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar filmes: " + e.getMessage());
        }
        System.out.println();
    }

    public static void selecionarPorId() {
        System.out.println("Digite o ID do filme:");
        int idFilme = scanner.nextInt();
        scanner.nextLine(); // Consume the leftover newline
        try {
            ResponseEntity<Filme> responseFilme = restTemplate.getForEntity(url + "/" + idFilme, Filme.class);
            Filme filme = responseFilme.getBody();
            printFilme(filme);
        } catch (Exception e) {
            System.out.println("Erro ao buscar filme: " + e.getMessage());
        }
        System.out.println();
    }

    public static void editar() {
        System.out.println("Digite o ID do filme:");
        int idEditar = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite o novo título do filme:");
        String titulo = scanner.nextLine();

        System.out.println("Digite a nova descrição do filme:");
        String descricao = scanner.nextLine();

        System.out.println("Digite a nova data de lançamento do filme:");
        String dataLancamento = scanner.nextLine();

        System.out.println("Digite a nova popularidade do filme:");
        float popularidade = scanner.nextFloat();
        scanner.nextLine();

        System.out.println("Digite a nova nota média do filme:");
        float notaMedia = scanner.nextFloat();
        scanner.nextLine();

        System.out.println("Digite o novo número de votos do filme:");
        int numeroVotos = scanner.nextInt();
        scanner.nextLine();

        System.out.println();

        var filme = Filme.builder()
                .id(idEditar)
                .title(titulo)
                .overview(descricao)
                .release_date(dataLancamento)
                .popularity(popularidade)
                .vote_average(notaMedia)
                .vote_count(numeroVotos)
                .build();

//        Filme filme = Filme.builder()
//                .id(idEditar)
//                .title(titulo)
//                .overview(descricao)
//                .release_date(dataLancamento)
//                .popularity(popularidade)
//                .vote_average(notaMedia)
//                .vote_count(numeroVotos)
//                .build();

        try {
            restTemplate.put(url, filme);
            System.out.println("Filme editado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao editar filme: " + e.getMessage());
        }
        System.out.println();
    }

    public static void remover() {
        System.out.println("Digite o ID do filme:");
        int idRemover = scanner.nextInt();
        scanner.nextLine();
        System.out.println();
        try {
            restTemplate.delete(url + "/" + idRemover);
            System.out.println("Filme removido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao remover filme: " + e.getMessage());
        }
        System.out.println();
    }

    public static void maiorNota() {
        try {
            ResponseEntity<Filme> response = restTemplate.getForEntity(url + "/maiorNota", Filme.class);
            Filme filme = response.getBody();
            printFilme(filme);
        } catch (Exception e) {
            System.out.println("Erro ao buscar filme: " + e.getMessage());
        }
    }

    public static void buscaPorPopularidade() {
        System.out.println("Digite a popularidade:");
        Float popularidade = scanner.nextFloat();
        scanner.nextLine();
        try {
            ResponseEntity<Filme[]> response = restTemplate.getForEntity(url + "/popularidadeMaiorQue/" + popularidade, Filme[].class);
            Filme[] filmes = response.getBody();
            for (Filme x : filmes) {
                printFilme(x);
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar filmes: " + e.getMessage());
        }
    }

    public static void menu() {
        System.out.println("a1 - Cadastrar filme");
        System.out.println("a2 - Cadastrar os códigos 1 a 1000 do TMDB");
        System.out.println("b - Selecionar");
        System.out.println("c - Selecionar pelo ID");
        System.out.println("d - Editar");
        System.out.println("e - Remover");
        System.out.println("f - Mostrar maior nota média");
        System.out.println("g - Mostrar filmes com popularidade maior que n");
        System.out.println("h - Sair");
    }

    public static void printFilme(Filme filme) {
        System.out.println("ID: " + filme.getId());
        System.out.println("Titulo: " + filme.getTitle());
        System.out.println("Descricao: " + filme.getOverview());
        System.out.println("Data de lançamento: " + filme.getRelease_date());
        System.out.println("Popularidade: " + filme.getPopularity());
        System.out.println("Nota média: " + filme.getVote_average());
        System.out.println("Número de votos: " + filme.getVote_count());
        System.out.println("\n\n-------------------------------\n\n");
    }
    public Main() {
        restTemplate = new RestTemplate();
        tmdbClient = new TMDBClient();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        new Main();
        String opcao = "";

        System.out.println("Bem-vindo ao sistema de filmes!");
        System.out.println("Digite a opção desejada:");

        do {
            menu();
            System.out.println();
            System.out.println("Digite a opção desejada:");
            opcao = scanner.nextLine();
            System.out.println();
            switch (opcao.toLowerCase()) {
                case "a1":
                    cadastrar();
                    break;

                case "a2":
                    for (int i = 1; i <= 1000; i++) {
                        DTOFilme dtofilme = tmdbClient.getMovieDetails(i);
                        try {
                            restTemplate.postForObject(url, dtofilme, String.class);
                        } catch (Exception e) {
                            System.out.println("Erro ao cadastrar filme: " + e.getMessage());
                        }
                    }
                    break;

                case "b":
                    selecionar();
                    break;

                case "c":
                    selecionarPorId();
                    break;
                case "d":
                    editar();
                    break;

                case "e":
                    remover();
                    break;
                case "f":
                    maiorNota();
                    break;
                case "g":
                    buscaPorPopularidade();
                    break;

                case "h":
                    System.out.println("Saindo...");
                    break;
            }
        } while (!opcao.equals("h"));
        scanner.close();
    }
}
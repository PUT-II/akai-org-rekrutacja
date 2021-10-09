package pl.akai;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    /*
        Twoim zadaniem jest napisanie prostego programu do pobierania i transformowania danych
        udostępnianych przez API. Dokumentacje API możesz znależć pod poniższym linkiem:
        https://akai-recruitment.herokuapp.com/documentation.html

        Całe API zawiera jeden endpoint: https://akai-recruitment.herokuapp.com/book
        Endpoint ten zwraca liste książek zawierajacch informację takie jak:
        - id
        - tytuł
        - autor
        - ocena

        Twoim zadaniem jest:
        1. Stworzenie odpowiedniej klasy do przechowywania informacji o książce
        2. Sparsowanie danych udostępnianych przez endpoint. Aby ułatwić to zadanie,
           do projektu są dołaczone 3 najpopularniejsze biblioteki do parsowania JSONów
           do obiektów Javy - Gson, Org.Json, Jackson. Możesz wykorzystać dowolną z nich
        3. Po sparsowaniu JSONu do obiektów Javy, uzupełnij program o funkcję wypisującą 3 autorów z
           najwyższą średnią ocen (wypisz także średnie ocen)

       Projekt został utworzony przy użyciu najnowszej Javy 17,
       jednakże nic nie stoi na przeszkodzie użycia innej wersji jeśli chcesz
     */

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static void main(String[] args) {
        var client = HttpClient.newBuilder().build();
        var request = HttpRequest.newBuilder()
                .uri(URI.create("https://akai-recruitment.herokuapp.com/book"))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (InterruptedException ignored) {
            return;
        }

        List<Book> books = new Gson().fromJson(response.body(), BookTypeToken.type);

        var groupedBooks = books.stream()
                .collect(Collectors.groupingBy(it -> it.author))
                .entrySet();

        Map<String, Double> rank = new HashMap<>();
        for (var authorsBooks : groupedBooks) {
            var averageRating = authorsBooks.getValue().stream()
                    .map(it -> it.rating)
                    .mapToDouble(it -> it)
                    .average()
                    .getAsDouble();

            rank.put(authorsBooks.getKey(), averageRating);
        }

        var bestRank = rank.entrySet().stream()
                .sorted((it1, it2) -> it2.getValue().compareTo(it1.getValue()))
                .limit(3)
                .collect(Collectors.toList());


        for (int i = 0; i < 3; i++) {
            var author = bestRank.get(i).getKey();
            var rating = bestRank.get(i).getValue();

            System.out.printf("%d. %s : %f%n", i + 1, author, rating);
        }

    }
}

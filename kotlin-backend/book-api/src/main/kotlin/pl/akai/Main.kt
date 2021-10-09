package pl.akai

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

private data class Book(
    val id: String,
    val title: String,
    val author: String,
    val rating: Float
)

private class BookTypeToken : TypeToken<List<Book>>() {
    companion object {
        val type: Type = BookTypeToken().type
    }
}

fun main() {
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
           do obiektów Javy/Kotlina - Gson, Org.Json, Jackson. Możesz wykorzystać dowolną z nich
        3. Po sparsowaniu JSONu do obiektów Kotlina, uzupełnij program o funkcję wypisującą 3 autorów z
           najwyższą średnią ocen (wypisz także średnie ocen)
     */

    val client = HttpClient.newBuilder().build()
    val request = HttpRequest.newBuilder()
        .uri(URI.create("https://akai-recruitment.herokuapp.com/book"))
        .build()

    val response: HttpResponse<String> =
        client.send(request, HttpResponse.BodyHandlers.ofString())

    val books: List<Book> = Gson().fromJson(response.body(), BookTypeToken.type)

    println(books)
    val authorRank = books.groupBy { it.author }
        .map { it.key to it.value.map { book -> book.rating }.average() }
        .sortedByDescending { it.second }
        .slice(0..2)

    authorRank.forEachIndexed { index, (author: String, rank: Double) ->
        println("${index + 1} $author : $rank")
    }
}
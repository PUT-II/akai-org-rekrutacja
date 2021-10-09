package pl.akai

fun main() {
    val sentences = arrayOf(
        "Taki mamy klimat",
        "Wszędzie dobrze ale w domu najlepiej",
        "Wyskoczył jak Filip z konopii",
        "Gdzie kucharek sześć tam nie ma co jeść",
        "Nie ma to jak w domu",
        "Konduktorze łaskawy zabierz nas do Warszawy",
        "Jeżeli nie zjesz obiadu to nie dostaniesz deseru",
        "Bez pracy nie ma kołaczy",
        "Kto sieje wiatr ten zbiera burzę",
        "Być szybkim jak wiatr",
        "Kopać pod kimś dołki",
        "Gdzie raki zimują",
        "Gdzie pieprz rośnie",
        "Swoją drogą to gdzie rośnie pieprz?",
        "Mam nadzieję, że poradzisz sobie z tym zadaniem bez problemu",
        "Nie powinno sprawić żadnego problemu, bo Google jest dozwolony"
    )

    /* TODO Twoim zadaniem jest wypisanie na konsoli trzech najczęściej występujących słów
            w tablicy 'sentences' wraz z ilością ich wystąpień..

            Przykładowy wynik:
            1. "mam" - 12
            2. "tak" - 5
            3. "z" - 2
    */
    val rank = sentences.flatMap { it.split(" ") }
        .map { it.lowercase() }
        .groupingBy { it }
        .eachCount().entries
        .sortedByDescending { it.value }
        .slice(0..2)

    rank.forEachIndexed { index, (word: String, occurrences: Int) ->
        println("${index + 1}. $word - $occurrences")
    }
}

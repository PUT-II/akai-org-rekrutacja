package pl.akai;

public class Book {
    public String id;
    public String title;
    public String author;
    public Double rating;

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", rating=" + rating +
                '}';
    }
}

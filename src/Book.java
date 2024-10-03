import java.util.List;

public class Book {
    private int id;
    private String title;
    private String author;
    private String year;
    private String genre;
    private String price;
    private String language;
    private Publisher publisher;
    private String isbn;
    private String format;
    private List<Review> reviews;
    private List<String> awards; // Изменено на List<String>

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<String> getAwards() {
        return awards; // Изменено на List<String>
    }

    public void setAwards(List<String> awards) {
        this.awards = awards; // Изменено на List<String>
    }

    @Override
    public String toString() {
        return "\n**********************\n\n" +
                "Book ID: " + id + "\n" +
                "Title: " + title + "\n" +
                "Author: " + author + "\n" +
                "Year: " + year + "\n" +
                "Genre: " + genre + "\n" +
                "Price: " + price + "\n" +
                "Language: " + language + "\n" +
                "Publisher: " + (publisher != null ? publisher : "N/A") + "\n" +
                "ISBN: " + (isbn != null ? isbn : "N/A") + "\n" +
                "Format: " + (format != null ? format : "N/A") + "\n" +
                "Reviews: " + (reviews != null ? reviews : "No reviews") + "\n" +
                "Awards: " + (awards != null && !awards.isEmpty() ? awards : "No awards"); // Обновлено
    }
}

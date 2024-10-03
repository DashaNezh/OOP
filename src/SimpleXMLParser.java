import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

// Класс Library, который содержит список книг
class Library {
    List<Book> books = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Book book : books) {
            sb.append(book.toString()).append("\n");
        }
        return sb.toString();
    }
}

// Класс Book, который хранит информацию о книге
class Book {
    String id;
    String title;
    String author;
    int year;
    String genre;
    double price;
    String currency;
    List<Review> reviews = new ArrayList<>();
    String language;
    Publisher publisher;
    String isbn;
    String format;
    List<String> awards = new ArrayList<>();

    @Override
    public String toString() {
        return "Book ID: " + id + "\nTitle: " + title + "\nAuthor: " + author +
                "\nYear: " + year + "\nGenre: " + genre + "\nPrice: " + price + " " + currency +
                "\nLanguage: " + language + "\nPublisher: " + (publisher != null ? publisher.toString() : "N/A") +
                "\nReviews: " + reviews + "\nAwards: " + awards;
    }
}

// Класс Review для хранения отзывов о книге
class Review {
    String user;
    int rating;
    String comment;

    @Override
    public String toString() {
        return "User: " + user + ", Rating: " + rating + ", Comment: " + comment;
    }
}

// Класс Publisher, который содержит информацию об издателе
class Publisher {
    String name;
    Address address;

    @Override
    public String toString() {
        return "Publisher: " + name + ", Address: " + address;
    }
}

// Класс Address для хранения адреса издателя
class Address {
    String city;
    String country;

    @Override
    public String toString() {
        return city + ", " + country;
    }
}

public class SimpleXMLParser {

    public static void main(String[] args) {
        String filePath = "C:/Users/Дарья/IdeaProjects/OOP/src/rundom_structure.xml";

        try {
            // Читаем содержимое файла в строку
            String xmlData = new String(Files.readAllBytes(Paths.get(filePath)));

            Library library = parseLibrary(xmlData);

            System.out.println(library.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для парсинга всей библиотеки
    public static Library parseLibrary(String xml) {
        Library library = new Library();
        int bookStartIndex = xml.indexOf("<book");
        while (bookStartIndex != -1) {
            int bookEndIndex = xml.indexOf("</book>", bookStartIndex);
            String bookData = xml.substring(bookStartIndex, bookEndIndex + 7);
            library.books.add(parseBook(bookData));
            bookStartIndex = xml.indexOf("<book", bookEndIndex);
        }
        return library;
    }

    // Метод для парсинга одной книги
    public static Book parseBook(String xml) {
        Book book = new Book();

        book.id = extractValue(xml, "id=\"", "\"");
        book.title = extractValue(xml, "<title>", "</title>");
        book.author = extractValue(xml, "<author>", "</author>");
        book.year = Integer.parseInt(extractValue(xml, "<year>", "</year>"));
        book.genre = extractValue(xml, "<genre>", "</genre>");
        book.price = Double.parseDouble(extractValue(xml, "<price currency=\"", "</price>").split(">")[1]);
        book.currency = extractValue(xml, "currency=\"", "\"");

        String reviewsData = extractBetween(xml, "<reviews>", "</reviews>");
        if (!reviewsData.isEmpty()) {
            int reviewStartIndex = reviewsData.indexOf("<review");
            while (reviewStartIndex != -1) {
                int reviewEndIndex = reviewsData.indexOf("</review>", reviewStartIndex);
                String reviewData = reviewsData.substring(reviewStartIndex, reviewEndIndex + 9);
                book.reviews.add(parseReview(reviewData));
                reviewStartIndex = reviewsData.indexOf("<review", reviewEndIndex);
            }
        }

        book.language = extractValue(xml, "<language>", "</language>");
        book.isbn = extractValue(xml, "<isbn>", "</isbn>");
        book.format = extractValue(xml, "<format>", "</format>");

        String publisherData = extractBetween(xml, "<publisher>", "</publisher>");
        if (!publisherData.isEmpty()) {
            book.publisher = parsePublisher(publisherData);
        }

        String awardsData = extractBetween(xml, "<awards>", "</awards>");
        if (!awardsData.isEmpty()) {
            int awardStartIndex = awardsData.indexOf("<award");
            while (awardStartIndex != -1) {
                int awardEndIndex = awardsData.indexOf("</award>", awardStartIndex);
                book.awards.add(extractValue(awardsData, "<award>", "</award>"));
                awardStartIndex = awardsData.indexOf("<award", awardEndIndex);
            }
        }

        return book;
    }

    // Метод для парсинга отзывов
    public static Review parseReview(String xml) {
        Review review = new Review();
        review.user = extractValue(xml, "<user>", "</user>");
        review.rating = Integer.parseInt(extractValue(xml, "<rating>", "</rating>"));
        review.comment = extractValue(xml, "<comment>", "</comment>");
        return review;
    }

    // Метод для парсинга издателя
    public static Publisher parsePublisher(String xml) {
        Publisher publisher = new Publisher();
        publisher.name = extractValue(xml, "<name>", "</name>");
        String addressData = extractBetween(xml, "<address>", "</address>");
        if (!addressData.isEmpty()) {
            publisher.address = parseAddress(addressData);
        }
        return publisher;
    }

    // Метод для парсинга адреса издателя
    public static Address parseAddress(String xml) {
        Address address = new Address();
        address.city = extractValue(xml, "<city>", "</city>");
        address.country = extractValue(xml, "<country>", "</country>");
        return address;
    }

    // Вспомогательные методы для извлечения данных между тегами
    public static String extractValue(String xml, String startTag, String endTag) {
        int start = xml.indexOf(startTag) + startTag.length();
        int end = xml.indexOf(endTag, start);
        if (start < 0 || end < 0) {
            return "";
        }
        return xml.substring(start, end);
    }

    public static String extractBetween(String xml, String startTag, String endTag) {
        int start = xml.indexOf(startTag);
        if (start == -1) {
            return "";
        }
        int end = xml.indexOf(endTag, start);
        if (end == -1) {
            return "";
        }
        return xml.substring(start + startTag.length(), end);
    }
}

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import javax.xml.parsers.*;
import org.w3c.dom.*;

class Library {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public String toString() {
        return books.stream()
                .map(Book::toString)
                .collect(Collectors.joining("\n\n", "Library:\n", ""));
    }
}

class Book {
    private String id, title, author, genre, language, isbn, format, currency;
    private int year;
    private double price;
    private Publisher publisher;
    private List<Review> reviews = new ArrayList<>();
    private List<String> awards = new ArrayList<>();

    public Book(String id, String title, String author, int year, String genre, double price, String currency,
                String language, Publisher publisher, String isbn, String format, List<Review> reviews, List<String> awards) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
        this.price = price;
        this.currency = currency;
        this.language = language;
        this.publisher = publisher;
        this.isbn = isbn;
        this.format = format;
        this.reviews = reviews;
        this.awards = awards;
    }

    @Override
    public String toString() {
        return String.format("ID: %s\nTitle: %s\nAuthor: %s\nYear: %d\nGenre: %s\nPrice: %.2f %s\nLanguage: %s\nPublisher: %s\nISBN: %s\nFormat: %s\nReviews:\n%s\nAwards: %s",
                id, title, author, year, genre, price, currency, language,
                publisher != null ? publisher : "N/A", isbn, format,
                reviews.isEmpty() ? "No reviews" : reviews.stream().map(Review::toString).collect(Collectors.joining("\n")),
                awards.isEmpty() ? "No awards" : String.join(", ", awards));
    }
}

class Review {
    private String user, comment;
    private int rating;

    public Review(String user, int rating, String comment) {
        this.user = user;
        this.rating = rating;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return String.format("User: %s, Rating: %d, Comment: %s", user, rating, comment);
    }
}

class Publisher {
    private String name;
    private Address address;

    public Publisher(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format("Publisher: %s, Address: %s", name, address);
    }
}

class Address {
    private String city, country;

    public Address(String city, String country) {
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString() {
        return String.format("%s, %s", city, country);
    }
}

public class SimpleXMLParser {

    public static void main(String[] args) {
        String filePath = "C:/Users/Дарья/IdeaProjects/OOP/src/rundom_structure.xml";

        try {
            Library library = parseLibrary(filePath);
            System.out.println(library.toString());
        } catch (Exception e) {
            System.err.println("Failed to process XML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Library parseLibrary(String filePath) throws Exception {
        Library library = new Library();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(Files.newInputStream(Paths.get(filePath)));
        NodeList bookNodes = doc.getElementsByTagName("book");

        for (int i = 0; i < bookNodes.getLength(); i++) {
            Element bookElement = (Element) bookNodes.item(i);
            Book book = parseBook(bookElement);
            library.addBook(book);
        }

        return library;
    }

    public static Book parseBook(Element bookElement) {
        String id = bookElement.getAttribute("id");
        String title = getTextContent(bookElement, "title");
        String author = getTextContent(bookElement, "author");
        int year = Integer.parseInt(getTextContent(bookElement, "year"));
        String genre = getTextContent(bookElement, "genre");
        double price = Double.parseDouble(getTextContent(bookElement, "price"));
        String currency = bookElement.getAttribute("currency");
        String language = getTextContent(bookElement, "language");
        String isbn = getTextContent(bookElement, "isbn");
        String format = getTextContent(bookElement, "format");

        Publisher publisher = parsePublisher((Element) bookElement.getElementsByTagName("publisher").item(0));
        List<Review> reviews = parseReviews((Element) bookElement.getElementsByTagName("reviews").item(0));
        List<String> awards = parseAwards((Element) bookElement.getElementsByTagName("awards").item(0));

        return new Book(id, title, author, year, genre, price, currency, language, publisher, isbn, format, reviews, awards);
    }

    public static Publisher parsePublisher(Element publisherElement) {
        if (publisherElement == null) return null;
        String name = getTextContent(publisherElement, "name");
        Element addressElement = (Element) publisherElement.getElementsByTagName("address").item(0);
        Address address = new Address(getTextContent(addressElement, "city"), getTextContent(addressElement, "country"));
        return new Publisher(name, address);
    }

    public static List<Review> parseReviews(Element reviewsElement) {
        List<Review> reviews = new ArrayList<>();
        if (reviewsElement == null) return reviews;
        NodeList reviewNodes = reviewsElement.getElementsByTagName("review");

        for (int i = 0; i < reviewNodes.getLength(); i++) {
            Element reviewElement = (Element) reviewNodes.item(i);
            String user = getTextContent(reviewElement, "user");
            int rating = Integer.parseInt(getTextContent(reviewElement, "rating"));
            String comment = getTextContent(reviewElement, "comment");
            reviews.add(new Review(user, rating, comment));
        }

        return reviews;
    }

    public static List<String> parseAwards(Element awardsElement) {
        List<String> awards = new ArrayList<>();
        if (awardsElement == null) return awards;
        NodeList awardNodes = awardsElement.getElementsByTagName("award");

        for (int i = 0; i < awardNodes.getLength(); i++) {
            awards.add(awardNodes.item(i).getTextContent());
        }

        return awards;
    }

    public static String getTextContent(Element element, String tagName) {
        NodeList list = element.getElementsByTagName(tagName);
        return list.getLength() > 0 ? list.item(0).getTextContent() : "";
    }
}

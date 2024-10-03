import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ParserMain {
    public static void main(String[] args) {
        String filePath = "C:/Users/Дарья/IdeaProjects/OOP/src/rundom_structure.xml";

        try {
            String xmlData = new String(Files.readAllBytes(Paths.get(filePath)));

            Library library = parseLibrary(xmlData);

            System.out.println(library);

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + filePath);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Не удалось обработать XML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Library parseLibrary(String xml) {
        Library library = new Library();
        int bookStartIndex = xml.indexOf("<book");
        while (bookStartIndex != -1) {
            int bookEndIndex = xml.indexOf("</book>", bookStartIndex);
            String bookData = xml.substring(bookStartIndex, bookEndIndex + 7);
            Book book = parseBook(bookData);
            library.getBooks().add(book);
            bookStartIndex = xml.indexOf("<book", bookEndIndex);
        }
        return library;
    }

    private static Book parseBook(String xml) {
        Book book = new Book();

        // Извлечение ID книги как строки
        String bookIdString = extractValue(xml, "<book id=\"", "\">");
        int bookId = 0; // Инициализация переменной bookId
        try {
            bookId = Integer.parseInt(bookIdString); // Преобразуем строку в int
        } catch (NumberFormatException e) {
            System.out.println("Ошибка при парсинге ID книги: " + bookIdString);
        }
        book.setId(bookId); // Установка ID книги

        book.setTitle(extractValue(xml, "<title>", "</title>"));
        book.setAuthor(extractValue(xml, "<author>", "</author>"));
        book.setYear(extractValue(xml, "<year>", "</year>"));
        book.setGenre(extractValue(xml, "<genre>", "</genre>"));

        // Извлечение цены с учетом валюты
        String priceData = extractValue(xml, "<price currency=\"", "</price>");
        String currency = ""; // Инициализация переменной currency
        String priceValue = ""; // Инициализация переменной priceValue

        if (priceData.contains("\"")) {
            currency = priceData.substring(priceData.indexOf("\"") + 1); // Получение валюты
            priceValue = priceData.split("\"")[0].trim(); // Получение цены
        }
        book.setPrice(priceValue + " " + currency); // Установка цены

        book.setLanguage(extractValue(xml, "<language>", "</language>"));
        book.setPublisher(extractPublisher(xml)); // Метод извлечения Publisher
        book.setIsbn(extractValue(xml, "<isbn>", "</isbn>"));
        book.setFormat(extractValue(xml, "<format>", "</format>"));

        // Извлечение отзывов
        book.setReviews(extractReviews(xml));
        // Извлечение наград
        book.setAwards(extractAwards(xml));

        return book;
    }




    public static List<Review> extractReviews(String xml) {
        List<Review> reviews = new ArrayList<>();
        String reviewsData = extractBetween(xml, "<reviews>", "</reviews>");
        if (!reviewsData.isEmpty()) {
            int reviewStartIndex = reviewsData.indexOf("<review");
            while (reviewStartIndex != -1) {
                int reviewEndIndex = reviewsData.indexOf("</review>", reviewStartIndex);
                String reviewData = reviewsData.substring(reviewStartIndex, reviewEndIndex + 9);
                Review review = parseReview(reviewData);
                reviews.add(review);
                reviewStartIndex = reviewsData.indexOf("<review", reviewEndIndex);
            }
        }
        return reviews;
    }

    public static Publisher extractPublisher(String xml) {
        String publisherData = extractBetween(xml, "<publisher>", "</publisher>");
        Publisher publisher = new Publisher();
        if (!publisherData.isEmpty()) {
            publisher.setName(extractValue(publisherData, "<name>", "</name>"));
            String city = extractValue(publisherData, "<city>", "</city>");
            String country = extractValue(publisherData, "<country>", "</country>");
            Address address = new Address(city, country); // Передаем значения в конструктор
            publisher.setAddress(address);
        }
        return publisher;
    }

    public static List<String> extractAwards(String xml) {
        List<String> awards = new ArrayList<>();
        String awardsData = extractBetween(xml, "<awards>", "</awards>");
        if (!awardsData.isEmpty()) {
            int awardStartIndex = awardsData.indexOf("<award");
            while (awardStartIndex != -1) {
                int awardEndIndex = awardsData.indexOf("</award>", awardStartIndex);
                if (awardEndIndex != -1) {
                    String awardData = awardsData.substring(awardStartIndex, awardEndIndex + 8); // +8 для закрывающего тега </award>
                    awards.add(extractValue(awardData, "<award>", "</award>")); // Извлечение значения
                    awardStartIndex = awardsData.indexOf("<award", awardEndIndex); // Продолжаем поиск
                } else {
                    break; // Если нет закрывающего тега, выходим из цикла
                }
            }
        }
        return awards;
    }

    public static Review parseReview(String xml) {
        Review review = new Review();
        review.setUser(extractValue(xml, "<user>", "</user>"));
        review.setRating(Integer.parseInt(extractValue(xml, "<rating>", "</rating>")));
        review.setComment(extractValue(xml, "<comment>", "</comment>"));
        return review;
    }

    public static String extractBetween(String xml, String startTag, String endTag) {
        int startIndex = xml.indexOf(startTag);
        int endIndex = xml.indexOf(endTag, startIndex);
        return (startIndex != -1 && endIndex != -1) ? xml.substring(startIndex + startTag.length(), endIndex) : "";
    }

    public static String extractValue(String xml, String startTag, String endTag) {
        int startIndex = xml.indexOf(startTag);
        if (startIndex == -1) return "N/A"; // Если не найден, вернуть "N/A"
        startIndex += startTag.length();
        int endIndex = xml.indexOf(endTag, startIndex);
        if (endIndex == -1) return "N/A"; // Если не найден, вернуть "N/A"
        return xml.substring(startIndex, endIndex).trim();
    }

}

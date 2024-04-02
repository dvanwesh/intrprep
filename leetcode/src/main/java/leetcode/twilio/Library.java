package leetcode.twilio;

import java.util.*;
import java.util.stream.Collectors;

public class Library {

    interface IBook {
        int getId();

        void setId(int id);

        String getCategory();

        void setCategory(String category);

        String getTitle();

        void setTitle(String title);

        int getPrice();

        void setPrice(int price);

        String getAuthor();

        void setAuthor(String author);
    }

    static class Book implements IBook {
        int id;
        String category;
        String title;
        int price;
        String author;

        public Book(int id, String category, String title, int price, String author) {
            this.id = id;
            this.category = category;
            this.title = title;
            this.price = price;
            this.author = author;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }

    static class BookTitleCount {
        String title;
        int price;
        int quantity;

        public BookTitleCount(String title, int price, int quantity) {
            this.title = title;
            this.price = price;
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return "BookTitleCount{" +
                    "title='" + title + '\'' +
                    ", price=" + price +
                    ", quantity=" + quantity +
                    '}' + "\n";
        }
    }

    static class CategoryAuthorCounts {
        String category;
        String author;
        int count;

        public CategoryAuthorCounts(String category, String author, int count) {
            this.category = category;
            this.author = author;
            this.count = count;
        }

        @Override
        public String toString() {
            return "CategoryAuthorCounts{" +
                    "category='" + category + '\'' +
                    ", author='" + author + '\'' +
                    ", count=" + count +
                    '}' + "\n";
        }
    }

    interface ILibrarySystem {
        void addBook(IBook book, int quantity);

        void removeBook(IBook book, int quantity);

        int totalPrice();

        List<CategoryAuthorCounts> getCategoryAuthorCounts();

        List<BookTitleCount> getTitleAndCounts();

        Map<String, Integer> getCategoryCount();
    }

    static class LibrarySystem implements ILibrarySystem {

        int totalSum = 0;
        Map<IBook, Integer> inventory = new TreeMap<>(Comparator.comparing(IBook::getTitle));

        @Override
        public void addBook(IBook book, int quantity) {
            try {
                int currBookCount = inventory.getOrDefault(book, 0);
                inventory.put(book, currBookCount + quantity);
                totalSum += book.getPrice() * quantity;
            } catch (Exception ex) {
                throw ex;
            }
        }

        @Override
        public void removeBook(IBook book, int quantity) {
            int currBookCount = inventory.getOrDefault(book, 0);
            if (currBookCount < quantity) {
                totalSum -= book.getPrice() * currBookCount;
                inventory.remove(book);
            } else {
                totalSum -= book.getPrice() * quantity;
                inventory.put(book, currBookCount - quantity);
            }
        }

        @Override
        public int totalPrice() {
            return totalSum;
        }

        // sort by category/author
        @Override
        public List<CategoryAuthorCounts> getCategoryAuthorCounts() {
            Map<String, Map<String, Integer>> categoryAuthorMap = new TreeMap<>();
            for (Map.Entry<IBook, Integer> entry : inventory.entrySet()) {
                IBook book = entry.getKey();
                int quantity = entry.getValue();
                Map<String, Integer> authorBookCountMap = categoryAuthorMap.computeIfAbsent(book.getCategory(), a -> new TreeMap<>());
                authorBookCountMap.put(book.getAuthor(), authorBookCountMap.getOrDefault(book.getAuthor(), 0) + quantity);
            }
            List<CategoryAuthorCounts> results = new ArrayList<>();
            for (Map.Entry<String, Map<String, Integer>> categoryEntry : categoryAuthorMap.entrySet()) {
                for (Map.Entry<String, Integer> authorEntry : categoryEntry.getValue().entrySet()) {
                    results.add(new CategoryAuthorCounts(categoryEntry.getKey(), authorEntry.getKey(), authorEntry.getValue()));
                }
            }
            return results;
        }

        @Override
        public List<BookTitleCount> getTitleAndCounts() {
            return inventory.entrySet().stream().map(e -> new BookTitleCount(e.getKey().getTitle(), e.getKey().getPrice(), e.getValue()))
                    .collect(Collectors.toList());
        }

        @Override
        public Map<String, Integer> getCategoryCount() {
            Map<String, Integer> categoryCounts = new HashMap<>();
            for (IBook book : inventory.keySet()) {
                categoryCounts.put(book.getCategory(), categoryCounts.getOrDefault(book.getCategory(), 0) + inventory.get(book));
            }
            return categoryCounts;
        }
    }

    public static void main(String[] args) {
        LibrarySystem librarySystem = new LibrarySystem();
        List<Book> books = new ArrayList<>();

        // Fiction Books
        books.add(new Book(1, "Fiction", "Pride and Prejudice", 20, "Jane Austen"));
        books.add(new Book(2, "Fiction", "The Lord of the Rings", 30, "J.R.R. Tolkien"));
        books.add(new Book(3, "Fiction", "The Handmaid's Tale", 10, "Margaret Atwood"));

        // Non-Fiction Books
        books.add(new Book(4, "Non-Fiction", "A Short History of Nearly Everything", 5, "Bill Bryson"));
        books.add(new Book(5, "Non-Fiction", "Sapiens: A Brief History of Humankind", 20, "Yuval Noah Harari"));
        books.add(new Book(6, "Non-Fiction", "Outliers: The Story of Success", 10, "Malcolm Gladwell"));

        // Science Fiction Books
        books.add(new Book(7, "Science Fiction", "Dune", 10, "Frank Herbert"));
        books.add(new Book(8, "Science Fiction", "The Hitchhiker's Guide to the Galaxy", 5, "Douglas Adams"));
        books.add(new Book(9, "Science Fiction", "Ender's Game", 20, "Orson Scott Card"));

        // Fantasy Books
        books.add(new Book(10, "Fantasy", "Harry Potter and the Sorcerer's Stone", 10, "J.K. Rowling"));
        books.add(new Book(11, "Fantasy", "The Chronicles of Narnia", 20, "C.S. Lewis"));
        books.add(new Book(12, "Fantasy", "A Game of Thrones 1", 30, "George R.R. Martin"));
        books.add(new Book(12, "Fantasy", "A Game of Thrones 2", 30, "George R.R. Martin"));
        books.add(new Book(12, "Fantasy", "A Game of Thrones 3", 30, "George R.R. Martin"));
        books.add(new Book(12, "Fantasy", "A Game of Thrones 4", 30, "George R.R. Martin"));

        int i = 1;
        for (Book book : books) {
            librarySystem.addBook(book, i);
            System.out.println(librarySystem.totalPrice());
        }
        System.out.println(librarySystem.getTitleAndCounts());
        System.out.println(librarySystem.getCategoryAuthorCounts());
        System.out.println(librarySystem.getCategoryCount());
    }
}

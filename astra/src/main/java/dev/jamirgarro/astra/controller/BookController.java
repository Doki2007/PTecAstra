package dev.jamirgarro.astra.controller;

import jakarta.validation.Valid;
import dev.jamirgarro.astra.model.Status;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import dev.jamirgarro.astra.repository.BookRepository;
import dev.jamirgarro.astra.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/book")
public class BookController {


    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping("")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book savedBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("")
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) String author,
                                                  @RequestParam(required = false) Status status) {
        List<Book> books;
        if (author != null) {
            books = bookRepository.findByAuthor(author);
        } else if (status != null) {
            books = bookRepository.findByStatus(status);
        } else {
            books = bookRepository.findAll();
        }
        return ResponseEntity.ok(books);
    }

    // Nuevo método para obtener libros por autor y rango de fechas
    @GetMapping("/search")
    public ResponseEntity<List<Book>> getBooksByAuthorAndDateRange(
            @RequestParam String author,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<Book> books = bookRepository.findBooksByAuthorAndDateRange(author, startDate, endDate);
        return ResponseEntity.ok(books);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book updatedBook) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setIsbn(updatedBook.getIsbn());
            book.setPublishedDate(updatedBook.getPublishedDate());
            book.setStatus(updatedBook.getStatus());
            bookRepository.save(book);
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



}

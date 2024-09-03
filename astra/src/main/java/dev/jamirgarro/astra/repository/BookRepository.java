package dev.jamirgarro.astra.repository;

import dev.jamirgarro.astra.model.Book;
import dev.jamirgarro.astra.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthor(String author);
    List<Book> findByStatus(Status status);
    List<Book> findByIsbn(String isbn);

    @Query("SELECT b FROM Book b WHERE b.author = :author AND b.publishedDate BETWEEN :startDate AND :endDate")
    List<Book> findBooksByAuthorAndDateRange(String author, LocalDate startDate, LocalDate endDate);

    @Query("SELECT COUNT(b) FROM Book b WHERE b.status = :status")
    long countBooksByStatus(Status status);

}

package com.example.project10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // إضافة كتاب جديد
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    // تعديل كتاب
    public Book updateBook(Long id, Book updatedBook) {
        Book book = bookRepository.findById(id).orElseThrow();

        book.setTitle(updatedBook.getTitle());
        book.setYear(updatedBook.getYear());
        book.setCategory(updatedBook.getCategory());
        book.setTotalCopies(updatedBook.getTotalCopies());
        book.setRating(updatedBook.getRating());
        book.setCoverImageUrl(updatedBook.getCoverImageUrl());
        book.setLoanCount(updatedBook.getLoanCount());
        book.setDescription(updatedBook.getDescription());
        book.setPrice(updatedBook.getPrice());
        book.setPurchasePrice(updatedBook.getPurchasePrice());
        book.setSaleType(updatedBook.getSaleType());

        return bookRepository.save(book);
    }

    // حذف كتاب
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // جلب كتاب حسب ID
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    // جلب كل الكتب
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // بحث بالعنوان
    public List<Book> searchBooks(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword);
    }

    // عدد النسخ المتاحة
    public int getAvailableCopies(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        return book.getTotalCopies();
    }

    // تحويل كيان Book إلى BookDTO
    private BookDTO convertToDTO(Book book) {
        BookDTO dto = new BookDTO();

        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setYear(book.getYear());
        dto.setCategory(book.getCategory());
        dto.setTotalCopies(book.getTotalCopies());
        dto.setRating(book.getRating());
        dto.setCreatedAt(book.getCreatedAt());
        dto.setCoverImageUrl(book.getCoverImageUrl());
        dto.setLoanCount(book.getLoanCount());
        dto.setDescription(book.getDescription());
        dto.setPrice(book.getPrice());
        dto.setPurchasePrice(book.getPurchasePrice());
        dto.setSaleType(book.getSaleType().name());
        dto.setCanBuy(book.canBuy());
        dto.setCanRent(book.canRent());

        List<AuthorDTO> authorDTOs = book.getAuthors().stream()
                .map(AuthorDTO::new)
                .collect(Collectors.toList());

        dto.setAuthors(authorDTOs);

        return dto;
    }

    // جلب كل الكتب بصيغة BookDTO
    public List<BookDTO> getAllBooksDTO()
    {
        return bookRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
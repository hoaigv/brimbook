package com.example.bookshop.books.services;

import com.example.bookshop.books.controllers.dto.books.*;
import com.example.bookshop.utils.componentUtils.spec.BookSpecification;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IBookService {
    BookResponse getBook(Integer id);
    BookCreateResponse createBook(BookCreateRequest request , MultipartFile file) ;
    Map<String,String> updateBookWithoutImage(BookUpdateRequest request);
    List<BookResponse> getAllBooks(Pageable pageable, BookSpecification filter);
    List<BookResponse> getBooksByCategories(Pageable pageable, List<String> categories);
    List<BookSearchResponse> searchBooks(BookSpecification filter , Pageable pageable);

}

package com.example.bookshop.books.services;

import com.example.bookshop.books.controllers.dto.books.BookCreateRequest;
import com.example.bookshop.books.controllers.dto.books.BookCreateResponse;
import com.example.bookshop.books.controllers.dto.books.BookResponse;
import com.example.bookshop.books.controllers.dto.books.BookUpdateRequest;
import com.example.bookshop.utils.componentUtils.spec.BookSpecification;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IBookService {
    List<BookResponse> getAllBooks1(Pageable pageable, BookSpecification filter);
    BookResponse updateBook(BookUpdateRequest request,MultipartFile image, Integer id);

    List<BookResponse> getBooksUser();
    BookResponse createBookImg(BookCreateRequest request, MultipartFile image);
    BookResponse getBooksUserById(Integer id);
    BookResponse getBookById(Integer id);
}

package com.example.bookshop.books.services.impl;

import com.example.bookshop.books.controllers.dto.books.*;
import com.example.bookshop.books.models.BookEntity;
import com.example.bookshop.utils.componentUtils.spec.BookSpecification;
import com.example.bookshop.books.models.CategoryEntity;
import com.example.bookshop.exceptionHandler.CustomRunTimeException;
import com.example.bookshop.exceptionHandler.ErrorCode;
import com.example.bookshop.books.mapper.BookMapper;
import com.example.bookshop.books.repositories.BookRepository;
import com.example.bookshop.books.repositories.CategoryRepository;
import com.example.bookshop.books.services.IBookService;
import com.example.bookshop.utils.CloudUtils;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookService implements IBookService {
    @Value("${firebase.token}")
    @NonFinal
    String firebaseToken;
    BookRepository bookRepository;
    CategoryRepository categoryRepository;
    BookMapper bookMapper;
    CloudUtils cloudinary;


    @Override
    public List<BookResponse> getAllBooks(Pageable pageable, BookSpecification filter) {
        var result = bookRepository.findAll(filter, pageable);
        List<BookResponse> bookResponses = new ArrayList<>();
        for (BookEntity book : result) {
            var bookItem = bookMapper.bookToBookResponse(book);
            bookResponses.add(bookItem);
        }
        return bookResponses;
    }

    @Override
    public List<BookResponse> getBooksByCategories(Pageable pageable, List<String> categories) {
        Set<CategoryEntity> categoryEntities = categoryRepository.findAllByCategoryCodeIn(new HashSet<>(categories));
        Specification<BookEntity> spec = BookRepository.Specs.byCategories(categoryEntities);
            var data = bookRepository.findAll(spec,pageable);
        return data.stream().map(bookMapper::bookToBookResponse).collect(Collectors.toList());
    }

    @Override
    public BookResponse getBook(Integer id) {
        var bookEntity = bookRepository.findById(id).orElseThrow(
                () -> new CustomRunTimeException(ErrorCode.BOOK_NOT_FOUND)
        );

        return bookMapper.bookToBookResponse(bookEntity);
    }

    @Override
    @Transactional
    public BookCreateResponse createBook(BookCreateRequest request, MultipartFile file) {
        var bookEntity = bookMapper.booktoBookEntity(request);
        bookEntity.setChapters(null);
        var link = cloudinary.uploadFile(file);
        bookEntity.setImage_url(link);
        var result = bookRepository.save(bookEntity);
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(Collections.singletonList(firebaseToken), String.valueOf(result.getId()));
        } catch (FirebaseMessagingException e) {
            throw new CustomRunTimeException(ErrorCode.ADD_TOPIC_NOT_SUCCESS);
        }
        return BookCreateResponse.builder()
                .title(result.getTitle())
                .id(result.getId())
                .build();
    }

    @Override
    @Transactional
    public Map<String, String> updateBookWithoutImage(BookUpdateRequest request) {
        var oldBookEntity = bookRepository.findById(request.getId()).orElseThrow(
                () -> new CustomRunTimeException(ErrorCode.BOOK_NOT_FOUND)
        );
    var newRequest = bookMapper.bookUpdateToBookCreateRequest(request);
    var newEntity = bookMapper.updateBookToBookEntity(oldBookEntity,bookMapper.booktoBookEntity(newRequest));
   var resp =  bookRepository.save(newEntity);
        return Map.of("message" ,resp.getTitle());
    }

    @Override
    public List<BookSearchResponse> searchBooks(BookSpecification filter, Pageable pageable) {
        var books = bookRepository.findAll(filter, pageable);
        List<BookSearchResponse> bookResponses = new ArrayList<>();
        for (BookEntity book : books) {
            var bookResp = bookMapper.bookToBookSearchResponse(book);
            bookResp.setCategories(book.getCategories().stream().map(
                    CategoryEntity::getCategoryName
            ).collect(Collectors.toSet()));
            bookResponses.add(bookResp);
        }
        return bookResponses;
    }





}

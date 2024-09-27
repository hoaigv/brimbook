package com.example.bookshop.books.services.impl;

import com.example.bookshop.books.controllers.dto.books.BookCreateRequest;
import com.example.bookshop.books.controllers.dto.books.BookResponse;
import com.example.bookshop.books.controllers.dto.books.BookUpdateRequest;
import com.example.bookshop.books.mappers.BookEntityMapper;
import com.example.bookshop.books.models.BookEntity;
import com.example.bookshop.books.repositories.BookRepository;
import com.example.bookshop.books.services.IBookService;
import com.example.bookshop.categories.models.CategoryEntity;
import com.example.bookshop.categories.repositories.CategoryRepository;
import com.example.bookshop.exceptionHandlers.CustomRunTimeException;
import com.example.bookshop.exceptionHandlers.ErrorCode;
import com.example.bookshop.users.models.UserEntity;
import com.example.bookshop.users.repositories.LikeRepository;
import com.example.bookshop.users.repositories.ReadBookRepository;
import com.example.bookshop.users.repositories.UserRepository;
import com.example.bookshop.utils.AuthUtils;
import com.example.bookshop.utils.CloudUtils;

import com.example.bookshop.utils.componentUtils.spec.BookSpecification;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutionException;
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
    CloudUtils cloudinary;



    @Autowired
    BookEntityMapper bookEntityMapper;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private ReadBookRepository readBookRepository;


    @Override
    public List<BookResponse> getAllBooks1(Pageable pageable, BookSpecification filter) {
        System.out.println("Day la getAllBooks1 va filter: "+filter.toString()+" va "+pageable.toString());
        var result = bookRepository.findAll(filter, pageable);
        System.out.println("Day la tap sau:"+result);
        var bookMap = BookEntityMapper.toBookDTOList(result.getContent());
        for(BookResponse bookEntity : bookMap) {
            Integer likeTotal = likeRepository.countLikesByBookId(bookEntity.getId());
            Integer readBooksTotal = readBookRepository.countReadBooksByBookId(bookEntity.getId());
            bookEntity.setTotal_likes(likeTotal);
            bookEntity.setTotal_reads(readBooksTotal);
        }
        return bookMap;
    }

    @Override
    public List<BookResponse> getBooksUser() {
        var user = userRepository.findByUsername(AuthUtils.getUserCurrent());
        System.out.println("Hi "+user.get().getUsername());
        var book = bookRepository.findAllByUserId(user.get().getId());
        System.out.println("Hi "+book.stream().toList());
        var bookMap = BookEntityMapper.toBookDTOList(book);
        for(BookResponse bookEntity : bookMap) {
            Integer likeTotal = likeRepository.countLikesByBookId(bookEntity.getId());
            Integer readBooksTotal = readBookRepository.countReadBooksByBookId(bookEntity.getId());
            bookEntity.setTotal_likes(likeTotal);
            bookEntity.setTotal_reads(readBooksTotal);
        }
        return bookMap.stream().toList();
    }

    @Override
    @Transactional
    public BookResponse createBookImg(BookCreateRequest request, MultipartFile image) {

        var bookEntity = modelMapper.map(request, BookEntity.class);


        CategoryEntity categoryEntity = categoryRepository.findById(request.getCategoriesID()).orElseThrow(()->new CustomRunTimeException(ErrorCode.CATEGORY_NOT_FOUND));
        bookEntity.setCategory(categoryEntity);

        var user = userRepository.findByUsername(AuthUtils.getUserCurrent())
                .orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));

        UserEntity userEntity = userRepository.findById(user.getId()).orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));System.out.println("User +"+userEntity.getUsername());
        bookEntity.setUser(userEntity);
        if (image.isEmpty()) {
            throw new IllegalArgumentException("File have not data");
        }
        String link;
        try {
            link = cloudinary.uploadFileAsync(image).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new CustomRunTimeException(ErrorCode.SET_IMAGE_NOT_SUCCESS);
        }

        bookEntity.setImage_url(link);


        bookEntity.setPublishedDate(new java.sql.Date(System.currentTimeMillis()));
        var result = bookRepository.save(bookEntity);

        return modelMapper.map(result, BookResponse.class);

    }

    @Override
    public BookResponse updateBook(BookUpdateRequest request, MultipartFile image, Integer id) {
        try {
            System.out.println("Hi 1");
            System.out.println("Category "+request.getCategories_ID());
            var oldBook = bookRepository.findById(id).orElseThrow(() -> new CustomRunTimeException(ErrorCode.BOOK_NOT_FOUND));
            var category = categoryRepository.findById(oldBook.getCategory().getId()).orElseThrow(() -> new CustomRunTimeException(ErrorCode.CATEGORY_NOT_FOUND));

            System.out.println("Hi 1");
            var book = modelMapper.map(request, BookEntity.class);
            System.out.println("Hi 2");
            modelMapper.map(book, oldBook);
            oldBook.setCategory(category);

            System.out.println("Hi 3");

            if (image != null && !image.isEmpty()) {
                if (oldBook.getImage_url() != null && !oldBook.getImage_url().isEmpty()) {
                    cloudinary.deleteFile(oldBook.getImage_url());
                }
                var link = cloudinary.uploadFileAsync(image).get();
                oldBook.setImage_url(link);
            }
            System.out.println("Hi 4");
            var bookUpdate = bookRepository.save(oldBook);
            return modelMapper.map(bookUpdate, BookResponse.class);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BookResponse getBooksUserById(Integer bookId){
        var user = userRepository.findByUsername(AuthUtils.getUserCurrent()).orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
        var books = bookRepository.findByUserIdAndBookId(user.getId(),bookId).orElseThrow(()->new CustomRunTimeException(ErrorCode.BOOK_NOT_FOUND));
        Integer likeTotal = likeRepository.countLikesByBookId(books.getId());
        Integer readBooksTotal = readBookRepository.countReadBooksByBookId(books.getId());
        var bookMap = modelMapper.map(books, BookResponse.class);
        bookMap.setTotal_likes(likeTotal);
        bookMap.setTotal_reads(readBooksTotal);
        return bookMap;
    }

    @Override
    public BookResponse getBookById(Integer bookId){
        var book = bookRepository.findById(bookId).orElseThrow(() -> new CustomRunTimeException(ErrorCode.BOOK_NOT_FOUND));
        Integer likeTotal = likeRepository.countLikesByBookId(book.getId());
        Integer readBooksTotal = readBookRepository.countReadBooksByBookId(book.getId());
        var bookMap = modelMapper.map(book, BookResponse.class);
        bookMap.setTotal_likes(likeTotal);
        bookMap.setTotal_reads(readBooksTotal);
        return bookMap;
    }

    @Override
    public List<BookResponse> getTopBookLike() {
      var listBook  =  bookRepository.findTopBooksByLikes();
      return BookEntityMapper.toBookDTOList(listBook);
    }
}

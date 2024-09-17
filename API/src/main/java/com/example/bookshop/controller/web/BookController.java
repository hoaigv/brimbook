package com.example.bookshop.controller.web;

import com.example.bookshop.constant.FieldSort;
import com.example.bookshop.dto.ApiResponse;
import com.example.bookshop.dto.book.BookResponse;
import com.example.bookshop.dto.book.BookSearchResponse;
import com.example.bookshop.infra.spec.BookSpecification;
import com.example.bookshop.service.IBookService;
import com.example.bookshop.utils.SortUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController("WebBookController")
@RequestMapping("/api/books")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookController {
    IBookService bookService;
    private final static String DEFAULT_FILTER_SIZE = "20";
    private final static String DEFAULT_FILTER_PAGE = "0";
    private final static Sort DEFAULT_FILTER_SORT = Sort.by(Sort.Direction.ASC, "createDate");

    @GetMapping("/{bookId}")
    public ApiResponse<BookResponse> getBookById(@PathVariable int bookId) {
        return ApiResponse.<BookResponse>builder()
                .result(bookService.getBook(bookId))
                .build();
    }


    @GetMapping("/filter")
    public ApiResponse<List<BookResponse>> getListBook(
            BookSpecification filter,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_SIZE) int size,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_PAGE) int page,
            @RequestParam(required = false) List<String> sortParams,
            @RequestParam(required = false) String sortParam

    ) {
        Sort sort = DEFAULT_FILTER_SORT;
        var sortList = Arrays.asList(FieldSort.sortForBook);
        if (sortParams != null) {
            sort = SortUtils.parseSortParams(sortParams, sortList);
        }
        if (sortParam != null) {
            sort = SortUtils.parseSortParam(sortParam, sortList);
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        var result = bookService.getAllBooks(pageable, filter);
        return ApiResponse.<List<BookResponse>>builder()
                .result(result)
                .build();
    }
    @GetMapping("/filter/categories")
    public ApiResponse<List<BookResponse>> getListBookByCategories(
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_SIZE) int size,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_PAGE) int page,
            @RequestParam(required = false) List<String> categoriesParams,
            @RequestParam(required = false) List<String> sortParams,
            @RequestParam(required = false) String sortParam

    ) {
        Sort sort = DEFAULT_FILTER_SORT;
        var sortList = Arrays.asList(FieldSort.sortForBook);
        if (sortParams != null) {
            sort = SortUtils.parseSortParams(sortParams, sortList);
        }
        if (sortParam != null) {
            sort = SortUtils.parseSortParam(sortParam, sortList);
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        var result = bookService.getBooksByCategories(pageable, categoriesParams);
        return ApiResponse.<List<BookResponse>>builder()
                .result(result)
                .build();
    }


    @GetMapping("/search")
    public ApiResponse<List<BookSearchResponse>> getListBookSearch(
            BookSpecification filter
    ) {
        Pageable pageable = PageRequest.of(0, 5, DEFAULT_FILTER_SORT);
        var result = bookService.searchBooks(filter, pageable);
        return ApiResponse.<List<BookSearchResponse>>builder().result(result).build();
    }
}

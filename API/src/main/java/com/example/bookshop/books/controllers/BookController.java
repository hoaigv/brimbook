package com.example.bookshop.books.controllers;

import com.example.bookshop.utils.constants.FieldSort;
import com.example.bookshop.utils.ApiResponse;
import com.example.bookshop.books.controllers.dto.books.BookCreateRequest;
import com.example.bookshop.books.controllers.dto.books.BookCreateResponse;
import com.example.bookshop.books.controllers.dto.books.BookResponse;
import com.example.bookshop.utils.componentUtils.spec.BookSpecification;
import com.example.bookshop.books.services.IBookService;
import com.example.bookshop.utils.SortUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<ApiResponse<BookResponse>> getBookById(@PathVariable int bookId) {
       var resp =   ApiResponse.<BookResponse>builder()
               .result(bookService.getBook(bookId))
               .build();
        return ResponseEntity.ok(resp);
    }
    @PostMapping
    public ResponseEntity<ApiResponse<BookCreateResponse>> createBook(
            @RequestPart BookCreateRequest request,
            @RequestPart MultipartFile file
    ) {
        var resp = ApiResponse.<BookCreateResponse>builder()
                .result( bookService.createBook(request,file))
                .build();

        return  ResponseEntity.ok(resp);
    }
    @PutMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Void>> updateBook(

    ) {

        return ResponseEntity.ok().build();
    }


    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getListBook(
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
        var result = ApiResponse.<List<BookResponse>>builder()
                .result( bookService.getAllBooks(pageable, filter))
                .build();

        return ResponseEntity.ok(result);
    }


}

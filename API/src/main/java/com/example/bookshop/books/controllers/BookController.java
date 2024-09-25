package com.example.bookshop.books.controllers;

import com.example.bookshop.books.controllers.dto.books.BookCreateRequest;
import com.example.bookshop.books.controllers.dto.books.BookResponse;
import com.example.bookshop.books.controllers.dto.books.BookUpdateRequest;
import com.example.bookshop.books.services.IBookService;
import com.example.bookshop.utils.ApiResponse;
import com.example.bookshop.utils.SortUtils;
import com.example.bookshop.utils.componentUtils.spec.BookSpecification;
import com.example.bookshop.utils.constants.FieldSort;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import jakarta.validation.constraints.NotNull;

@RestController()
@RequestMapping("/api/books")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookController {
    IBookService bookService;
    private final static String DEFAULT_FILTER_SIZE = "5";
    private final static String DEFAULT_FILTER_PAGE = "0";
    private final static Sort DEFAULT_FILTER_SORT = Sort.by(Sort.Direction.ASC, "createdAt");

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getListBook(
           BookSpecification filter,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_SIZE) int size,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_PAGE) int page,
            @RequestParam(required = false) List<String> sortParams,
            @RequestParam(required = false) String sortParam

    ) {
        try {
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
                    .result(bookService.getAllBooks1(pageable, filter))
                    .build();
            return ResponseEntity.ok(result);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getBookMe(){
        var resp = ApiResponse.<List<BookResponse>>builder()
                .result(bookService.getBooksUser())
                .build();
        return ResponseEntity.ok(resp);

    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<BookResponse>> createBookImage(
            @RequestPart("data") BookCreateRequest request,
            @RequestPart("image") MultipartFile image
    ) {
        try {
            String fileName = image.getOriginalFilename();
            if (image.isEmpty() || !Objects.requireNonNull(fileName).endsWith(".jpg") && !fileName.endsWith(".png")) {
                var resp = ApiResponse.<BookResponse>builder()
                        .code(400)
                        .message("Bạn cần chọn file ảnh (.jpg hoặc .png)")
                        .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
            } else {
                var result = bookService.createBookImg(request, image);
                var resp = ApiResponse.<BookResponse>builder()
                        .result(result)
                        .message("Tạo sách thành công")
                        .build();
                return ResponseEntity.status(HttpStatus.CREATED).body(resp);
            }
        } catch (Exception e) {
            log.error("Lỗi khi tạo sách: ", e);
            var resp = ApiResponse.<BookResponse>builder()
                    .code(500)
                    .message("Lỗi server: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }

    @PutMapping( value = "/{bookId}")
    public ResponseEntity<ApiResponse<BookResponse>> updateBook(
            @RequestPart("data") @Valid BookUpdateRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @PathVariable("bookId") Integer bookId
    ) {
        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            BookUpdateRequest request = objectMapper.readValue(data, BookUpdateRequest.class);

            System.out.println("test 1");
            var resp = ApiResponse.<BookResponse>builder()
                    .result(bookService.updateBook(request,image, bookId))
                    .message("Successfully updated book")
                    .build();
            System.out.println("test 2");
            return  ResponseEntity.status(HttpStatus.OK).body(resp);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{bookId}")
    public ResponseEntity<ApiResponse<BookResponse>> getUserBooks(
            @PathVariable("bookId") Integer bookId
    ){
        var book = bookService.getBooksUserById(bookId);
        var resp = ApiResponse.<BookResponse>builder()
                .result(book)
                .build();
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse<BookResponse>> getBookById(
            @PathVariable Integer bookId
    ){
        var resp = ApiResponse.<BookResponse>builder()
                .result(bookService.getBookById(bookId))
                .build();
        return ResponseEntity.ok(resp);
    }


}

package com.example.bookshop.books.services;

import java.util.concurrent.CompletableFuture;

public interface IAsyncService {
     CompletableFuture<Void> processDataAsync();
}

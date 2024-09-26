package com.sb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sb.entity.BookmarkWithStockInfo;
import com.sb.entity.StockBookmark;
import com.sb.repository.StockBookmarkRepository;
import com.sb.repository.StockRepository;

@Service
@Transactional
public class StockBookmarkServiceImpl implements StockBookmarkService {

    @Autowired
    StockBookmarkRepository bookmarkRepository;

    @Autowired
    StockRepository stockRepository;

    @Override
    public void addBookmark(StockBookmark stockbookmark) {
        bookmarkRepository.addBookmark(stockbookmark);
    }

    @Override
    public List<StockBookmark> getBookmarksByUserId(Integer userId) {
        return bookmarkRepository.getBookmarksByUserId(userId);
    }

    @Override
    public void removeBookmark(Integer userId, Integer stockId) {
        bookmarkRepository.removeBookmark(userId, stockId);
    }

    @Override
    public List<BookmarkWithStockInfo> getBookmarksWithStockInfo(Integer userId) {
        return bookmarkRepository.getBookmarksWithStockInfo(userId);

    }
}
package com.sb.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sb.entity.BookmarkWithStockInfo;
import com.sb.entity.StockBookmark;

public interface StockBookmarkService {

    public void addBookmark(StockBookmark bookmark);

    public List<StockBookmark> getBookmarksByUserId(Integer userId);

    public void removeBookmark(@Param("userId") Integer userId, @Param("stockId") Integer stockId);

    public List<BookmarkWithStockInfo> getBookmarksWithStockInfo(Integer userId);

}
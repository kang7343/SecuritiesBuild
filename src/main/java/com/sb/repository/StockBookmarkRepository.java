package com.sb.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sb.entity.BookmarkWithStockInfo;
import com.sb.entity.StockBookmark;

@Mapper
public interface StockBookmarkRepository {

    public void addBookmark(StockBookmark stockbookmark);

    public List<StockBookmark> getBookmarksByUserId(Integer userId);

    public void removeBookmark(@Param("userId") Integer userId, @Param("stockId") Integer stockId);

    public List<BookmarkWithStockInfo> getBookmarksWithStockInfo(Integer userId);

}

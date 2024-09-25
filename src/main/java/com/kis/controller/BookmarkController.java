package com.kis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kis.entity.BookmarkWithStockInfo;
import com.kis.service.StockBookmarkService;

@Controller
public class BookmarkController {

    @Autowired
    private StockBookmarkService bookmarkService;

    @GetMapping("/mypage")
    public String getBookmarks(@RequestParam(name = "userId") Integer userId, Model model) {

        System.out.println("UserId: " + userId);

        List<BookmarkWithStockInfo> bookmarks = bookmarkService.getBookmarksWithStockInfo(userId);

        model.addAttribute("bookmarks", bookmarks);

        return "bookmarks";
    }

    @PostMapping("/mypage/remove")
    public String removeBookmark(@RequestParam(name = "userId") Integer userId,
            @RequestParam(name = "stockId") Integer stockId) {
        bookmarkService.removeBookmark(userId, stockId);
        return "redirect:/mypage?userId=" + userId;
    }
}

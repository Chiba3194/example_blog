package com.example.example_blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.example_blog.repository.ArticleDAO;
import com.example.example_blog.service.ArticleService;

/**
 * 記事一覧ページのコントローラ
 * 記事の一覧表示をする
 * @author Chiba
 */
@Controller
public class ShowArticlesController {

	@Autowired
	ArticleService service;

	private final String VIEW_NAME = "Show";

	/**
	 * 記事一覧ページを表示する
	 * @param model 記事一覧ページの表示に必要なものを格納する
	 * @param modelMap リダイレクト元から送られたメッセージとメッセージタイプが格納されている
	 * @return 記事一覧のHTMLファイル名
	 */
	@GetMapping(path = PathName.SHOW_ARTICLES)
	/*
	 * @ModelAttributeを使用して、リダイレクト元が設定したメッセージ・メッセージタイプの入ったModelMapオブジェクトを受け取っている
	 */
	public String show(Model model, @ModelAttribute("model")ModelMap modelMap) {


		//リダイレクト元から受け取ったメッセージを取得
		String message = (String) modelMap.get("message");

		//リダイレクト元から受け取ったメッセージタイプを取得
		MessageType messageType = (MessageType) modelMap.get("messageType");

		/*
		 * エラー回避のため、メッセージタイプがnullの場合NONEに設定しておく
		 */
		if (messageType == null) {
			messageType = MessageType.NONE;
		}

		//modelにメッセージとメッセージタイプを格納
		model.addAttribute("message", message);
		model.addAttribute("messageType", messageType);

		//全ての記事を取得
		List<ArticleDAO> articles = service.getAllArticles();

		//modelに記事リストを格納
		model.addAttribute("articles", articles);

		return VIEW_NAME;

	}
}

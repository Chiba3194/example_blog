package com.example.example_blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.example_blog.service.ArticleService;

/**
 * 記事作成ページのコントローラ
 * 記事を作成する。
 * @author Chiba
 */
@Controller
public class CreateArticleController {

	@Autowired
	ArticleService service;

	private final String VIEW_NAME = "Create";

	/**
	 * 記事一覧ページで作成ボタンが押されたとき、
	 * 記事作成ページを表示する。
	 * @param model 記事作成ページの表示に必要なものを格納する
	 * @return 記事作成のHTMLファイル名
	 */
	@GetMapping(path = PathName.CREATE_ARTICLE)
	public String init(Model model) {

		//タイトル・本文・メッセージにそれぞれ空値を格納
		model.addAttribute("title", "");
		model.addAttribute("content", "");
		model.addAttribute("message", "");
		model.addAttribute("messageType", MessageType.NONE);

		return VIEW_NAME;
	}

	/**
	 * 記事作成ページで作成ボタンが押されたとき,記事を作成する。
	 * 作成に成功した場合は記事一覧ページを表示し、
	 * 失敗した場合は記事作成ページを表示する。
	 * @param model 記事作成ページの再表示に必要なものを格納する
	 * @param redirectAttributes 記事一覧ページにリダイレクトする際に送るものを格納する
	 * @param title 入力されたタイトル
	 * @param content 入力された本文
	 * @return 結果を表示するHTMLファイル名
	 */
	@GetMapping(path = PathName.CREATE_ARTICLE, params = "create")
	public String create(Model model,
			RedirectAttributes redirectAttributes,
			@ModelAttribute("title") String title,
			@ModelAttribute("content") String content) {

		//入力チェックが通らなかった時のために入力内容を格納する
		model.addAttribute("title", title);
		model.addAttribute("content", content);

		//入力チェック
		//タイトルの最大文字数
		final int MAX_TITLE_SIZE = 30;

		//本文の最大文字数
		final int MAX_CONTENT_SIZE = 1000;

		//タイトルがnullでも空値でもないことを確認する
		if (title == null || title.equals("")) {
			model.addAttribute("message", "タイトルが入力されていません。");
			model.addAttribute("messageType", MessageType.ALERT);
			return VIEW_NAME;
		}

		//本文がnullでも空値でもないことを確認する
		if (content == null || content.equals("")) {
			model.addAttribute("message", "本文が入力されていません。");
			model.addAttribute("messageType", MessageType.ALERT);
			return VIEW_NAME;
		}

		//タイトルの最大文字数チェック
		if (title.length() > MAX_TITLE_SIZE) {
			model.addAttribute("message", "タイトルは" + MAX_TITLE_SIZE + "字以内で入力してください。");
			model.addAttribute("messageType", MessageType.ALERT);
			return VIEW_NAME;
		}

		//本文の最大文字数チェック
		if (content.length() > MAX_CONTENT_SIZE) {
			model.addAttribute("message", "本文は" + MAX_CONTENT_SIZE + "字以内で入力してください。");
			model.addAttribute("messageType", MessageType.ALERT);
			return VIEW_NAME;
		}

		//記事の作成
		service.addArticle(title, content);

		/*
		 * 記事作成の成功メッセージを格納する
		 */
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("message", "記事が作成されました。");
		modelMap.addAttribute("messageType", MessageType.INFORMATION);

		/*
		 * 格納したメッセージをリダイレクト先が取り出せるように、
		 * RedirectAttributesにメッセージを格納する
		 */
		redirectAttributes.addFlashAttribute("model", modelMap);

		/*
		 * 記事一覧ページを表示させるためにリダイレクトを行う
		 * returnする文字列は HTMLファイル名 ではなく、 redirect:リダイレクト先コントローラのパス名 にする。
		 *
		 * リダイレクト時の処理の流れについて
		 * ×：
		 *   作成ボタン押下→このコントローラ→
		 *   記事一覧コントローラに直接向かう→
		 *   記事一覧ページの表示
		 *
		 * ○：
		 *   作成ボタン押下→このコントローラ→
		 *   「記事一覧コントローラにリクエストを出してね」という意味のメッセージを呼び出し元に返す→
		 *   呼び出し元は記事一覧コントローラにリクエスト発行→
		 *   記事一覧ページの表示
		 */
		return "redirect:" + PathName.SHOW_ARTICLES;
	}

}

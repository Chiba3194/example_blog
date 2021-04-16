package com.example.example_blog.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.example_blog.repository.ArticleDAO;
import com.example.example_blog.service.AcquisitionFailedException;
import com.example.example_blog.service.ArticleService;
import com.example.example_blog.service.DeleteFailedException;
import com.example.example_blog.service.UpdateFailedException;

/**
 * 記事編集ページのコントローラ
 * 記事の編集・削除をする。
 * @author Chiba
 */
@Controller
public class ModifyArticleController {

	@Autowired
	ArticleService service;

	private final String VIEW_NAME = "Modify";

	/**
	 * 記事一覧ページで編集ボタンが押されたとき、
	 * 記事編集ページを表示する。
	 * @param model 記事編集ページの表示に必要なものを格納する
	 * @param redirectAttributes 記事一覧ページにリダイレクトする際に送るものを格納する
	 * @param id 入力された編集対象の記事ID
	 * @return 結果を表示するHTMLファイル名
	 */
	@GetMapping(path = PathName.MODIFY_ARTICLE)
	public String init(Model model,
			RedirectAttributes redirectAttributes,
			@ModelAttribute("id") int id) {

		//入力チェック
		//IDが1未満でないことを確認する。
		if (id < 1) {
			throw new IllegalArgumentException("IDが1未満です");
		}

		try {

			//編集対象の記事オブジェクトを取得する
			ArticleDAO article = service.getArticle(id);

			//modelに記事を格納
			model.addAttribute("article", article);
			model.addAttribute("message", "");
			model.addAttribute("messageType", MessageType.NONE);

			return VIEW_NAME;

		}
		//記事の取得に失敗した場合
		catch (AcquisitionFailedException e) {

			//記事取得の失敗メッセージを格納する。
			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("message", "対象の記事が存在しません。");
			modelMap.addAttribute("messageType", MessageType.ALERT);

			redirectAttributes.addFlashAttribute("model", modelMap);

			return "redirect:" + PathName.SHOW_ARTICLES;

		}
	}

	/**
	 * 記事編集ページで編集ボタンが押されたとき、記事を更新する。
	 * 入力に不備があるなら記事編集ページを表示し、
	 * 更新に成功した場合は記事一覧ページを表示する。
	 * 更新時にエラーが発生した場合も記事一覧ページを表示する。
	 * @param model 記事編集ページの表示に必要なものを格納する
	 * @param redirectAttributes 記事一覧ページにリダイレクトする際に送るものを格納する
	 * @param id 入力された編集対象の記事ID
	 * @param date 入力された日付
	 * @param title 入力された編集後のタイトル
	 * @param content 入力された編集語の本文
	 * @return 結果を表示するHTMLファイル名
	 */
	@GetMapping(path = PathName.MODIFY_ARTICLE, params = "modify")
	public String modify(Model model,
			RedirectAttributes redirectAttributes,
			@ModelAttribute("id") int id,
			//String型で受け取って後でValueOfに変換する。
			@ModelAttribute("date") String date,
			@ModelAttribute("title") String title,
			@ModelAttribute("content") String content) {

		//入力チェックが通らなかった時のために入力内容を格納する
		ArticleDAO article = new ArticleDAO();
		article.setId(id);
		article.setDate(Timestamp.valueOf(date));
		article.setTitle(title);
		article.setContent(content);

		model.addAttribute("article", article);

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


		try {

			//記事を更新
			service.modifyArticle(id, title, content);

			//記事更新の完了メッセージを格納する。
			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("message", "記事が編集されました。");
			modelMap.addAttribute("messageType", MessageType.INFORMATION);

			redirectAttributes.addFlashAttribute("model", modelMap);

			return "redirect:" + PathName.SHOW_ARTICLES;

		}
		//記事の編集に失敗した場合
		catch (UpdateFailedException e) {
			//記事更新の失敗メッセージを格納する。
			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("message", "対象の記事が存在しません。");
			modelMap.addAttribute("messageType", MessageType.ALERT);

			redirectAttributes.addFlashAttribute("model", modelMap);

			return "redirect:" + PathName.SHOW_ARTICLES;

		}
	}

	/**
	 * 記事編集ページで削除ボタンが押されたとき、記事を削除する。
	 * 削除に成功してもエラーが発生しても記事一覧ページを表示する。
	 * @param redirectAttributes 記事一覧ページにリダイレクトする際に送るものを格納する
	 * @param id 入力された削除対象の記事ID
	 * @return 記事一覧のHTMLファイル名
	 */
	@GetMapping(path = PathName.MODIFY_ARTICLE, params = "delete")
	public String delete(RedirectAttributes redirectAttributes,
			@ModelAttribute("id") int id) {

		//入力チェック
		//IDが1未満でないことを確認する。
		if (id < 1) {
			throw new IllegalArgumentException("IDが1未満です");
		}

		try {

			//記事の削除
			service.deleteArticle(id);

			//記事削除の完了メッセージを格納する。
			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("message", "記事が削除されました。");
			modelMap.addAttribute("messageType", MessageType.INFORMATION);

			redirectAttributes.addFlashAttribute("model", modelMap);

			return "redirect:" + PathName.SHOW_ARTICLES;

		}
		//記事の削除に失敗した場合
		catch (DeleteFailedException e) {
			//記事削除の失敗メッセージを格納する。
			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("message", "対象の記事が存在しません。");
			modelMap.addAttribute("messageType", MessageType.ALERT);

			redirectAttributes.addFlashAttribute("model", modelMap);

			return "redirect:" + PathName.SHOW_ARTICLES;

		}

	}

}

package com.example.example_blog.repository;

import java.util.List;

/**
 * 記事リポジトリ
 * 記事を管理するデータベースに対してSQL文を実行する。
 * @author Chiba
 */
public interface ArticleRepository {
	/**
	 * データベースに記事を追加
	 * @param title 記事のタイトル
	 * @param content 記事の本文
	 */
	public void addArticle(String title, String content);


	/**
	 * データベースの記事を更新
	 * @param id 記事のID
	 * @param title 記事のタイトル
	 * @param content 記事の本文
	 * @throws NoArticleFoundException 更新件数が0件だったときにスローする例外
	 */
	public void modifyArticle(int id, String title, String content) throws NoArticleFoundException;


	/**
	 * データベースの記事を取得
	 * @param id 記事のID
	 * @return 記事オブジェクト
	 * @throws NoArticleFoundException 取得に失敗したときにスローする例外
	 */
	public ArticleDAO getArticle(int id) throws NoArticleFoundException;


	/**
	 * データベースから全ての記事を取得
	 * @return 記事のリスト
	 */
	public List<ArticleDAO> getAllArticles();


	/**
	 * データベースの記事を削除
	 * @param id 記事のID
	 * @throws NoArticleFoundException 削除件数が0件だったときにスローする例外
	 */
	public void deleteArticle(int id) throws NoArticleFoundException;

}

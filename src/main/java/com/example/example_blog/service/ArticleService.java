package com.example.example_blog.service;

import java.util.List;

import com.example.example_blog.repository.ArticleDAO;

/**
 * 記事管理サービス
 * 記事を管理する
 * @author Chiba
 */
public interface ArticleService {

	/**
	 * データベースに記事を追加する
	 * @param title 記事のタイトル
	 * @param content 記事の本文
	 */
	public void addArticle(String title, String content);

	/**
	 * データベースの記事を更新する
	 * @param id 記事のID
	 * @param title 記事のタイトル
	 * @param content 記事の本文
	 * @throws UpdateFailedException 指定されたIDの記事が見つからなかった場合にスローする例外
	 */
	public void modifyArticle(int id, String title, String content) throws UpdateFailedException;

	/**
	 * データベースの記事を取得する
	 * @param id 記事のID
	 * @return 記事オブジェクト
	 * @throws AcquisitionFailedException 指定されたIDの記事が見つからなかった場合にスローする例外
	 */
	public ArticleDAO getArticle(int id) throws AcquisitionFailedException;

	/**
	 * データベースの全記事を取得する
	 * @return 記事のリスト
	 */
	public List<ArticleDAO> getAllArticles();

	/**
	 * データベースの記事を削除する
	 * @param id 記事のID
	 * @throws DeleteFailedException 指定されたIDの記事が見つからなかった場合にスローする例外
	 */
	public void deleteArticle(int id) throws DeleteFailedException;

}

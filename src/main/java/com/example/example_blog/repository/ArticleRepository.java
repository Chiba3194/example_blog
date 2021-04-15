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
	public void addPost(String title, String content);


	/**
	 * データベースの記事を更新
	 * @param id 記事のID
	 * @param title 記事のタイトル
	 * @param content 記事の本文
	 * @throws NoPostFoundException 更新件数が0件だったときにスローする例外
	 */
	public void modifyPost(int id, String title, String content) throws NoPostFoundException;


	/**
	 * データベースの記事を取得
	 * @param id 記事のID
	 * @return 記事オブジェクト
	 * @throws NoPostFoundException 取得に失敗したときにスローする例外
	 */
	public ArticleDAO getPost(int id) throws NoPostFoundException;


	/**
	 * データベースから全ての記事を取得
	 * @return 記事のリスト
	 */
	public List<ArticleDAO> getAllPosts();


	/**
	 * データベースの記事を削除
	 * @param id 記事のID
	 * @throws NoPostFoundException 削除件数が0件だったときにスローする例外
	 */
	public void deletePost(int id) throws NoPostFoundException;

}

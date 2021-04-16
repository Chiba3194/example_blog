package com.example.example_blog.controller;

/**
 * コントローラのパス名を保持するクラス
 * ShowArticlesControllerのパスは複数クラスで使用するので、
 * 共通して参照できる場所に定数化しておく
 * @author Chiba
 */
public class PathName {

	//CreateArticleControllerのパス
	public static final String CREATE_ARTICLE = "/CreateArticle";

	//ModifyArticleControllerのパス
	public static final String MODIFY_ARTICLE = "/ModifyArticle";

	//ShowArticlesControllerのパス
	public static final String SHOW_ARTICLES = "/ShowArticles";

}

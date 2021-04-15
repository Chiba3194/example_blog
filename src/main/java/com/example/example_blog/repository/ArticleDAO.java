package com.example.example_blog.repository;

import java.sql.Timestamp;

/**
 * 記事を表すオブジェクト
 * @author Chiba
 */
public class ArticleDAO {

	//記事のID
	private int id;

	//記事のタイトル
	private String title;

	//記事の投稿日
	private Timestamp date;

	//記事の本文
	private String content;


	//IDのゲッタ
	public int getId() {
		return id;
	}

	//IDのセッタ
	public void setId(int id) {
		this.id = id;
	}

	//タイトルのゲッタ
	public String getTitle() {
		return title;
	}

	//タイトルのセッタ
	public void setTitle(String title) {
		this.title = title;
	}

	//投稿日のゲッタ
	public Timestamp getDate() {
		return date;
	}

	//投稿日のセッタ
	public void setDate(Timestamp date) {
		this.date = date;
	}

	//本文のゲッタ
	public String getContent() {
		return content;
	}

	//本文のセッタ
	public void setContent(String content) {
		this.content = content;
	}
}

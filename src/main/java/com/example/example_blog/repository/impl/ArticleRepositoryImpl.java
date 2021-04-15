package com.example.example_blog.repository.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.example_blog.repository.ArticleDAO;
import com.example.example_blog.repository.ArticleRepository;
import com.example.example_blog.repository.NoPostFoundException;

/**
 * 記事リポジトリを実装するクラス
 * @author Chiba
 */
/*
 * @Repository：ArticleRepositoryImpl.java がリポジトリであることを示すアノテーション
 */
@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	//タイトルの最大文字数
	private final int MAX_TITLE_SIZE = 30;

	//本文の最大文字数
	private final int MAX_CONTENT_SIZE = 1000;

	/**
	 * {@inheritDoc}
	 */
	/*
	 * @inheritDoc：スーパクラスやインタフェースのドキュメントコメントを継承する
	 */
	@Override
	public void addPost(String title, String content) {

		//タイトルがnullでも空値でもないことを確認する。
		if (title == null || title.equals("")) {
			throw new IllegalArgumentException("タイトルがnullもしくは空値です");
		}

		//本文がnullでも空値でもないことを確認する。
		if (content == null || content.equals("")) {
			throw new IllegalArgumentException("本文がnullもしくは空値です");
		}

		//タイトルの最大文字数チェック
		if (title.length() > MAX_TITLE_SIZE) {
			throw new IllegalArgumentException("タイトルが" + MAX_TITLE_SIZE + "字より多いです");
		}

		//本文の最大文字数チェック
		if (content.length() > MAX_CONTENT_SIZE) {
			throw new IllegalArgumentException("本文が" + MAX_TITLE_SIZE + "字より多いです");
		}


		/*
		 * 記事を追加するSQL文の設定
		 *
		 * :title ... "title"という名前のパラメータを:titleに当てはめる
		 * :content ... "content"という名前のパラメータを:titleに当てはめる
		 *
		 * now() ... PostgreSQLの関数
		 *           PostgreSQLの現在時刻を取得する
		 *
		 */
		final String sql = "INSERT INTO articles (title, date, content) VALUES (:title, now(), :content);";

		/*
		 * SQL文に入れる引数のリスト化
		 *
		 * パラメータとして以下の内容を設定している。
		 *
		 * "title"という名前のパラメータに 変数title を当てはめ、
		 * "content"という名前のパラメータに 変数content を当てはめる。
		 *
		 */
		SqlParameterSource parameters = new MapSqlParameterSource("title", title)
				.addValue("content", content);

		/*
		 * SQL文と当てはめるパラメータを引数に渡し、SQL文を実行する。
		 */
		jdbcTemplate.update(sql, parameters);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyPost(int id, String title, String content) throws NoPostFoundException {

		//タイトルがnullでも空値でもないことを確認する。
		if (title == null || title.equals("")) {
			throw new IllegalArgumentException("タイトルがnullもしくは空値です");
		}

		//本文がnullでも空値でもないことを確認する。
		if (content == null || content.equals("")) {
			throw new IllegalArgumentException("本文がnullもしくは空値です");
		}

		//タイトルの最大文字数チェック
		if (title.length() > MAX_TITLE_SIZE) {
			throw new IllegalArgumentException("タイトルが" + MAX_TITLE_SIZE + "字より多いです");
		}

		//本文の最大文字数チェック
		if (content.length() > MAX_CONTENT_SIZE) {
			throw new IllegalArgumentException("本文が" + MAX_TITLE_SIZE + "字より多いです");
		}

		//IDが1未満でないことを確認する。
		if (id < 1) {
			throw new IllegalArgumentException("IDが1未満です");
		}


		//記事を更新するSQL文を設定
		final String sql = "UPDATE posts set title = :title, content = :content WHERE id = :id;";

		//SQL文に入れる引数のリスト化
		SqlParameterSource parameters = new MapSqlParameterSource("title", title)
				.addValue("content", content)
				.addValue("id", id);

		//SQL文を実行し、更新件数を取得する。
		int count = jdbcTemplate.update(sql, parameters);

		//更新件数が0だった場合
		//更新に失敗したとしてNoPostFoundExceptionをスローする
		if (count == 0) {
			throw new NoPostFoundException();
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArticleDAO getPost(int id) throws NoPostFoundException {

		//IDが1未満でないことを確認する。
		if (id < 1) {
			throw new IllegalArgumentException("IDが1未満です");
		}


		//呼び出し元に返却する記事オブジェクト
		ArticleDAO dao = null;

		try {

			//記事を取得するSQLの設定
			final String sql = "SELECT * FROM posts WHERE id = :id;";

			//SQL文に入れる引数のリスト化
			SqlParameterSource parameters = new MapSqlParameterSource("id", id);

			//取得処理を実行
			Map<String, Object> result = jdbcTemplate.queryForMap(sql, parameters);

			//取得結果をオブジェクトに変換
			dao = toArticleDAO(result);

		} catch (IncorrectResultSizeDataAccessException e) {
			//IncorrectResultSizeDataAccessException SQL文の実行結果が1行でない場合スローされる

			//結果が取得できなかったとしてNoPostFoundExceptionをスローする
			throw new NoPostFoundException();
		}

		//記事を返却
		return dao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ArticleDAO> getAllPosts() {

		//全ての記事を取得するSQLの設定
		final String sql = "SELECT * FROM posts ORDER BY date DESC;";

		//SQL文に入れる引数のリスト化（引数なし）
		SqlParameterSource parameters = new MapSqlParameterSource();

		//取得処理を実行
		List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, parameters);

		//検索結果を記事オブジェクトのリストに変換する
		//1個もなければ要素数0の記事リストを作る
		List<ArticleDAO> postList = new ArrayList<>();

		for (Map<String, Object> result : resultList) {

			//検索結果を記事オブジェクトに変換
			ArticleDAO dao = toArticleDAO(result);

			//記事オブジェクトを記事リストに追加
			postList.add(dao);
		}

		//記事リストを返却
		return postList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deletePost(int id) throws NoPostFoundException {

		//IDが1未満でないことを確認する。
		if (id < 1) {
			throw new IllegalArgumentException("IDが1未満です");
		}


		//記事を削除するSQL文を設定
		final String sql = "DELETE FROM posts WHERE id = :id;";

		//SQL文に入れる引数のリスト化
		SqlParameterSource parameters = new MapSqlParameterSource("id", id);

		//SQL文を実行し、削除件数を取得する。
		int count = jdbcTemplate.update(sql, parameters);

		//削除件数が0だった場合
		//削除に失敗したとしてNoPostFoundExceptionをスローする
		if (count == 0) {
			throw new NoPostFoundException();
		}

	}

	/*
	 * 検索結果を記事オブジェクトに変換する処理は一件取得・全件取得の両方にある。
	 * そのため、処理をメソッド化して一か所にまとめておく。
	 */
	/**
	 * 取得した記事を記事オブジェクトに変換する
	 * @param result 記事の取得結果
	 * @return 記事オブジェクト
	 */
	private ArticleDAO toArticleDAO(Map<String, Object> result) {

		ArticleDAO dao = new ArticleDAO();

		//IDのセット
		dao.setId((int) result.get("id"));

		//タイトルのセット
		dao.setTitle((String) result.get("title"));

		//日付のセット
		dao.setDate((Timestamp) result.get("date"));

		//本文のセット
		dao.setContent((String) result.get("content"));

		return dao;
	}

}

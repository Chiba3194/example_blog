package com.example.example_blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.example_blog.repository.ArticleDAO;
import com.example.example_blog.repository.ArticleRepository;
import com.example.example_blog.repository.NoPostFoundException;
import com.example.example_blog.service.AcquisitionFailedException;
import com.example.example_blog.service.ArticleService;
import com.example.example_blog.service.DeleteFailedException;
import com.example.example_blog.service.UpdateFailedException;

/**
 * 記事管理サービスの実装クラス
 * @author Chiba
 */
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleRepository articleRepository;

	//タイトルの最大文字数
	private final int MAX_TITLE_SIZE = 30;

	//本文の最大文字数
	private final int MAX_CONTENT_SIZE = 1000;


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPost(String title, String content) {

		//タイトルがnullでも空値でもないことを確認する。
		if (title == null || title.equals("")) {

			// この例外メッセージは画面表示には使用しないが、例外発生時の原因調査に使えるので書いておく
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

		//記事を追加
		articleRepository.addPost(title, content);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyPost(int id, String title, String content) throws UpdateFailedException {

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


		try {
			//記事の更新
			articleRepository.modifyPost(id, title, content);

		} catch (NoPostFoundException e) {
			//指定されたIDの記事が見つからなかった場合
			/*
			 * スローする例外オブジェクトに、この例外をスローすることになった直接原因の例外情報を保持させる
			 * こうすることで、例外が発生したときにどこで発生したのか、大元をたどって探すことができる
			 */
			throw new UpdateFailedException(e);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArticleDAO getPost(int id) throws AcquisitionFailedException {

		//IDが1未満でないことを確認する。
		if (id < 1) {
			throw new IllegalArgumentException("IDが1未満です");
		}

		try {
			//記事の取得
			ArticleDAO dao = articleRepository.getPost(id);

			//記事の返却
			return dao;

		} catch (NoPostFoundException e) {
			//指定されたIDの記事が見つからなかった場合
			throw new AcquisitionFailedException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ArticleDAO> getAllPosts() {

		//記事リストの取得
		List<ArticleDAO> daoList = articleRepository.getAllPosts();

		//記事リストの返却
		return daoList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deletePost(int id) throws DeleteFailedException {

		//IDが1未満でないことを確認する。
		if (id < 1) {
			throw new IllegalArgumentException("IDが1未満です");
		}

		try {
			//削除の実行
			articleRepository.deletePost(id);

		} catch (NoPostFoundException e) {
			//指定されたIDの記事が見つからなかった場合
			throw new DeleteFailedException(e);

		}
	}
}

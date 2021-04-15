package com.example.example_blog.service;

/**
 * 記事の削除に失敗した例外
 * @author Chiba
 */
public class DeleteFailedException extends Exception {

	/**
	 * 内部例外を保持するコンストラクタ
	 * @param cause キャッチした例外
	 */
	public DeleteFailedException(Throwable cause) {
		super(cause);
	}
}

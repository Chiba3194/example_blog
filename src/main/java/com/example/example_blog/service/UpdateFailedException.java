package com.example.example_blog.service;

/**
 * 記事の更新に失敗した例外
 * @author Chiba
 */
public class UpdateFailedException extends Exception {

	/**
	 * 内部例外を保持するコンストラクタ
	 * @param cause キャッチした例外
	 */
	public UpdateFailedException(Throwable cause) {
		super(cause);
	}
}

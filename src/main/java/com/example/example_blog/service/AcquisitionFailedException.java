package com.example.example_blog.service;

/**
 * 記事の取得に失敗した例外
 * @author Chiba
 */
public class AcquisitionFailedException extends Exception {

	/**
	 * 内部例外を保持するコンストラクタ
	 * @param cause キャッチした例外
	 */
	public AcquisitionFailedException(Throwable cause) {
		super(cause);
	}
}

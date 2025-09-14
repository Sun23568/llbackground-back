package com.llback.common.types;

import java.util.regex.Pattern;

/**
 * css @2024
 * TokenId
 *
 * @author hex
 * @date 2023/11/9 18:31
 */

public class TokenId extends StringId {

    /**
     * TokenId 正则表达式
     */
    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9_.@-]+$");

    /**
     * 空的TokenId
     */
    public static final TokenId EMPTY_TOKEN = new TokenId();

    /**
     * 空的TokenId
     */
    protected TokenId() {
        super();
    }

    /**
     * 构造函数
     *
     * @param tokenId tokenId
     */
    protected TokenId(CharSequence tokenId) {
        super(tokenId, PATTERN, 2048, "TokenId");
    }

    /**
     * 构造函数
     *
     * @param tokenId tokenId
     * @param tag     错误信息
     */
    protected TokenId(CharSequence tokenId, int maxLen, String tag) {
        super(tokenId, PATTERN, maxLen, tag);
    }


    /**
     * 构造函数
     *
     * @param tokenId tokenId
     * @return TokenId
     */
    public static TokenId of(CharSequence tokenId) {
        return new TokenId(tokenId);
    }

    /**
     * 构造函数
     *
     * @param tokenId tokenId
     * @param tag     错误信息
     * @return TokenId
     */
    public static TokenId of(CharSequence tokenId, int maxLen, String tag) {
        return new TokenId(tokenId, maxLen, tag);
    }
}

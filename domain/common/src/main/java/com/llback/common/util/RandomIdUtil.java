package com.llback.common.util;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * css @2024
 * 随机ID生成工具类
 *
 * @author hex
 * @date 2023/11/11 21:54
 */
@UtilityClass
public class RandomIdUtil {

    /**
     * The default random number generator used by this class.
     * Creates cryptographically strong NanoId Strings.
     */
    private static final SecureRandom DEFAULT_NUMBER_GENERATOR = new SecureRandom();


    /**
     * 区分大小写的ID字符集，默认使用
     * 注意：如果使用该ID值存储到数据库表作为主键，需要将字符集编码设置为大小写敏感！！！
     */
    private static final char[] CASE_SENSITIVE_ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_".toCharArray();


    /**
     * 纯小写的ID字符集
     */
    private static final char[] CASE_INSENSITIVE_ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyz_".toCharArray();

    /**
     * 纯数字的ID字符集
     */
    private static final char[] NUMBERS = "0123456789".toCharArray();


    /**
     * 21位长度的NanoId
     */
    public static String nanoId() {
        return nanoId(21);
    }

    /**
     * 21位长度的NanoId
     *
     * @param ignoreCase 是否忽略大小写
     */
    public static String nanoId(boolean ignoreCase) {
        return nanoId(21, ignoreCase);
    }

    /**
     * 获取指定长度的NanoId
     *
     * @param size 长度
     */
    public static String nanoId(int size) {
        return NanoId.randomNanoId(DEFAULT_NUMBER_GENERATOR, CASE_INSENSITIVE_ALPHABET, size);
    }

    /**
     * 获取指定长度的纯数字NanoId
     *
     * @param size 长度
     */
    public static String nanoNumId(int size) {
        return NanoId.randomNanoId(DEFAULT_NUMBER_GENERATOR, NUMBERS, size);
    }

    /**
     * 获取指定长度的NanoId
     *
     * @param size       长度
     * @param ignoreCase 是否忽略大小写
     */
    public static String nanoId(int size, boolean ignoreCase) {
        final char[] alphabet = ignoreCase ? CASE_INSENSITIVE_ALPHABET : CASE_SENSITIVE_ALPHABET;
        return NanoId.randomNanoId(DEFAULT_NUMBER_GENERATOR, alphabet, size);
    }

    /**
     * 获取UUID(32位长度，无中划线)
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * NanoId is a URL-friendly, unique string ID generator for Java.
     * 注意问题：熵源不足时阻塞问题
     * 概念回顾：
     * "熵值"：即是随机值的不确定性度量值。
     * "熵源"：即是随机数的来源。
     * "熵输入"：是伪随机数产生器描述从熵源获取的bit串，用来产生种子。
     * "种子"：即是输入到伪随机数产生器用于初始化的bit串。
     * 问题描述
     * 在Linux系统中，/dev/random是系统提供的安全随机数接口。当通过/dev/random读取随机数的速度可以为产品所接受时，可以直接使用/dev/random读取的随机数。
     * 有时无法满足产品对随机数的使用要求，熵源不足时存在阻塞，会导致得到随机数的速度太慢。
     * 在读取时，/dev/random设备会返回小于熵池噪声总数的随机字节。/dev/random可生成高随机性的公钥或一次性密码本。若熵池空了，对/dev/random的读操作将会被阻塞，直到收集到了足够的环境噪声为止。
     * <p>
     * 解决方法
     * 提高系统随机数产生器产生随机数速度的一种方法：
     * <a href="https://www.cnblogs.com/arci/p/14977324.html">采用haveged守护进程增加系统熵池熵值以提高/dev/random读取随机数的速度。</a>
     */
    private static final class NanoId {

        /**
         * Static factory to retrieve a NanoId String.
         * <p>
         * The string is generated using the given random number generator.
         *
         * @param random   The random number generator.
         * @param alphabet The symbols used in the NanoId String.
         * @param size     The number of symbols in the NanoId String.
         * @return A randomly generated NanoId String.
         */
        public static String randomNanoId(final SecureRandom random, final char[] alphabet, final int size) {
            AssertUtil.notNull(random, "random cannot be null.");
            AssertUtil.notNull(alphabet, "alphabet cannot be null.");
            AssertUtil.assertTrue(size > 0, "size must be greater than zero.");
            AssertUtil.assertTrue(alphabet.length > 0 && alphabet.length < 256, "alphabet must contain between 1 and 255 symbols.");

            double floor = Math.floor(Math.log((double) alphabet.length - 1) / Math.log(2));
            final int mask = (2 << (int) floor) - 1;
            final int step = (int) Math.ceil(1.6 * mask * size / alphabet.length);

            final StringBuilder idBuilder = new StringBuilder();

            while (idBuilder.length() != size) {
                final byte[] bytes = new byte[step];
                random.nextBytes(bytes);

                for (int i = 0; i < step; i++) {
                    final int alphabetIndex = bytes[i] & mask;

                    if (alphabetIndex < alphabet.length) {
                        idBuilder.append(alphabet[alphabetIndex]);
                        if (idBuilder.length() == size) {
                            break;
                        }
                    }
                }
            }
            return idBuilder.toString();
        }
    }
}

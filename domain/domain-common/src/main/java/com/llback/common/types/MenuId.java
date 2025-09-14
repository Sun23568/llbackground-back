package com.llback.common.types;

/**
 * 菜单ID
 *
 * @author yz.sun
 * @date 2024/4/28
 */
public class MenuId extends StringId {
    /**
     * 默认最大长度。
     */
    private static final int DEF_MAX_LEN = 32;

    /**
     * 构造函数
     *
     * @param menuId 菜单ID
     */
    protected MenuId(CharSequence menuId) {
        super(menuId, DEF_MAX_LEN, "菜单ID");
    }

    /**
     * 创建文件名
     *
     * @param menuId 文件名
     * @return 文件名
     */
    public static MenuId of(CharSequence menuId) {
        return new MenuId(menuId);
    }
}

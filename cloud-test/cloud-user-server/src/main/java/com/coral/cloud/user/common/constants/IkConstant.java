package com.coral.cloud.user.common.constants;

/**
 * @author huss
 * @version 1.0
 * @className IkConstant
 * @description 分词常量
 * @date 2022/5/6 15:58
 */
public interface IkConstant {
    /**
     * 会将文本做最粗粒度（能一次拆分就不两次拆分）的拆分，例如「中华人民共和国国歌」会被拆分为「中华人民共和国、国歌」
     */
    String IK_SMART = "ik_smart";

    /**
     * 会将文本做最细粒度（拆到不能再拆）的拆分，
     * 例如「中华人民共和国国歌」会被拆分为「中华人民共和国、中华人民、中华、华人、人民共和国、人民、人、民、共和国、共和、和、国国、国歌」，会穷尽各种可能的组合
     */
    String IK_MAX_WORD = "ik_max_word";


}

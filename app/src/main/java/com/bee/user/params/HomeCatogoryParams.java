package com.bee.user.params;

/**
 * 创建时间：2021/11/21
 *
 * @Author： 陈陈陈
 * 功能描述：
 */
public class HomeCatogoryParams {

    public DataBean data;
    public int pageNum;
    public int pageSize;

    public static class DataBean {
        public int categoryId;
        //1-综合；2-新品；3-销量；4-价格从高到低；5-价格从低到高
        public int sortType;

        public DataBean(int categoryId, int sortType) {
            this.categoryId = categoryId;
            this.sortType = sortType;
        }
    }
}

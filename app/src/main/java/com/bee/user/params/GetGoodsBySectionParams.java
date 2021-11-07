package com.bee.user.params;

/**
 - @Description:
 - @Author: bxy
 - @Time:  2021/11/7 下午3:50
 */
public class GetGoodsBySectionParams {
    public int pageNum;
    public int pageSize;
    public GetGoodsBySectionData data;

    public static class GetGoodsBySectionData{
        public Integer getTimeSectionId() {
            return timeSectionId;
        }

        public void setTimeSectionId(Integer timeSectionId) {
            this.timeSectionId = timeSectionId;
        }

        public GetGoodsBySectionData(Integer timeSectionId) {
            this.timeSectionId = timeSectionId;
        }

        public Integer timeSectionId;
    }
}

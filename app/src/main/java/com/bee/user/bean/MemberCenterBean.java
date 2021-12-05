package com.bee.user.bean;

import java.io.Serializable;
import java.util.List;

/**
 - @Description:
 - @Author: bxy
 - @Time:  2021/12/5 下午2:20
 */
public class MemberCenterBean implements Serializable {
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getMemberIdentitId() {
        return memberIdentitId;
    }

    public void setMemberIdentitId(int memberIdentitId) {
        this.memberIdentitId = memberIdentitId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelValue() {
        return levelValue;
    }

    public void setLevelValue(String levelValue) {
        this.levelValue = levelValue;
    }

    public String getLevelIcon() {
        return levelIcon;
    }

    public void setLevelIcon(String levelIcon) {
        this.levelIcon = levelIcon;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNowGrowResult() {
        return nowGrowResult;
    }

    public void setNowGrowResult(String nowGrowResult) {
        this.nowGrowResult = nowGrowResult;
    }

    public String getLevelUpGrow() {
        return levelUpGrow;
    }

    public void setLevelUpGrow(String levelUpGrow) {
        this.levelUpGrow = levelUpGrow;
    }

    public String getLevelUpGrowDesc() {
        return levelUpGrowDesc;
    }

    public void setLevelUpGrowDesc(String levelUpGrowDesc) {
        this.levelUpGrowDesc = levelUpGrowDesc;
    }

    public String getStartGrowScope() {
        return startGrowScope;
    }

    public void setStartGrowScope(String startGrowScope) {
        this.startGrowScope = startGrowScope;
    }

    public String getEndGrowScope() {
        return endGrowScope;
    }

    public void setEndGrowScope(String endGrowScope) {
        this.endGrowScope = endGrowScope;
    }

    public List<PrivilegeVOBean> getPrivilegeVOList() {
        return privilegeVOList;
    }

    public void setPrivilegeVOList(List<PrivilegeVOBean> privilegeVOList) {
        this.privilegeVOList = privilegeVOList;
    }

    public List<RuleVOBean> getRuleVOList() {
        return ruleVOList;
    }

    public void setRuleVOList(List<RuleVOBean> ruleVOList) {
        this.ruleVOList = ruleVOList;
    }

    private int memberId;
    private int memberIdentitId;
    private String levelName;
    private String levelValue;
    private String levelIcon;
    private String startTime;
    private String endTime;
    private String nowGrowResult;
    private String levelUpGrow;

    private String levelUpGrowDesc;
    private String startGrowScope;
    private String endGrowScope;

    public String getMemberNickName() {
        return memberNickName;
    }

    public void setMemberNickName(String memberNickName) {
        this.memberNickName = memberNickName;
    }

    public String getMemberIcon() {
        return memberIcon;
    }

    public void setMemberIcon(String memberIcon) {
        this.memberIcon = memberIcon;
    }

    private String memberNickName;
    private String memberIcon;
    private List<PrivilegeVOBean> privilegeVOList;
    private List<RuleVOBean> ruleVOList;

    public class PrivilegeVOBean implements Serializable{
        public int getPrivilegeId() {
            return privilegeId;
        }

        public void setPrivilegeId(int privilegeId) {
            this.privilegeId = privilegeId;
        }

        public String getPrivilegeCode() {
            return privilegeCode;
        }

        public void setPrivilegeCode(String privilegeCode) {
            this.privilegeCode = privilegeCode;
        }

        public String getPrivilegeName() {
            return privilegeName;
        }

        public void setPrivilegeName(String privilegeName) {
            this.privilegeName = privilegeName;
        }

        public String getPrivilegeICon() {
            return privilegeICon;
        }

        public void setPrivilegeICon(String privilegeICon) {
            this.privilegeICon = privilegeICon;
        }

        private int privilegeId;
        private String privilegeCode;
        private String privilegeName;
        private String privilegeICon;

        public String getGetConditions() {
            return getConditions;
        }

        public void setGetConditions(String getConditions) {
            this.getConditions = getConditions;
        }

        public String getPrivilegeDesc() {
            return privilegeDesc;
        }

        public void setPrivilegeDesc(String privilegeDesc) {
            this.privilegeDesc = privilegeDesc;
        }

        private String getConditions;
        private String privilegeDesc;
    }

    public static class RuleVOBean implements Serializable{
        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public String getGrowScope() {
            return growScope;
        }

        public void setGrowScope(String growScope) {
            this.growScope = growScope;
        }

        private String levelName;
        private String growScope;

        public String getExpiredDate() {
            return expiredDate;
        }

        public void setExpiredDate(String expiredDate) {
            this.expiredDate = expiredDate;
        }

        private String expiredDate;
    }
}

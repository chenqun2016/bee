package com.bee.user.params;

import java.io.Serializable;
import java.util.List;

/**
 * 创建时间：2021/7/24
 * 编写人： 陈陈陈
 * 功能描述：
 */
public class PayParams implements Serializable {
    public List<Integer> orderIds;
    public String payPassword;
}

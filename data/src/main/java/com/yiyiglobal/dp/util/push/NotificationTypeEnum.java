package com.yiyiglobal.dp.util.push;

/**
 * Created by zhangdebin on 2018/5/22.
 */
public enum NotificationTypeEnum {
    SYSTEM(0, "系统"),
    CONTRIBUTION(1, "收获贡献值"),
    TOKENCOIN(2, "收获币值");
    private Integer type;
    private String title;

    NotificationTypeEnum(Integer type, String title) {
        this.type = type;
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public  static String getTitle(Integer type){
        for(NotificationTypeEnum notifyType:values()){
            if(notifyType.getType()==type){
                return notifyType.getTitle();
            }
        }
        return null;
    }
}


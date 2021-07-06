package com.moon.moon_commons.entity;

/**
 * @ClassName OrderTimeFareItem
 * @Description: TODO
 * @Author zyl
 * @Date 2021/2/23
 * @Version V1.0
 **/
public class OrderTimeFareItem {

    /**
     * 明细类型: 1/起步价,2/超里程费,3/超时长费,4/远途费
     */
    private Integer itemType;

    /**
     * 费用
     */
    private Integer fare;

    /**
     * 里程
     */
    private Integer mileLen;

    /**
     * 时长
     */
    private Integer minuteLen;

    /**
     * 节假日类型: 1/工作日,2/休息日
     */
    private Integer holidayType;

    /**
     * 时段区间
     */
    private String timeDuration;

    /**
     * 里程区间
     */
    private String mileDuration;

    /**
     *
     * 跨区间数
     */
    private Integer itemTypeCount;

    public Integer getItemTypeCount() {
        return itemTypeCount;
    }

    public void setItemTypeCount(Integer itemTypeCount) {
        this.itemTypeCount = itemTypeCount;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Integer getFare() {
        return fare;
    }

    public void setFare(Integer fare) {
        this.fare = fare;
    }

    public Integer getMileLen() {
        return mileLen;
    }

    public void setMileLen(Integer mileLen) {
        this.mileLen = mileLen;
    }

    public Integer getMinuteLen() {
        return minuteLen;
    }

    public void setMinuteLen(Integer minuteLen) {
        this.minuteLen = minuteLen;
    }

    public Integer getHolidayType() {
        return holidayType;
    }

    public void setHolidayType(Integer holidayType) {
        this.holidayType = holidayType;
    }

    public String getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(String timeDuration) {
        this.timeDuration = timeDuration;
    }

    public String getMileDuration() {
        return mileDuration;
    }

    public void setMileDuration(String mileDuration) {
        this.mileDuration = mileDuration;
    }
}

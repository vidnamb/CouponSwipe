package edu.cmu.couponswipe.model;

/**
 * Created by sparshith on 10/4/15.
 */
public class DealHistory {
    private int userId;
    private String dealUuid;
    private String action;
    private String updatedAt;
    private String createdAt;

    public DealHistory() {
        super();
    }

    public DealHistory(int userId, String dealUuid, String action, String updatedAt, String createdAt) {
        this.userId = userId;
        this.dealUuid = dealUuid;
        this.action = action;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDealUuid() {
        return dealUuid;
    }

    public void setDealUuid(String dealUuid) {
        this.dealUuid = dealUuid;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


}

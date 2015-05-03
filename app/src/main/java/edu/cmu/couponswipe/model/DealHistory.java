package edu.cmu.couponswipe.model;

import android.content.Context;

import edu.cmu.couponswipe.database.DatabaseHandler;

/**
 * Created by sparshith on 10/4/15.
 */
public class DealHistory {

    private String email;
    private String dealUuid;
    private String action;
    private String updatedAt;
    private String createdAt;

    public DealHistory() {
        super();
    }

    public DealHistory(String email, String dealUuid, String action, String updatedAt, String createdAt) {
        this.email = email;
        this.dealUuid = dealUuid;
        this.action = action;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public DealHistory(String email, String dealUuid) {
        this.email = email;
        this.dealUuid = dealUuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

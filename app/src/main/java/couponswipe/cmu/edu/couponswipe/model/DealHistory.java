package couponswipe.cmu.edu.couponswipe.model;

/**
 * Created by sparshith on 10/4/15.
 */
public class DealHistory {
    private String userId;
    private String dealId;
    private String action;
    private String updatedAt;
    private String createdAt;

    public DealHistory() {
        super();
    }

    public DealHistory(String userId, String dealId, String action, String updatedAt, String createdAt) {
        this.userId = userId;
        this.dealId = dealId;
        this.action = action;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
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

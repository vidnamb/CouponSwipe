package edu.cmu.couponswipe.model;

/**
 * Created by lloyddsilva on 4/4/15.
 */

// https://partner-api.groupon.com/deals.json?tsToken=US_AFF_0_201236_212556_0&offset=0&limit=1
public class Deal {
    private String dealUuid;
    private String dealTitle;
    private String dealDescription;
    private String dealLocation;
    private String dealLatitude;
    private String dealLongitude;

    private String dealAmount;
    private String dealCurrency;

    private String dealStartDate;
    private String dealExpiryDate;

    private String dealCategory;

    private String smallImageUrl;
    private String mediumImageUrl;
    private String largeImageUrl;

    private String merchantUuid;
    private String merchantName;
    private String merchantUrl;

    private String dealBuyUrl;

    public Deal() {
        dealTitle = "40% Off Papa Johns";
        smallImageUrl = "https://img.grouponcdn.com/deal/wRHAMdAvHH8ruCEYyT44tL/IMAGE_Papa-Johns-700x420/v1/t50x50.jpg";
    }

    public Deal(String dealUuid) {
        this();
        dealUuid = dealUuid;
    }

    public String getDealUuid() {
        return dealUuid;
    }

    public void setDealUuid(String dealUuid) {
        this.dealUuid = dealUuid;
    }

    public String getDealTitle() {
        return dealTitle;
    }

    public void setDealTitle(String dealTitle) {
        this.dealTitle = dealTitle;
    }

    public String getDealDescription() {
        return dealDescription;
    }

    public void setDealDescription(String dealDescription) {
        this.dealDescription = dealDescription;
    }

    public String getDealLocation() {
        return dealLocation;
    }

    public void setDealLocation(String dealLocation) {
        this.dealLocation = dealLocation;
    }

    public String getDealLatitude() {
        return dealLatitude;
    }

    public void setDealLatitude(String dealLatitude) {
        this.dealLatitude = dealLatitude;
    }

    public String getDealLongitude() {
        return dealLongitude;
    }

    public void setDealLongitude(String dealLongitude) {
        this.dealLongitude = dealLongitude;
    }

    public String getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(String dealAmount) {
        this.dealAmount = dealAmount;
    }

    public String getDealCurrency() {
        return dealCurrency;
    }

    public void setDealCurrency(String dealCurrency) {
        this.dealCurrency = dealCurrency;
    }

    public String getDealStartDate() {
        return dealStartDate;
    }

    public void setDealStartDate(String dealStartDate) {
        this.dealStartDate = dealStartDate;
    }

    public String getDealExpiryDate() {
        return dealExpiryDate;
    }

    public void setDealExpiryDate(String dealExpiryDate) {
        this.dealExpiryDate = dealExpiryDate;
    }

    public String getDealCategory() {
        return dealCategory;
    }

    public void setDealCategory(String dealCategory) {
        this.dealCategory = dealCategory;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getMediumImageUrl() {
        return mediumImageUrl;
    }

    public void setMediumImageUrl(String mediumImageUrl) {
        this.mediumImageUrl = mediumImageUrl;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    public String getMerchantUuid() {
        return merchantUuid;
    }

    public void setMerchantUuid(String merchantUuid) {
        this.merchantUuid = merchantUuid;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantUrl() {
        return merchantUrl;
    }

    public void setMerchantUrl(String merchantUrl) {
        this.merchantUrl = merchantUrl;
    }

    public String getDealBuyUrl() {
        return dealBuyUrl;
    }

    public void setDealBuyUrl(String dealBuyUrl) {
        this.dealBuyUrl = dealBuyUrl;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Deal{");
        sb.append("dealUuid='").append(dealUuid).append('\'');
        sb.append(", dealTitle='").append(dealTitle).append('\'');
        sb.append(", dealDescription='").append(dealDescription).append('\'');
        sb.append(", dealLocation='").append(dealLocation).append('\'');
        sb.append(", dealLatitude='").append(dealLatitude).append('\'');
        sb.append(", dealLongitude='").append(dealLongitude).append('\'');
        sb.append(", dealAmount='").append(dealAmount).append('\'');
        sb.append(", dealCurrency='").append(dealCurrency).append('\'');
        sb.append(", dealStartDate='").append(dealStartDate).append('\'');
        sb.append(", dealExpiryDate='").append(dealExpiryDate).append('\'');
        sb.append(", dealCategory='").append(dealCategory).append('\'');
        sb.append(", smallImageUrl='").append(smallImageUrl).append('\'');
        sb.append(", mediumImageUrl='").append(mediumImageUrl).append('\'');
        sb.append(", largeImageUrl='").append(largeImageUrl).append('\'');
        sb.append(", merchantUuid='").append(merchantUuid).append('\'');
        sb.append(", merchantName='").append(merchantName).append('\'');
        sb.append(", merchantUrl='").append(merchantUrl).append('\'');
        sb.append(", dealBuyUrl='").append(dealBuyUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

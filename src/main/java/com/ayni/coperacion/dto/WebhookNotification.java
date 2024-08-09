package com.ayni.coperacion.dto;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebhookNotification implements Serializable {
    
    @SerializedName("id")
    private String id;
    @SerializedName("api_version")
    private String api_version;
    @SerializedName("payment_request_id")
    private String payment_request_id;
    @SerializedName("transaction_id")
    private String transaction_id;
    @SerializedName("resource")
    private String resource;
    @SerializedName("resource_status")
    private String resource_status;
    @SerializedName("detail_type")
    private String detail_type;
    @SerializedName("attempts")
    private int attempts;
    @SerializedName("sent_date")
    private String sent_date;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("completed_at")
    private String completed_at;
    @SerializedName("expires_at")
    private String expires_at;
    @SerializedName("cancelled_at")
    private String cancelled_at;
    @SerializedName("expired_at")
    private String expired_at;
    @SerializedName("declined_at")
    private String declined_at;
    @SerializedName("payment_date")
    private String payment_date;
    @SerializedName("me_reference_id")
    private String me_reference_id;
    @SerializedName("receipt_no")
    private String receipt_no;
    @SerializedName("payment_type")
    private String payment_type;
    @SerializedName("barcode")
    private String barcode;
    @SerializedName("customer_email")
    private String customer_email;



}

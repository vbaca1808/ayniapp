package com.ayni.coperacion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebhookNotification {
    
    private String id;
    private String api_version;
    private String payment_request_id;
    private String transaction_id;
    private String resource;
    private String resource_status;
    private String detail_type;
    private int attempts;
    private String sent_date;
    private String created_at;
    private String completed_at;
    private String expires_at;
    private String cancelled_at;
    private String expired_at;
    private String declined_at;
    private String payment_date;
    private String me_reference_id;
    private String receipt_no;
    private String payment_type;
    private String barcode;
    private String customer_email;



}

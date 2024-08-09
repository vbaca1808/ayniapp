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



}

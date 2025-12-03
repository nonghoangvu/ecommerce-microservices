package vn.microservice.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EUserStatus {
    @JsonProperty("active")
    ACTIVE,
    @JsonProperty("inactive")
    INACTIVE,
    @JsonProperty("blocked")
    BLOCKED,
    @JsonProperty("none")
    NONE
}

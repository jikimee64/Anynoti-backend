package com.anynoti.attributes;

import java.util.Map;

public abstract class Oauth2Attributes {
    protected Map<String, Object> attributes;

    public Oauth2Attributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() { return attributes; }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();
}
package com.anynoti.documentation.utils;

import static org.springframework.restdocs.snippet.Attributes.key;

import org.springframework.restdocs.snippet.Attributes;

public interface DocsAttributeProvider {
    static Attributes.Attribute getExampleAttribute(Object example) {
        return key("example").value(example);
    }
}
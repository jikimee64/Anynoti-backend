package com.anynoti.documentation.utils;

import static org.springframework.restdocs.snippet.Attributes.key;

import java.util.List;
import org.springframework.restdocs.snippet.Attributes;

public class DocumentFormatGenerator {

    public static Attributes.Attribute getDateFormat() {
        return key("format").value("yyyy-MM-dd");
    }

    public static Attributes.Attribute getConstraint(List<String> description) {
        return key("constraints").value(description);
    }

}
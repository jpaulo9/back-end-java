package com.rest.api.serialization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

public class YamlJackson2HttpMsgConverter extends AbstractJackson2HttpMessageConverter {

    public YamlJackson2HttpMsgConverter() {
        super(
                new YAMLMapper().setSerializationInclusion(
                        JsonInclude.Include.NON_NULL),
                MediaType.parseMediaType("application/x-yaml")
                );

    }
}
 
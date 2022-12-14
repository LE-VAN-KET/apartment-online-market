package com.cdcn.apartmentonlinemarket.helpers.specs;

import com.cdcn.apartmentonlinemarket.common.enums.FilterOperator;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class FilterOperatorDeserializer extends JsonDeserializer<FilterOperator> {
    @Override
    public FilterOperator deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        final ObjectCodec objectCodec = jsonParser.getCodec();
        final JsonNode node = objectCodec.readTree(jsonParser);
        final String type = node.asText();
        return FilterOperator.fromValue(type);
    }
}

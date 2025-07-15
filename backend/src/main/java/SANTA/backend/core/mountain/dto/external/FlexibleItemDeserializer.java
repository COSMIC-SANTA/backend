package SANTA.backend.core.mountain.dto.external;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlexibleItemDeserializer extends JsonDeserializer<List<ForestApiItem>> {

    @Override
    public List<ForestApiItem> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        List<ForestApiItem> items = new ArrayList<>();

        if (p.getCurrentToken() == JsonToken.START_ARRAY) {
            // 배열인 경우 (여러 결과)
            ForestApiItem[] array = mapper.readValue(p, ForestApiItem[].class);
            items.addAll(List.of(array));
        } else if (p.getCurrentToken() == JsonToken.START_OBJECT) {
            // 객체인 경우 (단일 결과)
            ForestApiItem item = mapper.readValue(p, ForestApiItem.class);
            items.add(item);
        }

        return items;
    }

}

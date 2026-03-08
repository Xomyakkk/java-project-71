package hexlet.code.formatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.core.DiffNode;

import java.util.List;

public class JsonFormatter implements Formatter {

    @Override
    public String format(List<DiffNode> diff) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(diff);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

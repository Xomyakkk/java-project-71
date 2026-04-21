package hexlet.code.formatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.core.DiffNode;

import java.util.List;

public final class JsonFormatter implements Formatter {

    @Override
    public String format(List<DiffNode> diff) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(diff);
    }
}

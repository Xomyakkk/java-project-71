package hexlet.code.formatter;

import hexlet.code.core.DiffNode;

import java.util.List;

public class StylishFormatter implements Formatter {

    @Override
    public String format(List<DiffNode> diff) {
        StringBuilder result = new StringBuilder("{\n");

        for (DiffNode node : diff) {
            switch (node.status()) {
                case UNCHANGED ->
                        result.append("    ")
                                .append(node.key())
                                .append(": ")
                                .append(node.oldValue())
                                .append("\n");

                case ADDED ->
                        result.append("  + ")
                                .append(node.key())
                                .append(": ")
                                .append(node.newValue())
                                .append("\n");

                case REMOVED ->
                        result.append("  - ")
                                .append(node.key())
                                .append(": ")
                                .append(node.oldValue())
                                .append("\n");

                case UPDATED -> {
                    result.append("  - ")
                            .append(node.key())
                            .append(": ")
                            .append(node.oldValue())
                            .append("\n");
                    result.append("  + ")
                            .append(node.key())
                            .append(": ")
                            .append(node.newValue())
                            .append("\n");
                }
            }
        }

        result.append("}");
        return result.toString();
    }
}

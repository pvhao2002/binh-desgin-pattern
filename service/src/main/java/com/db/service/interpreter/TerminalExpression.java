package com.db.service.interpreter;

import com.db.service.interpreter.context.FilterContext;

import java.util.Objects;

public class TerminalExpression implements Expression {

    private final String field;
    private final String value;

    public TerminalExpression(String field, String value) {
        this.field = field;
        this.value = value;
    }

    @Override
    public boolean interpret(FilterContext context) {
        if (context.getStudent() == null) return false;
        return switch (field.toLowerCase()) {
            case "classid" -> Objects.equals(
                    context.getStudent().getClassEntity() != null ? context.getStudent().getClassEntity().getClassId().toString() : null,
                    value);
            case "status" -> Objects.equals(context.getStudent().getStatus(), value);
            case "fullname" -> context.getStudent().getFullName() != null && context.getStudent().getFullName().toLowerCase().contains(value.toLowerCase());
            default -> false;
        };
    }
}

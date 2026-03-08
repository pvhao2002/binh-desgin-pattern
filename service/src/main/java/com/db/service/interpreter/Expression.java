package com.db.service.interpreter;

import com.db.service.interpreter.context.FilterContext;

public interface Expression {
    boolean interpret(FilterContext context);
}

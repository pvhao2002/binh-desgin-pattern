package com.db.service.interpreter;

import com.db.service.interpreter.context.FilterContext;

public class AndExpression implements Expression {

    private final Expression left;
    private final Expression right;

    public AndExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean interpret(FilterContext context) {
        return left.interpret(context) && right.interpret(context);
    }
}

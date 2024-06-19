package io.cloudevents.sql.impl.functions;

import io.cloudevents.CloudEvent;
import io.cloudevents.sql.EvaluationContext;
import io.cloudevents.sql.EvaluationRuntime;
import io.cloudevents.sql.impl.runtime.EvaluationResult;

public class RightFunction extends BaseTwoArgumentFunction<String, Integer, String> {
    public RightFunction() {
        super("RIGHT", String.class, Integer.class, String.class);
    }

    @Override
    EvaluationResult invoke(EvaluationContext ctx, EvaluationRuntime evaluationRuntime, CloudEvent event, String s, Integer length) {
        if (length > s.length()) {
            return new EvaluationResult(s);
        }
        if (length < 0) {
            return new EvaluationResult(s, ctx.exceptionFactory().functionExecutionError(name(), new IllegalArgumentException("The length of the RIGHT substring is lower than 0: " + length)).create(ctx.expressionInterval(), ctx.expressionText()));
        }
        return new EvaluationResult(s.substring(s.length() - length));
    }
}

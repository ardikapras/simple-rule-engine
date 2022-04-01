package ruleengine.rulesImpl.loanMatchRuleEngine;

import ruleengine.restAPI.RuleNamespace;
import ruleengine.ruleEngine.InferenceEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoanMatchInferenceEngine extends InferenceEngine<InputDetails, OutputDetails> {

    @Override
    protected RuleNamespace getRuleNamespace() {
        return RuleNamespace.LOAN_MATCH;
    }

    @Override
    protected OutputDetails initializeOutputResult(boolean isMatch) {
        return OutputDetails.builder()
                .match(isMatch)
                .approvalStatus(false)
                .build();
    }
}

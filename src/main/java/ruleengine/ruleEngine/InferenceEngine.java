package ruleengine.ruleEngine;

import ruleengine.knowledgeBase.models.Rule;
import ruleengine.langParser.RuleParser;
import ruleengine.restAPI.RuleNamespace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public abstract class InferenceEngine<INPUT_DATA, OUTPUT_RESULT> {

    @Autowired
    private RuleParser<INPUT_DATA, OUTPUT_RESULT> ruleParser;

    /**
     * Run inference engine on set of rules for given data.
     * @param listOfRules
     * @param inputData
     * @return
     */
    public OUTPUT_RESULT run (List<Rule> listOfRules, INPUT_DATA inputData){
        boolean isMatch = true;
        if (null == listOfRules || listOfRules.isEmpty()){
            return null;
        }

        List<Rule> conflictSet = match(listOfRules, inputData);
        Rule resolvedRule = resolve(conflictSet);
        if (null == resolvedRule){
            isMatch = false;
        }
        return executeRule(resolvedRule, inputData, isMatch);
    }

    /**
     *We can use here any pattern matching algo:
     * 1. Rete
     * 2. Linear
     * 3. Treat
     * 4. Leaps
     *
     * Here we are using Linear matching algorithm for pattern matching.
     * @param listOfRules
     * @param inputData
     * @return
     */
    protected List<Rule> match(List<Rule> listOfRules, INPUT_DATA inputData){
        return listOfRules.stream()
                .filter(
                        rule -> {
                            String condition = rule.getCondition();
                            return ruleParser.parseCondition(condition, inputData);
                        }
                )
                .collect(Collectors.toList());
    }

    /**
     * We can use here any resolving techniques:
     * 1. Lex
     * 2. Recency
     * 3. MEA
     * 4. Refactor
     * 5. Priority wise
     *
     *  Here we are using find first rule logic.
     * @param conflictSet
     * @return
     */
    protected Rule resolve(List<Rule> conflictSet){
        Optional<Rule> rule = conflictSet.stream()
                .findFirst();
        if (rule.isPresent()){
            return rule.get();
        }
        return null;
    }

    /**
     * Execute selected rule on input data.
     * @param rule
     * @param inputData
     * @return
     */
    protected OUTPUT_RESULT executeRule(Rule rule, INPUT_DATA inputData, boolean isMatch){
        OUTPUT_RESULT outputResult = initializeOutputResult(isMatch);
        if (!isMatch) {
            return outputResult;
        }
        return ruleParser.parseAction(rule.getAction(), inputData, outputResult);
    }

    protected abstract OUTPUT_RESULT initializeOutputResult(boolean isMatch);
    protected abstract RuleNamespace getRuleNamespace();
}

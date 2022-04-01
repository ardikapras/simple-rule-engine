package ruleengine.ruleEngine;

import ruleengine.constant.Status;
import ruleengine.knowledgeBase.KnowledgeBaseService;
import ruleengine.knowledgeBase.models.Rule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RuleEngine {

    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    public Object run(InferenceEngine inferenceEngine, Object inputData) {
        String ruleNamespace = inferenceEngine.getRuleNamespace().toString();
        List<Rule> allRulesByNamespace = knowledgeBaseService.getAllRuleByNamespace(ruleNamespace, Status.ACTIVE);
        Object result = inferenceEngine.run(allRulesByNamespace, inputData);
        return result;
    }

}

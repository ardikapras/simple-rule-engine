package ruleengine.restAPI;

import com.google.common.base.Enums;
import ruleengine.constant.Status;
import ruleengine.knowledgeBase.KnowledgeBaseService;
import ruleengine.knowledgeBase.models.Rule;
import ruleengine.ruleEngine.RuleEngine;
import ruleengine.rulesImpl.loanMatchRuleEngine.OutputDetails;
import ruleengine.rulesImpl.loanMatchRuleEngine.LoanMatchInferenceEngine;
import ruleengine.rulesImpl.loanMatchRuleEngine.InputDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class RuleEngineRestController {
    @Autowired
    private KnowledgeBaseService knowledgeBaseService;
    @Autowired
    private RuleEngine ruleEngine;
    @Autowired
    private LoanMatchInferenceEngine loanMatchInferenceEngine;

    @GetMapping(value = "/get-all-rules/{ruleNamespace}")
    public ResponseEntity<?> getRulesByNamespace(@PathVariable("ruleNamespace") String ruleNamespace) {
        RuleNamespace namespace = Enums.getIfPresent(RuleNamespace.class, ruleNamespace.toUpperCase()).or(RuleNamespace.DEFAULT);
        List<Rule> allRules = knowledgeBaseService.getAllRuleByNamespace(namespace.toString(), Status.ACTIVE);
        return ResponseEntity.ok(allRules);
    }

    @GetMapping(value = "/get-all-rules")
    public ResponseEntity<?> getAllRules() {
        List<Rule> allRules = knowledgeBaseService.getAllRules();
        return ResponseEntity.ok(allRules);
    }

    @PostMapping(value = "/loan-match")
    public ResponseEntity<?> postLoanMatchDetails(@RequestBody InputDetails inputDetails) {
        long start1 = System.currentTimeMillis();
        OutputDetails result = (OutputDetails) ruleEngine.run(loanMatchInferenceEngine, inputDetails);
        long end1 = System.currentTimeMillis();
        System.out.println("::" + (end1 - start1));
        return ResponseEntity.ok(result);
    }
}

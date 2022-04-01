package ruleengine.knowledgeBase;

import com.google.common.base.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ruleengine.knowledgeBase.db.RuleDbModel;
import ruleengine.knowledgeBase.db.RulesRepository;
import ruleengine.knowledgeBase.models.Rule;
import ruleengine.restAPI.RuleNamespace;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KnowledgeBaseService {
    @Autowired
    private RulesRepository rulesRepository;

    public List<Rule> getAllRules() {
        return rulesRepository.findAll().stream()
                .map(
                        ruleDbModel -> mapFromDbModel(ruleDbModel)
                )
                .collect(Collectors.toList());
    }

    @Cacheable(cacheNames = {"rules"}, key="#ruleNamespace")
    public List<Rule> getAllRuleByNamespace(String ruleNamespace, Integer status) {
        List<Rule> ruleList = rulesRepository.findByRuleNamespaceAndStatusIsOrderByPriorityAsc(ruleNamespace, status).stream()
                .map(
                        ruleDbModel -> mapFromDbModel(ruleDbModel)

                )
                .collect(Collectors.toList());
        return ruleList;
    }

    private Rule mapFromDbModel(RuleDbModel ruleDbModel) {
        RuleNamespace namespace = Enums.getIfPresent(RuleNamespace.class, ruleDbModel.getRuleNamespace().toUpperCase())
                .or(RuleNamespace.DEFAULT);
        return Rule.builder()
                .ruleNamespace(namespace)
                .ruleId(ruleDbModel.getRuleId())
                .condition(ruleDbModel.getCondition())
                .action(ruleDbModel.getAction())
                .description(ruleDbModel.getDescription())
                .priority(ruleDbModel.getPriority())
                .status(ruleDbModel.getStatus())
                .build();
    }
}

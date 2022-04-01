package ruleengine.knowledgeBase.models;

import ruleengine.restAPI.RuleNamespace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Rule implements Serializable {
    Integer ruleId;
    RuleNamespace ruleNamespace;
    String condition;
    String action;
    Integer priority;
    String description;
    Integer status;
}

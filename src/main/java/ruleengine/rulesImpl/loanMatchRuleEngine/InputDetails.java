package ruleengine.rulesImpl.loanMatchRuleEngine;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputDetails {
    @JsonProperty("parent_approved_date")
    Integer parentApprovedDate;
    @JsonProperty("tokoscore")
    String tokoscore;
    @JsonProperty("cb_collect")
    String cbCollect;
    @JsonProperty("shop_badge")
    String shopBadge;
    @JsonProperty("lender_alias")
    String lenderAlias;
}

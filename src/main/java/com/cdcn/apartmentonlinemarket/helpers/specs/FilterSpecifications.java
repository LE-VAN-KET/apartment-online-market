package com.cdcn.apartmentonlinemarket.helpers.specs;

import com.cdcn.apartmentonlinemarket.common.enums.FieldType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class FilterSpecifications<T> implements Specification<T> {
    private List<FilterCriteria> filterConditions;

    public FilterSpecifications() {
        this.filterConditions = new ArrayList<>();
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();
        filterConditions.forEach(conditon -> predicateList.add(buildPredicate(conditon, root, query,
                criteriaBuilder)));

        return predicateList.isEmpty() ? null : criteriaBuilder.and(predicateList.toArray(
                new Predicate[predicateList.size()]));
    }

    public void addCondition(List<FilterCriteria> filterCriteriaList) {
        filterConditions.addAll(filterCriteriaList);
    }

    private Predicate buildPredicate(FilterCriteria condition, Root root, CriteriaQuery criteriaQuery,
                                     CriteriaBuilder criteriaBuilder) {
        Predicate p = null;

        switch (condition.getOperation()) {
            case EQUAL:
                p = criteriaBuilder.equal(root.get(condition.getKey()), condition.getValue());
                break;
            case NOT_EQUAL:
                p = criteriaBuilder.notEqual(root.get(condition.getKey()), condition.getValue());
                break;
            case IS_NULL:
                p = criteriaBuilder.isNull(root.get(condition.getKey()));
                break;
            case IS_NOT_NULL:
                p = criteriaBuilder.isNotNull(root.get(condition.getKey()));
                break;
            case IS_EMPTY:
                p = criteriaBuilder.equal(root.get(condition.getKey()), "");
                break;
            case IS_NOT_EMPTY:
                p = criteriaBuilder.notEqual(root.get(condition.getKey()), "");
                break;
            case CONTAINS:
                p = criteriaBuilder.like(root.get(condition.getKey()).as(String.class),
                        "%" + condition.getValue() + "%");
                break;
            case NOT_CONTAINS:
                p = criteriaBuilder.notLike(root.get(condition.getKey()).as(String.class),
                        "%" + condition.getValue() + "%");
                break;
            case START_WITH:
                p = criteriaBuilder.like(root.get(condition.getKey()).as(String.class),
                        condition.getValue() + "%");
                break;
            case END_WITH:
                p = criteriaBuilder.like(root.get(condition.getKey()).as(String.class),
                        "%" + condition.getValue());
                break;
            case GREATER_THAN:
                p = criteriaBuilder.greaterThan(root.get(condition.getKey()).as(BigDecimal.class),
                        (BigDecimal) condition.getValue());
                break;
            case GREATER_THAN_OR_EQUAL_TO:
                p = criteriaBuilder.greaterThanOrEqualTo(root.get(condition.getKey()).as(BigDecimal.class),
                        (BigDecimal)condition.getValue());
                break;
            case LESS_THAN:
                p = criteriaBuilder.lessThan(root.get(condition.getKey()).as(BigDecimal.class),
                        (BigDecimal) condition.getValue());
                break;
            case LESS_THAN_OR_EQUAL_TO:
                p = criteriaBuilder.lessThanOrEqualTo(root.get(condition.getKey()).as(BigDecimal.class),
                        (BigDecimal) condition.getValue());
                break;
            case BETWEEN:
                Object value = condition.getFieldType().parse(condition.getValue().toString());
                Object valueTo = condition.getFieldType().parse(condition.getValueTo().toString());
                if (condition.getFieldType() == FieldType.DATE) {
                    LocalDateTime startDate = (LocalDateTime) value;
                    LocalDateTime endDate = (LocalDateTime) valueTo;
                    Expression<LocalDateTime> key = root.get(condition.getKey());
                    p = criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(key, startDate),
                            criteriaBuilder.lessThanOrEqualTo(key, endDate));
                }
                if (condition.getFieldType() != FieldType.CHAR && condition.getFieldType() != FieldType.BOOLEAN) {
                    Number start = (Number) value;
                    Number end = (Number) valueTo;
                    Expression<Number> key = root.get(condition.getKey());
                    p = criteriaBuilder.and(criteriaBuilder.and(criteriaBuilder.ge(key, start),
                            criteriaBuilder.le(key, end)));
                }
                break;
            default:
                p = criteriaBuilder.equal(root.get(condition.getKey()), condition.getValue());
        }
        return p;
    }
}

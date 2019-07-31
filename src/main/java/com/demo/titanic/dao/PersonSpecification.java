package com.demo.titanic.dao;

import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.demo.titanic.dto.Person;

public class PersonSpecification {

	/*
	 * The method constructs the query filters of JPA criteria API based on the queryParameters passed in the input string.
	 * For every filter present in the query:
	 * filtercondition<i> - filter condition
	 * filterfiled<i> - entity's field to be matched in the condition
	 * filtervalue<i> - value to be applied in condition
	 * filteroperator<i> - operator for combining conditions 'and' / 'or'
	 * 
	 * This method will be executed when applyFilter is invoked with find* operation of an Entity's repository method.
	 * 
	 * JpaSpecificationExecutor has the method List<T> findAll(Specification<T> spec); where we can give Specification<T> as parameter.
	 * The Specification can be created by overriding the toPredicate(Root,CriteriaQuery, CriteriaBuilder) method. 
	 * The Predicate class is part of the Criteria API and is used to construct where clauses of resulting SQL.
	 * Combining all predicates = all and/or conditions of SQL query.
	 */
	public static Specification<Person> applyFilter(int filterCount, Map<String, String> queryParameters, String groupQueryParamField, boolean groupQueryParamValue) {
		// (Root<Person>, CriteriaQuery<?>, CriteriaBuilder) - this is an override of toPredicate method
		return (person, query, criteriaBuilder) -> {

			Predicate finalPredicate = null;
			Predicate predicate = null;

			try {
				if (filterCount > 0) {
					//eg: jqx grid sends individual filter parameters for all the filters applied
					for (int i = 0; i < filterCount; i++) {
						String filterValue = queryParameters.get(("filtervalue" + i));
						String filterCondition = queryParameters.get(("filtercondition" + i));
						String filterField = queryParameters.get(("filterdatafield" + i));
						String filterOperator = queryParameters.get(("filteroperator" + i)).equals("0") ? "AND" : "OR";
						Path<String> path = person.get(filterField);
						
						switch (filterCondition) {
						case "CONTAINS":
							predicate = criteriaBuilder.like(path, "%" + filterValue + "%");
							break;
						case "DOES_NOT_CONTAIN":
							predicate = criteriaBuilder.notLike(path, "%" + filterValue + "%");
							break;
						case "EQUAL":
							predicate = criteriaBuilder.equal(path, filterValue);
							break;
						case "NOT_EQUAL":
							predicate = criteriaBuilder.notEqual(path, filterValue);
							break;
						case "GREATER_THAN":
							predicate = criteriaBuilder.greaterThan(path, filterValue);
							break;
						case "GREATER_THAN_OR_EQUAL":
							predicate = criteriaBuilder.greaterThanOrEqualTo(path, filterValue);
							break;
						case "LESS_THAN":
							predicate = criteriaBuilder.lessThan(path, filterValue);
							break;
						case "LESS_THAN_OR_EQUAL":
							predicate = criteriaBuilder.lessThanOrEqualTo(path, filterValue);
							break;
						case "STARTS_WITH":
							predicate = criteriaBuilder.like(path, filterValue + "%");
							break;
						case "ENDS_WITH":
							predicate = criteriaBuilder.like(path, "%" + filterValue);
							break;
						case "NULL":
							predicate = criteriaBuilder.isNull(path);
							break;
						case "NOT_NULL":
							predicate = criteriaBuilder.isNotNull(path);
							break;
						}

						//keep appending the predicate
						if (filterOperator.equalsIgnoreCase("OR")) {
							finalPredicate = (finalPredicate != null) ? criteriaBuilder.or(finalPredicate, predicate)
									: predicate;
						} else {
							finalPredicate = (finalPredicate != null) ? criteriaBuilder.and(finalPredicate, predicate)
									: predicate;
						}
					}
					
				}
				
				if(groupQueryParamField != null) {
					Path<String> path = person.get(groupQueryParamField);
					predicate = criteriaBuilder.equal(path, groupQueryParamValue);
					finalPredicate = (finalPredicate != null) ? criteriaBuilder.and(finalPredicate, predicate)
							: predicate;
				}
			} catch (Exception e) {
				System.out.println("Error parsing filter parameters!");
				e.printStackTrace();
			}

			return finalPredicate;
		};
	}

}


/**
 * Project Name:summit-srv-demo 
 * File Name:PageUtils.java <br/><br/>  
 * Description: TODO
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date May 7, 2018 3:15:50 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.summit.demo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.sap.summit.constants.CommonConstants;
import com.sap.summit.demo.dto.SearchCriteria;

/**
 * ClassName: PageUtils <br/>
 * <br/>
 * Description: TODO
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public class PageUtils
{

    public static final Integer DEFULT_PAGE_SIZE = 20;

    public static final Integer MAX_PAGE_NUMBER = 9999;

    public static final Integer DEFULT_PAGE_NUMBER = 0;

    public static <T> Specification<T> buildSpecification(SearchCriteria searchCriteria, String language)
    {

        return new Specification<T>()
        {

            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb)
            {

                List<Predicate> predicates = new ArrayList<>();

                if (null != searchCriteria)
                {
                    searchCriteria.getFilters().stream().forEach(filter -> {
                        String key = filter.getKey();
                        String value = filter.getValue();

                        if (StringUtils.equalsIgnoreCase(key, "ownedById"))
                        {
                            predicates.add(PageUtils.buildLikePredicate(root, cb, value, "ownedById"));
                        }

                        if (StringUtils.equalsIgnoreCase(key, "customerId"))
                        {
                            predicates.add(PageUtils.buildEqPredicate(root, cb, value, "customerId"));
                        }

                        if (StringUtils.equalsIgnoreCase(key, "status"))
                        {
                            predicates.add(PageUtils.buildEqPredicate(root, cb, value, "status"));
                        }
                    });
                }

                // add predicates
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return query.getRestriction();
            }
        };

    }

    /**
     * Title: buildPageRequest Description: create page request (page number, page size and orderBy)
     * 
     * @exception @see
     * @since {@inheritDoc}
     * @param pageNumber
     * @param pagzSize
     * @param orderBy
     * @return page request object
     */
    public static PageRequest buildPageRequest(Integer pageNumber, Integer pageSize)
    {
        // pageSize and pageNumber are null means retrieve maximal 9999 hits data
        int actualPageNumber = PageUtils.DEFULT_PAGE_NUMBER;
        int actualPageSize = PageUtils.MAX_PAGE_NUMBER;

        // get all and no pagination if page size equals to -1
        if (null != pageSize && -1 != pageSize)
        {
            // if pageNumber is invalid (0, -2, -10 ...), then set it to 1 (the first page)
            actualPageNumber = (null == pageNumber || pageNumber <= 0) ? PageUtils.DEFULT_PAGE_NUMBER : pageNumber - 1;

            // if pageSize is invalid (-1, -10 ...), then set it to 20 
            if (pageSize <= 0)
            {
                actualPageSize = PageUtils.DEFULT_PAGE_SIZE;
            }
            else
            {
                actualPageSize = pageSize;
            }
        }

        return new PageRequest(actualPageNumber, actualPageSize);
    }

    /**
     * Title: buildLikeQuery Description: build like query string
     * 
     * @param <T>
     * 
     * @exception @see
     * @since {@inheritDoc}
     * @param queryStr
     * @return %queryStr%
     */
    public static <T> Predicate buildLikePredicate(Root<T> root, CriteriaBuilder builder, String queryStr, String entityField)
    {
        Predicate likePredicate = null;
        if (queryStr != null)
        {
            String upperQueryStr = queryStr.toUpperCase(Locale.ENGLISH);
            String escapedQueryStr = escapeSpecialCharacter(upperQueryStr);
            if (upperQueryStr.equals(escapedQueryStr))
            {
                // don't need to escape special character
                likePredicate = builder.like(builder.upper(root.get(entityField)),
                    CommonConstants.PERCENT + escapedQueryStr + CommonConstants.PERCENT);
            }
            else
            {
                // escape special character
                likePredicate = builder.like(builder.upper(root.get(entityField)),
                    CommonConstants.PERCENT + escapedQueryStr + CommonConstants.PERCENT, CommonConstants.SLASH_CHAR);
            }
        }
        return likePredicate;
    }

    /**
     * Title: buildLikePredicate <br/>
     * <br/>
     * Description: buildLikePredicate
     * 
     * @param root
     * @param builder
     * @param queryStr
     * @param entityField
     * @return
     * @see
     * @since
     */
    public static <T> Predicate buildLikePredicate(Root<T> root, CriteriaBuilder builder, String queryStr, String[] entityFields)
    {
        Predicate likePredicate = null;
        if (queryStr != null)
        {
            String upperQueryStr = queryStr.toUpperCase(Locale.ENGLISH);
            String escapedQueryStr = escapeSpecialCharacter(upperQueryStr);
            List<Predicate> param = new ArrayList<>();
            if (upperQueryStr.equals(escapedQueryStr))
            {
                // don't need to escape special character
                for (String entityField : entityFields)
                {
                    Predicate p = builder.like(builder.upper(root.get(entityField)),
                        CommonConstants.PERCENT + escapedQueryStr + CommonConstants.PERCENT);
                    param.add(p);
                }
            }
            else
            {
                // escape special character
                for (String entityField : entityFields)
                {
                    Predicate p = builder.like(builder.upper(root.get(entityField)),
                        CommonConstants.PERCENT + escapedQueryStr + CommonConstants.PERCENT, CommonConstants.SLASH_CHAR);
                    param.add(p);
                }
            }
            likePredicate = builder.or(param.toArray(new Predicate[param.size()]));
        }
        return likePredicate;
    }

    /**
     * Title: buildEqPredicate Description: build eq query string
     * 
     * @exception @see
     * @since {@inheritDoc}
     * @param root
     * @param builder
     * @param queryStr
     * @param entityField
     * @return Predicate equal
     */
    public static <T> Predicate buildEqPredicate(Root<T> root, CriteriaBuilder builder, String queryStr, String entityField)
    {
        return builder.equal(root.get(entityField), queryStr);
    }

    /**
     * Title: buildNotEqPredicate <br/>
     * <br/>
     * Description: buildNotEqPredicate
     * 
     * @param root
     * @param builder
     * @param queryStr
     * @param entityField
     * @return Predicate not equal
     */
    public static <T> Predicate buildNotEqPredicate(Root<T> root, CriteriaBuilder builder, String queryStr, String entityField)
    {
        return builder.notEqual(root.get(entityField), queryStr);
    }

    /**
     * Title: splitOrderQuery Description: split order query string
     * 
     * @exception @see
     * @since {@inheritDoc}
     * @param queryStr
     * @return order attribute and direction
     */
    public static String[] splitOrderQuery(String queryStr)
    {
        return queryStr.split(CommonConstants.BLANK);
    }

    /**
     * Title: escapeSpecialCharacter Description: escape query string like: test_n?a*me to test/_n/?a/*me
     * 
     * @exception @see
     * @since {@inheritDoc}
     * @param queryStr
     * @return escaped query string
     */
    public static String escapeSpecialCharacter(String queryStr)
    {
        Pattern p = Pattern.compile(CommonConstants.SPECICAL_SYMBOL_REXG_MINIMAL);
        Matcher m = p.matcher(queryStr);
        StringBuffer sb = new StringBuffer();
        while (m.find())
        {
            m.appendReplacement(sb, CommonConstants.SLASH_STR + m.group());
        }
        m.appendTail(sb);
        return sb.toString();
    }

}

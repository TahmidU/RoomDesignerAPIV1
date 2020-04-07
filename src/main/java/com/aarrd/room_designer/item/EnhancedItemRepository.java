package com.aarrd.room_designer.item;

import com.aarrd.room_designer.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class EnhancedItemRepository implements IEnhancedItemRepository
{
    @PersistenceContext
    private EntityManager em;

    /**
     * Fetch items by checking user filters. Possible this method will be either changed or removed for a more elegant
     * solution.
     * @param pageNum page number.
     * @param itemName name of the item (does not have to be exact).
     * @param catIds  IDs of the category.
     * @param typeIds  IDs of the type.
     * @param hasModel if the item has a model.
     * @return Page of items.
     */
    @Override
    public List<Item> findAllItems(Integer pageNum, String itemName, List<Integer> catIds, List<Integer> typeIds,
                                       Boolean hasModel)
    {
        int pageSize = 9;
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Item> criteriaQuery = cb.createQuery(Item.class);
        List<Predicate> predicates = new ArrayList<>();
        Root<Item> root = criteriaQuery.from(Item.class);

        //Filter and overwrite query.
        if(itemName != null)
            predicates.add(cb.like(root.get("name"), "%" + itemName + "%"));
        if(catIds != null)
            predicates.add(root.get("category").in(catIds));
        if(typeIds != null)
            predicates.add(root.get("type").in(typeIds));
        if(hasModel != null) {
            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));
        }


        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));

        TypedQuery<Item> query = em.createQuery(criteriaQuery);
        query.setFirstResult(Math.toIntExact(pageNum));
        query.setMaxResults(pageSize);
        return new PageImpl<>((query.getResultList()),
                PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.ASC, "date")), getTotalCount(cb)).toList();
    }

    /**
     * Fetch items by checking user filters. Possible this method will be either changed or removed for a more elegant
     * solution.
     * @param pageNum page number.
     * @param itemName name of the item (does not have to be exact).
     * @param catIds  IDs of the category.
     * @param typeIds  IDs of the type.
     * @param hasModel if the item has a model.
     * @param userId ID of user.
     * @return Page of items.
     */
    @Override
    public List<Item> findAllUserItems(Integer pageNum, String itemName, List<Integer> catIds, List<Integer> typeIds,
                                       Boolean hasModel, Long userId)
    {
        int pageSize = 9;
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Item> criteriaQuery = cb.createQuery(Item.class);
        List<Predicate> predicates = new ArrayList<>();
        Root<Item> root = criteriaQuery.from(Item.class);

        Join<Item, User> userJoin = root.join("user");
        predicates.add(cb.equal(userJoin.get("userId"), userId));

        //Filter and overwrite query.
        if(itemName != null)
            predicates.add(cb.like(root.get("name"), "%" + itemName + "%"));
        if(catIds != null)
            predicates.add(root.get("category").in(catIds));
        if(typeIds != null)
            predicates.add(root.get("type").in(typeIds));
        if(hasModel != null)
            predicates.add(cb.equal(root.get("hasModel"), 1));

        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));

        TypedQuery<Item> query = em.createQuery(criteriaQuery);
        query.setFirstResult(Math.toIntExact(pageNum));
        query.setMaxResults(pageSize);
        return new PageImpl<>((query.getResultList()),
                PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.ASC, "date")), getTotalCount(cb)).toList();
    }

    private Long getTotalCount(CriteriaBuilder cb)
    {
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Item> root = cq.from(Item.class);

        cq.select(cb.count(root));

        return em.createQuery(cq).getSingleResult();
    }
}

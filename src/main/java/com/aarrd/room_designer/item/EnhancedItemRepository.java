package com.aarrd.room_designer.item;

import com.aarrd.room_designer.item.category.Category;
import com.aarrd.room_designer.item.type.Type;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
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

    @Override
    public Page<Item> findAllItems(Integer pageNum, String itemName, Integer catId, Integer typeId,
                                       Boolean hasModel)
    {
        int pageSize = 2;
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Item> criteriaQuery = cb.createQuery(Item.class);
        List<Predicate> predicates = new ArrayList<>();
        Root<Item> root = criteriaQuery.from(Item.class);



        if(itemName != null)
            criteriaQuery.multiselect(root.get("itemId"),root.get("name"),root.get("description"),root.get("date"),
                    root.get("hasModel")).where(cb.like(root.get("name"), "%"+itemName+"%"));
        if(catId != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            predicates.add(cb.equal(catJoin.get("catId"), catId));

            criteriaQuery.multiselect(root.get("itemId"),root.get("name"),root.get("description"),root.get("date"),
                    root.get("hasModel")).where(predicates.toArray(new Predicate[]{}));
        }
        if(typeId != null)
        {
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));

            criteriaQuery.multiselect(root.get("itemId"),root.get("name"),root.get("description"),root.get("date"),
                    root.get("hasModel")).where(predicates.toArray(new Predicate[]{}));
        }
        if(hasModel != null)
        {
            if(hasModel)
            {
                criteriaQuery.multiselect(root.get("itemId"),root.get("name"),root.get("description"),root.get("date"),
                        root.get("hasModel")).where(cb.equal(root.get("hasModel"), 1));
            }
        }
        if(itemName != null && catId != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            predicates.add(cb.equal(catJoin.get("catId"), catId));
            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));

            criteriaQuery.multiselect(root.get("itemId"),root.get("name"),root.get("description"),root.get("date"),
                    root.get("hasModel")).where(predicates.toArray(new Predicate[]{}));
        }
        if(itemName != null && typeId != null)
        {
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));
            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));

            criteriaQuery.multiselect(root.get("itemId"),root.get("name"),root.get("description"),root.get("date"),
                    root.get("hasModel")).where(predicates.toArray(new Predicate[]{}));
        }
        if(itemName != null && hasModel != null)
        {
            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));
            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));

            criteriaQuery.multiselect(root.get("itemId"),root.get("name"),root.get("description"),root.get("date"),
                    root.get("hasModel")).where(predicates.toArray(new Predicate[]{}));
        }
        if(catId != null && typeId != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.equal(catJoin.get("catId"), catId));
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));

            criteriaQuery.multiselect(root.get("itemId"),root.get("name"),root.get("description"),root.get("date"),
                    root.get("hasModel")).where(predicates.toArray(new Predicate[]{}));
        }
        if(catId != null && hasModel != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            predicates.add(cb.equal(catJoin.get("catId"), catId));
            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));

            criteriaQuery.multiselect(root.get("itemId"),root.get("name"),root.get("description"),root.get("date"),
                    root.get("hasModel")).where(predicates.toArray(new Predicate[]{}));
        }
        if(typeId != null && hasModel != null)
        {
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));
            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));

            criteriaQuery.multiselect(root.get("itemId"),root.get("name"),root.get("description"),root.get("date"),
                    root.get("hasModel")).where(predicates.toArray(new Predicate[]{}));
        }
        if(itemName != null && catId != null && typeId != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));
            predicates.add(cb.equal(catJoin.get("catId"), catId));
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));

            criteriaQuery.multiselect(root.get("itemId"),root.get("name"),root.get("description"),root.get("date"),
                    root.get("hasModel")).where(predicates.toArray(new Predicate[]{}));
        }
        if(itemName != null && typeId != null && hasModel != null)
        {
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));

            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));

            criteriaQuery.multiselect(root.get("itemId"),root.get("name"),root.get("description"),root.get("date"),
                    root.get("hasModel")).where(predicates.toArray(new Predicate[]{}));
        }
        if(catId != null && typeId != null && hasModel != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.equal(catJoin.get("catId"), catId));
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));

            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));

            criteriaQuery.multiselect(root.get("itemId"),root.get("name"),root.get("description"),root.get("date"),
                    root.get("hasModel")).where(predicates.toArray(new Predicate[]{}));
        }
        if(itemName != null && catId != null && typeId != null && hasModel != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.equal(catJoin.get("catId"), catId));
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));
            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));

            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));

            criteriaQuery.multiselect(root.get("itemId"),root.get("name"),root.get("description"),root.get("date"),
                    root.get("hasModel")).where(predicates.toArray(new Predicate[]{}));
        }

        criteriaQuery.multiselect(root.get("itemId"),root.get("name"),root.get("description"),root.get("date"),
                root.get("hasModel"));

        TypedQuery<Item> query = em.createQuery(criteriaQuery);
        query.setFirstResult(Math.toIntExact(pageNum));
        query.setMaxResults(pageSize);
        return new PageImpl<>((query.getResultList()),
                PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.ASC, "date")), getTotalCount(cb));
    }

    private Long getTotalCount(CriteriaBuilder cb)
    {
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Item> root = cq.from(Item.class);

        cq.select(cb.count(root));

        return em.createQuery(cq).getSingleResult();
    }
}

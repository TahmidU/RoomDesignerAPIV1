package com.aarrd.room_designer.item;

import com.aarrd.room_designer.favourite.IFavouriteRepository;
import com.aarrd.room_designer.item.category.Category;
import com.aarrd.room_designer.item.type.Type;
import com.aarrd.room_designer.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    private final IFavouriteRepository favouriteRepository;

    @Autowired
    public EnhancedItemRepository(IFavouriteRepository favouriteRepository)
    {
        this.favouriteRepository = favouriteRepository;
    }

    /**
     * Fetch items by checking user filters. Possible this method will be either changed or removed for a more elegant
     * solution.
     * @param pageNum page number.
     * @param itemName name of the item (does not have to be exact).
     * @param catId  ID of the category.
     * @param typeId  ID of the type.
     * @param hasModel if the item has a model.
     * @return Page of items.
     */
    @Override
    public Page<Item> findAllItems(Integer pageNum, String itemName, Integer catId, Integer typeId,
                                       Boolean hasModel)
    {
        int pageSize = 16;
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Item> criteriaQuery = cb.createQuery(Item.class);
        List<Predicate> predicates = new ArrayList<>();
        Root<Item> root = criteriaQuery.from(Item.class);

        //Filter and overwrite query.
        if(itemName != null)
            criteriaQuery.select(root).where(cb.like(root.get("name"), "%"+itemName+"%"));
        if(catId != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            predicates.add(cb.equal(catJoin.get("catId"), catId));

            criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        }
        if(typeId != null)
        {
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));

            criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        }
        if(hasModel != null)
        {
            if(hasModel)
            {
                criteriaQuery.select(root).where(cb.equal(root.get("hasModel"), 1));
            }
        }
        if(itemName != null && catId != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            predicates.add(cb.equal(catJoin.get("catId"), catId));
            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));

            criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        }
        if(itemName != null && typeId != null)
        {
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));
            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));

            criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        }
        if(itemName != null && hasModel != null)
        {
            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));
            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));

            criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        }
        if(catId != null && typeId != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.equal(catJoin.get("catId"), catId));
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));

            criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        }
        if(catId != null && hasModel != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            predicates.add(cb.equal(catJoin.get("catId"), catId));
            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));

            criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        }
        if(typeId != null && hasModel != null)
        {
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));
            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));

            criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        }
        if(itemName != null && catId != null && typeId != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));
            predicates.add(cb.equal(catJoin.get("catId"), catId));
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));

            criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        }
        if(itemName != null && typeId != null && hasModel != null)
        {
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));

            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));

            criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        }
        if(catId != null && typeId != null && hasModel != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.equal(catJoin.get("catId"), catId));
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));

            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));

            criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
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

            criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));
        }

        criteriaQuery.select(root);

        TypedQuery<Item> query = em.createQuery(criteriaQuery);
        query.setFirstResult(Math.toIntExact(pageNum));
        query.setMaxResults(pageSize);
        return new PageImpl<>((query.getResultList()),
                PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.ASC, "date")), getTotalCount(cb));
    }

    /**
     * Fetch items by checking user filters. Possible this method will be either changed or removed for a more elegant
     * solution.
     * @param pageNum page number.
     * @param itemName name of the item (does not have to be exact).
     * @param catId  ID of the category.
     * @param typeId  ID of the type.
     * @param hasModel if the item has a model.
     * @param userId ID of user.
     * @return Page of items.
     */
    @Override
    public Page<Item> findAllUserItems(Integer pageNum, String itemName, Integer catId, Integer typeId, Boolean hasModel,
                                       Long userId)
    {
        int pageSize = 16;
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Item> criteriaQuery = cb.createQuery(Item.class);
        List<Predicate> predicates = new ArrayList<>();
        Root<Item> root = criteriaQuery.from(Item.class);

        //Filter and overwrite query.
        if(itemName != null)
        {
            Join<Item, User> userJoin = root.join("user");

            predicates.add(cb.like(root.get("name"), "%" + itemName + "%"));
            predicates.add(cb.equal(userJoin.get("userId"), userId));
        }
        if(catId != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            Join<Item, User> userJoin = root.join("user");

            predicates.add(cb.equal(catJoin.get("catId"), catId));
            predicates.add(cb.equal(userJoin.get("userId"), userId));
        }
        if(typeId != null)
        {
            Join<Item, Type> typeJoin = root.join("type");
            Join<Item, User> userJoin = root.join("user");

            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));
            predicates.add(cb.equal(userJoin.get("userId"), userId));
        }
        if(hasModel != null)
        {
            Join<Item, User> userJoin = root.join("user");

            predicates.add(cb.equal(root.get("hasModel"), 1));
            predicates.add(cb.equal(userJoin.get("userId"), userId));
        }
        if(itemName != null && catId != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            Join<Item, User> userJoin = root.join("user");

            predicates.add(cb.equal(catJoin.get("catId"), catId));
            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));
            predicates.add(cb.equal(userJoin.get("userId"), userId));
        }
        if(itemName != null && typeId != null)
        {
            Join<Item, Type> typeJoin = root.join("type");
            Join<Item, User> userJoin = root.join("user");

            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));
            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));
            predicates.add(cb.equal(userJoin.get("userId"), userId));
        }
        if(itemName != null && hasModel != null)
        {
            Join<Item, User> userJoin = root.join("user");

            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));
            predicates.add(cb.equal(userJoin.get("userId"), userId));
            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));
        }
        if(catId != null && typeId != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            Join<Item, Type> typeJoin = root.join("type");
            Join<Item, User> userJoin = root.join("user");

            predicates.add(cb.equal(catJoin.get("catId"), catId));
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));
            predicates.add(cb.equal(userJoin.get("userId"), userId));
        }
        if(catId != null && hasModel != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            Join<Item, User> userJoin = root.join("user");

            predicates.add(cb.equal(catJoin.get("catId"), catId));
            predicates.add(cb.equal(userJoin.get("userId"), userId));
            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));
        }
        if(typeId != null && hasModel != null)
        {
            Join<Item, Type> typeJoin = root.join("type");
            Join<Item, User> userJoin = root.join("user");

            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));
            predicates.add(cb.equal(userJoin.get("userId"), userId));
            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));
        }
        if(itemName != null && catId != null && typeId != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            Join<Item, Type> typeJoin = root.join("type");
            Join<Item, User> userJoin = root.join("user");

            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));
            predicates.add(cb.equal(catJoin.get("catId"), catId));
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));
            predicates.add(cb.equal(userJoin.get("userId"), userId));
        }
        if(itemName != null && typeId != null && hasModel != null)
        {
            Join<Item, Type> typeJoin = root.join("type");
            Join<Item, User> userJoin = root.join("user");

            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));
            predicates.add(cb.equal(userJoin.get("userId"), userId));

            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));
        }
        if(catId != null && typeId != null && hasModel != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            Join<Item, Type> typeJoin = root.join("type");
            Join<Item, User> userJoin = root.join("user");

            predicates.add(cb.equal(catJoin.get("catId"), catId));
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));
            predicates.add(cb.equal(userJoin.get("userId"), userId));

            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));
        }
        if(itemName != null && catId != null && typeId != null && hasModel != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            Join<Item, Type> typeJoin = root.join("type");
            Join<Item, User> userJoin = root.join("user");

            predicates.add(cb.equal(catJoin.get("catId"), catId));
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));
            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));
            predicates.add(cb.equal(userJoin.get("userId"), userId));

            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));
        }

        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));

        TypedQuery<Item> query = em.createQuery(criteriaQuery);
        query.setFirstResult(Math.toIntExact(pageNum));
        query.setMaxResults(pageSize);
        return new PageImpl<>((query.getResultList()),
                PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.ASC, "date")), getTotalCount(cb));
    }

    /**
     * Fetches the item the user has favourited and filters accordingly.
     * @param pageNum page number.
     * @param itemName name of the item (does not have to be exact).
     * @param catId  ID of the category.
     * @param typeId  ID of the type.
     * @param hasModel if the item has a model.
     * @param userId ID of user.
     * @return Page of items.
     */
    @Override
    public Page<Item> findAllUserFavourites(Integer pageNum, String itemName, Integer catId, Integer typeId, Boolean hasModel,
                                       Long userId)
    {
        List<Long> favourites = favouriteRepository.findByUserId(userId);

        int pageSize = 16;
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Item> criteriaQuery = cb.createQuery(Item.class);
        List<Predicate> predicates = new ArrayList<>();
        Root<Item> root = criteriaQuery.from(Item.class);

        //Filter and overwrite query.
        if(itemName != null)
        {
            predicates.add(cb.like(root.get("name"), "%" + itemName + "%"));
            predicates.add(cb.equal(root.get("userId"), userId));
        }
        if(catId != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            predicates.add(cb.equal(catJoin.get("catId"), catId));
        }
        if(typeId != null)
        {
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));
            predicates.add(cb.equal(root.get("userId"), userId));
        }
        if(hasModel != null)
        {
            predicates.add(cb.equal(root.get("hasModel"), 1));
            predicates.add(cb.equal(root.get("userId"), userId));
        }
        if(itemName != null && catId != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            predicates.add(cb.equal(catJoin.get("catId"), catId));
            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));
            predicates.add(cb.equal(root.get("userId"), userId));
        }
        if(itemName != null && typeId != null)
        {
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));
            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));
            predicates.add(cb.equal(root.get("userId"), userId));
        }
        if(itemName != null && hasModel != null)
        {
            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));
            predicates.add(cb.equal(root.get("userId"), userId));
            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));
        }
        if(catId != null && typeId != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.equal(catJoin.get("catId"), catId));
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));
            predicates.add(cb.equal(root.get("userId"), userId));
        }
        if(catId != null && hasModel != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            predicates.add(cb.equal(catJoin.get("catId"), catId));
            predicates.add(cb.equal(root.get("userId"), userId));
            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));
        }
        if(typeId != null && hasModel != null)
        {
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));
            predicates.add(cb.equal(root.get("userId"), userId));
            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));
        }
        if(itemName != null && catId != null && typeId != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));
            predicates.add(cb.equal(catJoin.get("catId"), catId));
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));
            predicates.add(cb.equal(root.get("userId"), userId));

        }
        if(itemName != null && typeId != null && hasModel != null)
        {
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));
            predicates.add(cb.equal(root.get("userId"), userId));

            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));
        }
        if(catId != null && typeId != null && hasModel != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.equal(catJoin.get("catId"), catId));
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));
            predicates.add(cb.equal(root.get("userId"), userId));

            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));

        }
        if(itemName != null && catId != null && typeId != null && hasModel != null)
        {
            Join<Item, Category> catJoin = root.join("category");
            Join<Item, Type> typeJoin = root.join("type");
            predicates.add(cb.equal(catJoin.get("catId"), catId));
            predicates.add(cb.equal(typeJoin.get("typeId"), typeId));
            predicates.add(cb.like(root.get("name"), "%"+itemName+"%"));
            predicates.add(cb.equal(root.get("userId"), userId));

            if(hasModel)
                predicates.add(cb.equal(root.get("hasModel"), 1));
        }

        Expression<Long> favouritesExpression = root.get("itemId");
        predicates.add(favouritesExpression.in(favourites));
        criteriaQuery.select(root).where(predicates.toArray(new Predicate[]{}));

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
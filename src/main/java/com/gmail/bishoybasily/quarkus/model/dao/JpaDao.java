package com.gmail.bishoybasily.quarkus.model.dao;

import io.reactivex.Observable;
import io.reactivex.Single;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.transaction.TransactionManager;
import java.io.Serializable;

public interface JpaDao<T, Id extends Serializable, Params> {

    TransactionManager getTransactionManager();

    EntityManager getEntityManager();

    default Single<T> persist(T t) {
        return Single.fromCallable(() -> {
            try {
                getTransactionManager().begin();
                getEntityManager().persist(t);
                getTransactionManager().commit();
                return t;
            } catch (Exception e) {
                getTransactionManager().rollback();
                throw e;
            }
        });
    }

    default Single<T> find(Class<T> cls, Id id) {
        return Single.fromCallable(() -> {
            return getEntityManager().find(cls, id);
        });
    }

    default Observable<T> find(Class<T> cl, Params params) {
        return Observable.create(emitter -> {

            try {

                CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
                CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(cl);
                Root<T> root = criteriaQuery.from(cl);

                getEntityManager()
                        .createQuery(createCriteriaQuery(params, root, criteriaBuilder, criteriaQuery))
                        .getResultList()
                        .forEach(emitter::onNext);

                emitter.onComplete();

            } catch (Exception e) {
                emitter.onError(e);
            }

        });
    }

    default Single<T> delete(Class<T> cls, Id id) {
        return find(cls, id).flatMap(t -> {
            return Single.fromCallable(() -> {
                try {
                    getTransactionManager().begin();
                    getEntityManager().detach(t);
                    getTransactionManager().commit();
                    return t;
                } catch (Exception e) {
                    getTransactionManager().rollback();
                    throw e;
                }
            });
        });
    }

    default CriteriaQuery<T> createCriteriaQuery(Params params, Root<T> root, CriteriaBuilder criteriaBuilder, CriteriaQuery<T> criteriaQuery) {
        return criteriaQuery
                .select(createSelect(params, root, criteriaBuilder, criteriaQuery))
                .distinct(createIsDistinct(params, root, criteriaBuilder, criteriaQuery))
                .where(createWhere(params, root, criteriaBuilder, criteriaQuery))
                .groupBy(createGroupBy(params, root, criteriaBuilder, criteriaQuery))
                .having(createHaving(params, root, criteriaBuilder, criteriaQuery))
                .orderBy(createOrderBy(params, root, criteriaBuilder, criteriaQuery));
    }

    default Root<T> createSelect(Params p, Root<T> r, CriteriaBuilder cb, CriteriaQuery<T> cq) {
        return r;
    }

    default boolean createIsDistinct(Params p, Root<T> r, CriteriaBuilder cb, CriteriaQuery<T> cq) {
        return true;
    }

    default Predicate[] createWhere(Params p, Root<T> r, CriteriaBuilder cb, CriteriaQuery<T> cq) {
        return new Predicate[]{};
    }

    default Expression<?>[] createGroupBy(Params p, Root<T> r, CriteriaBuilder cb, CriteriaQuery<T> cq) {
        return new Expression[]{};
    }

    default Predicate[] createHaving(Params p, Root<T> r, CriteriaBuilder cb, CriteriaQuery<T> cq) {
        return new Predicate[]{};
    }

    default Order[] createOrderBy(Params p, Root<T> r, CriteriaBuilder cb, CriteriaQuery<T> cq) {
        return new Order[]{};
    }

}

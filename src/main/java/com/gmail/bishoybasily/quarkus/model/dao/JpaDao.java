package com.gmail.bishoybasily.quarkus.model.dao;

import io.reactivex.Observable;
import io.reactivex.Single;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.transaction.TransactionManager;
import java.io.Serializable;

public interface JpaDao {

    TransactionManager getTransactionManager();

    EntityManager getEntityManager();

    default <T> Single<T> persist(T t) {
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

    default <T, Id extends Serializable> Single<T> find(Class<T> cls, Id id) {
        return Single.fromCallable(() -> {
            return getEntityManager().find(cls, id);
        });
    }

    default <T> Observable<T> find(Class<T> cl) {
        return Observable.create(emitter -> {

            try {

                CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
                CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(cl);
                Root<T> root = criteriaQuery.from(cl);

                getEntityManager()
                        .createQuery(criteriaQuery.select(root))
                        .getResultList()
                        .forEach(emitter::onNext);

                emitter.onComplete();

            } catch (Exception e) {
                emitter.onError(e);
            }

        });
    }

    default <Params, T> Observable<T> find(Class<T> cl, Params params) {
        return Observable.create(emitter -> {

            try {

                CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
                CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(cl);
                Root<T> root = criteriaQuery.from(cl);

                getEntityManager()
                        .createQuery(buildCriteriaQuery(params, root, criteriaBuilder, criteriaQuery))
                        .getResultList()
                        .forEach(emitter::onNext);

                emitter.onComplete();

            } catch (Exception e) {
                emitter.onError(e);
            }

        });
    }

    default <T, Id extends Serializable> Single<T> delete(Class<T> cls, Id id) {
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

    default <Params, T> CriteriaQuery<T> buildCriteriaQuery(Params params, Root<T> root, CriteriaBuilder criteriaBuilder, CriteriaQuery<T> criteriaQuery) {
        return criteriaQuery
                .select(createSelect(params, root, criteriaBuilder, criteriaQuery))
                .distinct(createIsDistinct(params, root, criteriaBuilder, criteriaQuery))
                .where(createWhere(params, root, criteriaBuilder, criteriaQuery))
                .groupBy(createGroupBy(params, root, criteriaBuilder, criteriaQuery))
                .having(createHaving(params, root, criteriaBuilder, criteriaQuery))
                .orderBy(createOrderBy(params, root, criteriaBuilder, criteriaQuery));
    }

    default <Params, T> Root<T> createSelect(Params p, Root<T> r, CriteriaBuilder cb, CriteriaQuery<T> cq) {
        return r;
    }

    default <Params, T> boolean createIsDistinct(Params p, Root<T> r, CriteriaBuilder cb, CriteriaQuery<T> cq) {
        return true;
    }

    default <Params, T> Predicate[] createWhere(Params p, Root<T> r, CriteriaBuilder cb, CriteriaQuery<T> cq) {
        return new Predicate[]{};
    }

    default <Params, T> Expression<?>[] createGroupBy(Params p, Root<T> r, CriteriaBuilder cb, CriteriaQuery<T> cq) {
        return new Expression[]{};
    }

    default <Params, T> Predicate[] createHaving(Params p, Root<T> r, CriteriaBuilder cb, CriteriaQuery<T> cq) {
        return new Predicate[]{};
    }

    default <Params, T> Order[] createOrderBy(Params p, Root<T> r, CriteriaBuilder cb, CriteriaQuery<T> cq) {
        return new Order[]{};
    }

}

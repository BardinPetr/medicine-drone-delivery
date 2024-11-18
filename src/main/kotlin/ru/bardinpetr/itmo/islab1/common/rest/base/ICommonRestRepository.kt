package ru.bardinpetr.itmo.meddelivery.common.rest.base

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.ListPagingAndSortingRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.history.RevisionRepository
import org.springframework.transaction.annotation.Transactional
import ru.bardinpetr.itmo.meddelivery.common.audit.model.RevisionIdType
import ru.bardinpetr.itmo.meddelivery.common.models.IBaseEntity
import ru.bardinpetr.itmo.meddelivery.common.models.IdType


/**
 * Common repository for Entity
 * @param T object type
 */
@Transactional(readOnly = true)
@NoRepositoryBean
interface ICommonRestRepository<T : IBaseEntity> :
    CrudRepository<T, IdType>,
    JpaRepository<T, IdType>,
    ListPagingAndSortingRepository<T, IdType>,
    JpaSpecificationExecutor<T>,
    RevisionRepository<T, IdType, RevisionIdType>

/* Copyright 2015, the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.hibernate.memcached.strategy;

import com.googlecode.hibernate.memcached.region.MemcachedEntityRegion;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.internal.DefaultCacheKeysFactory;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.persister.entity.EntityPersister;

/**
 * Based on Ehcache specific non-strict read/write entity region access strategy.
 */
public class NonStrictReadWriteMemcachedEntityRegionAccessStrategy
        extends AbstractMemcachedAccessStrategy<MemcachedEntityRegion>
        implements EntityRegionAccessStrategy {

    /**
     * Create a non-strict read/write access strategy accessing the given collection region.
     *
     * @param region   The wrapped region
     * @param settings The Hibernate settings
     */
    public NonStrictReadWriteMemcachedEntityRegionAccessStrategy(MemcachedEntityRegion region, SessionFactoryOptions settings) {
        super(region, settings);
    }


    @Override
    public Object get(SharedSessionContractImplementor session, Object key, long txTimestamp) throws CacheException {
        return region().get(key);
    }

    @Override
    public boolean putFromLoad(SharedSessionContractImplementor session, Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride)
            throws CacheException {
        if (minimalPutOverride && region.contains(key)) {
            return false;
        } else {
            region().put(key, value);
            return true;
        }
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Since this is a non-strict read/write strategy item locking is not used.
     */
    @Override
    public SoftLock lockItem(SharedSessionContractImplementor session, Object key, Object version) throws CacheException {
        return null;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Since this is a non-strict read/write strategy item locking is not used.
     */
    @Override
    public void unlockItem(SharedSessionContractImplementor session, Object key, SoftLock lock) throws CacheException {
        region().remove(key);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Returns <code>false</code> since this is an asynchronous cache access strategy.
     */
    @Override
    public boolean insert(SharedSessionContractImplementor session, Object key, Object value, Object version) throws CacheException {
        return false;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Returns <code>false</code> since this is a non-strict read/write cache access strategy
     */
    @Override
    public boolean afterInsert(SharedSessionContractImplementor session, Object key, Object value, Object version) throws CacheException {
        return false;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Removes the entry since this is a non-strict read/write cache strategy.
     */
    @Override
    public boolean update(SharedSessionContractImplementor session, Object key, Object value, Object currentVersion, Object previousVersion)
            throws CacheException {
        remove(session, key);
        return false;
    }

    @Override
    public boolean afterUpdate(SharedSessionContractImplementor session, Object key, Object value, Object currentVersion, Object previousVersion, SoftLock lock)
            throws CacheException {
        unlockItem(session, key, lock);
        return false;
    }

    @Override
    public void remove(SharedSessionContractImplementor session, Object key) throws CacheException {
        region().remove(key);
    }

    @Override
    public Object generateCacheKey(Object id, EntityPersister persister, SessionFactoryImplementor factory, String tenantIdentifier) {
        return DefaultCacheKeysFactory.staticCreateEntityKey(id, persister, factory, tenantIdentifier);
    }

    @Override
    public Object getCacheKeyId(Object cacheKey) {
        return DefaultCacheKeysFactory.staticGetEntityId(cacheKey);
    }

}

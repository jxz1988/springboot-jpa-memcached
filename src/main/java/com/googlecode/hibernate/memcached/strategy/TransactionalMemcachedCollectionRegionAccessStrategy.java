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

import com.googlecode.hibernate.memcached.region.MemcachedCollectionRegion;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.internal.DefaultCacheKeysFactory;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.persister.collection.CollectionPersister;

public class TransactionalMemcachedCollectionRegionAccessStrategy
        extends AbstractMemcachedAccessStrategy<MemcachedCollectionRegion>
        implements CollectionRegionAccessStrategy {

    public TransactionalMemcachedCollectionRegionAccessStrategy(MemcachedCollectionRegion region, SessionFactoryOptions settings) {
        super(region, settings);
    }

    @Override
    public boolean putFromLoad(
            SharedSessionContractImplementor session,
            Object key,
            Object value,
            long txTimestamp,
            Object version,
            boolean minimalPutOverride) throws CacheException {

        if (minimalPutOverride && region.getCache().get(key) != null) {
            return false;
        } else {
            region.getCache().put(key, value);
            return true;
        }
    }

    public Object get(SharedSessionContractImplementor session, Object key, long txTimestamp) throws CacheException {
        return region.getCache().get(key);
    }

    @Override
    public SoftLock lockItem(SharedSessionContractImplementor session, Object key, Object version) throws CacheException {
        return null;
    }

    @Override
    public void unlockItem(SharedSessionContractImplementor session, Object key, SoftLock lock) throws CacheException {
        // no-op
    }


    @Override
    public void remove(SharedSessionContractImplementor session, Object key) throws CacheException {
        region.getCache().remove(key);
    }


    @Override
    public Object generateCacheKey(Object id, CollectionPersister persister, SessionFactoryImplementor factory, String tenantIdentifier) {
        return DefaultCacheKeysFactory.staticCreateCollectionKey(id, persister, factory, tenantIdentifier);
    }

    @Override
    public Object getCacheKeyId(Object cacheKey) {
        return DefaultCacheKeysFactory.staticGetCollectionId(cacheKey);
    }

}

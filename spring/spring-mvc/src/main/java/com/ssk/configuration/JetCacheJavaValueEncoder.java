package com.ssk.configuration;

import com.alicp.jetcache.CacheValueHolder;
import com.alicp.jetcache.support.JavaValueDecoder;
import com.alicp.jetcache.support.JavaValueEncoder;
import lombok.SneakyThrows;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @author ssk
 * @date 2021/3/22
 */
public class JetCacheJavaValueEncoder implements RedisSerializer {

    JavaValueEncoder javaValueEncoder = new JavaValueEncoder(true);
    JavaValueDecoder javaValueDecoder = new JavaValueDecoder(true);

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        CacheValueHolder holder = new CacheValueHolder(o,0L);
        return javaValueEncoder.apply(holder);
    }

    @SneakyThrows
    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        Object o = javaValueDecoder.doApply(bytes);
        if(o != null && o instanceof CacheValueHolder){
            return ((CacheValueHolder) o).getValue();
        }
        return javaValueDecoder.doApply(bytes);
    }
}

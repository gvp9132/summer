package org.gvp.gateway.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.gvp.gateway.pojo.CacheUser;
import org.gvp.gateway.pojo.SecurityPath;
import org.gvp.gateway.pojo.SecurityUser;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.gvp.common.constant.DataTime.*;

@Configuration
public class ReactiveRedisTemplateConfig {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public ReactiveRedisTemplateConfig(){
        this.init();
    }

    private void init(){
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(
                DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class,
                new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(
                DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addDeserializer(LocalTime.class,
                new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        objectMapper.registerModule(javaTimeModule);
    }

    /**
     * 没有注入ObjectMapper时Jackson返回时间类型序列化方式
     * 返回给前端的时间格式定义
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);
            //返回时间数据序列化
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
            //接收时间数据反序列化
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
        };
    }

    @Bean
    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory redisConnectionFactory){
        RedisSerializationContext<String, Object> build =
                this.builderSerializationContext(Object.class);
        return new ReactiveRedisTemplate<String,Object>(redisConnectionFactory, build);
    }

    @Bean
    public ReactiveRedisTemplate<String, CacheUser> loginUserRedisTemplate(ReactiveRedisConnectionFactory redisConnectionFactory){
        RedisSerializationContext<String, CacheUser> build =
                this.builderSerializationContext(CacheUser.class);
        return new ReactiveRedisTemplate<String,CacheUser>(redisConnectionFactory, build);
    }

    @Bean
    public ReactiveRedisTemplate<String, SecurityUser> userRedisTemplate(ReactiveRedisConnectionFactory redisConnectionFactory){
        RedisSerializationContext<String, SecurityUser> build =
                this.builderSerializationContext(SecurityUser.class);
        return new ReactiveRedisTemplate<String,SecurityUser>(redisConnectionFactory, build);
    }

    @Bean
    public ReactiveRedisTemplate<String, SecurityPath> pathRedisTemplate(ReactiveRedisConnectionFactory redisConnectionFactory){
        RedisSerializationContext<String, SecurityPath> build =
                this.builderSerializationContext(SecurityPath.class);
        return new ReactiveRedisTemplate<String,SecurityPath>(redisConnectionFactory, build);
    }

    /**
     * 构建一个指定类型的序列化上下文
     * @param clazz 指定构建的序列化上下文类型
     * @return 返回构建的序列化上下文
     * @param <T> 指定的序列化类型
     */
    public <T> RedisSerializationContext<String, T> builderSerializationContext(Class<T> clazz){
        Jackson2JsonRedisSerializer<T> serializer = new Jackson2JsonRedisSerializer<T>(objectMapper,clazz);
        // 构建(key)String的序列化器
        RedisSerializationContext.SerializationPair<String> stringSerializationPair =
                RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer.UTF_8);
        // 构建值的序列化器
        RedisSerializationContext.SerializationPair<T> objectSerializationPair =
                RedisSerializationContext.SerializationPair.fromSerializer(serializer);
        // 设置序列化器
        RedisSerializationContext.RedisSerializationContextBuilder<String,T> builder =
                RedisSerializationContext.newSerializationContext();
        builder.key(stringSerializationPair);
        builder.value(objectSerializationPair);
        builder.hashKey(stringSerializationPair);
        builder.hashValue(objectSerializationPair);
        builder.string(stringSerializationPair);
        return builder.build();
    }
}

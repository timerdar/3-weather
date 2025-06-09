package org.timerdar.redis;

import com.google.gson.Gson;
import org.timerdar.responses.TimeAndTempList;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisCache{

    private final JedisPool pool;
    private final Gson gson = new Gson();

    public RedisCache(){
        pool =  new JedisPool("localhost", 6379);
    }

    public void setTemperatures(String city, TimeAndTempList temps){
        try(Jedis jedis = pool.getResource()){
            String tempsString = gson.toJson(temps);
            jedis.setex(city, 900, tempsString);
            System.out.println("Добавили кэш для города " + city);
        }catch (Exception e){
            System.out.println("Error while writing temperature to redis: " + e.getMessage());
        }
    }

    public TimeAndTempList getTemperatures(String city){
        try(Jedis jedis = pool.getResource()){
            String tempsString = jedis.get(city);
            return gson.fromJson(tempsString, TimeAndTempList.class);
        }catch (Exception e){
            System.out.println("Redis error: " + e.getMessage());
            return null;
        }
    }
}

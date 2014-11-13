package edu.sjsu.cmpe.cache.client;

import com.google.common.hash.*;

public class Client {


    static int getHash(String key,int size){
      return( Hashing.consistentHash(Hashing.md5().hashString(key), size));
    }
    public static void main(String[] args) throws Exception {
        int bucket;
        String key, value;
        int noOfCaches;
        System.out.println("Starting Cache Client...");
        CacheServiceInterface cache1 = new DistributedCacheService("http://localhost:3000");
        CacheServiceInterface cache2 = new DistributedCacheService("http://localhost:3001");
        CacheServiceInterface cache3 = new DistributedCacheService("http://localhost:3002");
  
        CacheServiceInterface cache[] = {cache1,cache2,cache3};
        String valueArray[] = {"","a","b","c","d","e", "f","g","h","i","j"}; 
        for(int i=1;i<=10;i++){
          key = Integer.toString(i);
          value = valueArray[i];
          noOfCaches = 3;
          bucket = getHash(key,noOfCaches); 
          System.out.println("routed to cache"+bucket);
          cache[bucket].put(i,value);
          System.out.println("put("+i +")=>"+ value);
          String result = cache[(getHash(key,noOfCaches))].get(i);
          System.out.println("get("+i+") => " + result);
        } 
        System.out.println("Existing Cache Client...");    
    }
}

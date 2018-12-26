//package com.yiyiglobal.dp;
//
//
//import com.yiyiglobal.dp.domain.User;
//import com.yiyiglobal.dp.util.redis.service.ObjectRedisService;
//import com.yiyiglobal.dp.util.redis.service.StringRedisService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
////@RunWith(SpringRunner.class)
////@SpringBootTest
//public class RedisTests {
//
//	    @Autowired
//		private ObjectRedisService objectRedisService;
//
//	    @Autowired
//	    private StringRedisService stringRedisService;
//
//	    @Autowired
//		private StringRedisTemplate redisTemplate;
//
//	    private static Logger logger = LoggerFactory.getLogger(RedisTests.class);
//
//		@Test
//		public void test() throws Exception {
//
//			User user = new User();
//			user.setId(20);
//			user.setNickname("超人");
//			Map<String,Object>  map =new HashMap<>();
//			map.put("id",45);
//			map.put("name","superman");
//            String key = "ccc1";
//			String key1 = "ccc2";
//            if(objectRedisService.exists(key)||objectRedisService.exists(key1)){
//               objectRedisService.remove(key);
//               objectRedisService.remove(key1);
//			}
//
//            objectRedisService.set(key,user);
//            objectRedisService.lPush(key1,map);
//
//			logger.info(((User)objectRedisService.get(key)).getNickname());
//			List<Object>  list = objectRedisService.lRange(key1,0,-1);
//			for(Object o:list){
//				logger.info(((Map)o).get("name").toString());
//			}
//
//
//			stringRedisService.set("aaa","qq11aa33");
//			logger.info(stringRedisService.get("aaa"));
//
//			for(Integer i =1;i<56;i++){
//				redisTemplate.opsForValue().increment("ccc3",i);
//				logger.info(redisTemplate.opsForValue().get("ccc3"));
//			}
//
//
//		}
//
//
//}

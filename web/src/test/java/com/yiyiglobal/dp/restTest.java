//package com.yiyiglobal.dp;
//
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
////@RunWith(SpringRunner.class)
////@SpringBootTest
//public class restTest {
//
//
//	private static Logger logger = LoggerFactory.getLogger(restTest.class);
//	private MockMvc mvc;
//
//	@Autowired
//	private WebApplicationContext wac;
//
//	@Before //这个方法在每个方法执行之前都会执行一遍
//	public void setup() {
//		mvc = MockMvcBuilders.webAppContextSetup(wac).build();  //初始化MockMvc对象
//	}
//	@Test
//		public void test() throws Exception {
//
//			// 测试UserController
//			RequestBuilder request = null;
//
//			// 1、get查一下user列表，应该为空
////			request = get("/users/");
////			mvc.perform(request)
////					.andExpect(status().isOk());
//
//			// 2、post提交一个user
//			request = get("/v1/res/list")
//					.param("offset", "0").param("pageSize","1");
//			mvc.perform(request)
//					.andExpect(status().isOk());
//
////			// 3、get获取user列表，应该有刚才插入的数据
////			request = get("/users/");
////			mvc.perform(request)
////					.andExpect(status().isOk());
////
////			// 4、put修改id为2的user
////			request = put("/users/2")
////					.param("name", "钢铁侠2");
////			mvc.perform(request)
////					.andExpect(status().isOk());
////
////			// 5、get一个id为2的user
////			request = get("/users/2");
////			mvc.perform(request)
////					.andExpect(status().isOk());
////
////			// 6、del删除id为2的user
////			request = delete("/users/2");
////			mvc.perform(request)
////					.andExpect(status().isOk());
////
////			// 7、get查一下user列表，应该为空
////			request = get("/users/");
////			mvc.perform(request)
////					.andExpect(status().isOk());
//
//
//		}
//
//
//
//
//}

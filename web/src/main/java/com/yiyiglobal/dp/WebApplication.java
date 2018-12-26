package com.yiyiglobal.dp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication()
@EnableScheduling
@ServletComponentScan
public class WebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 注意这里要指向原先用main方法执行的Application启动类
		return builder.sources(WebApplication.class);
	}

	@EnableAsync
	@Configuration
	class TaskPoolConfig {
		// 任务系统奖励收入线程池
		@Bean("taskIncomeExecutor")
		public Executor taskExecutor() {
			ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
			executor.setCorePoolSize(10);
			executor.setMaxPoolSize(20);
			executor.setQueueCapacity(200);
			executor.setThreadNamePrefix("taskIncomeExecutor-");
			executor.setWaitForTasksToCompleteOnShutdown(true);
			executor.setAwaitTerminationSeconds(60);
			executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
			return executor;
		}
	}
}

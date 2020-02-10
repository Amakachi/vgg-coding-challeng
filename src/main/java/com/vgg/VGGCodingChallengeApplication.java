
package com.vgg;

import com.vgg.model.UserDto;
import com.vgg.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class VGGCodingChallengeApplication {
	
    	public static void main(String[] args) {
    	  ApplicationContext ctx = 
	  		SpringApplication.run(VGGCodingChallengeApplication.class, args);
	  		/*String[] beanNames = ctx.getBeanDefinitionNames();
	  		    Arrays.sort(beanNames);
	  		    System.out.println("***********************");
	  		    for (String beanName : beanNames) {
	  		        System.out.println(beanName);
	  		    }
	  		    System.out.println("***********************");*/
    	  
    	  UserService userService =
    		  ctx.getBean("userService", UserService.class);
    	UserDto loginModel =new UserDto();
    	loginModel.setPassword("toks123");
    	loginModel.setUsername("toks");
//    	 System.out.println(userService.findOne(loginModel.getUsername()).getId());
    	
    }
}

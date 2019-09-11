package nl.rb.demo.personmanagementapp.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.ResourceUtils;

@Getter
@Setter
@Configuration
@ConfigurationProperties("person-management-app")
@PropertySource(factory = YamlPropertySourceFactory.class, value = ResourceUtils.CLASSPATH_URL_PREFIX + "application.yml")
public class AppProperties {

	private Security security;

	@Getter
	@Setter
	public static class Security {
		private boolean enabled;
		private String defaultUsername;
		private String defaultPassword;
		private String requestType;
	}
	

}

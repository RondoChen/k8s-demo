package io.xyzshop.product.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "services")
@PropertySource(value = "classpath:config.yml")
public class ConfigProp {

	@Value("${passport_service_address}")
	private String passportServiceAddress;

	public String getPassportServiceAddress() {
		return passportServiceAddress;
	}

	public void setPassportServiceAddress(String passportServiceAddress) {
		this.passportServiceAddress = passportServiceAddress;
	}
}

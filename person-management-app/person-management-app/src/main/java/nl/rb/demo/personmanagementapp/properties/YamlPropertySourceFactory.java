package nl.rb.demo.personmanagementapp.properties;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.Nullable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

/**
 * Factory to parse YAML property sources.
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(@Nullable final String name, final EncodedResource resource) throws IOException {
    	// Constructs the properties resource from YAML resource.
        Properties propertiesFromYaml = loadYamlIntoProperties(resource);
        
        // Grabs the sourceName.
        String sourceName = Optional.ofNullable(name).orElseGet(() -> resource.getResource().getFilename());
        
        // Returns a new Property Source.
        return new PropertiesPropertySource(sourceName, propertiesFromYaml);
    }

    private Properties loadYamlIntoProperties(final EncodedResource resource) throws FileNotFoundException {
        try {
            // Get YAML properties factory.
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            
            // Set resource.
            factory.setResources(resource.getResource());
            
            // Set properties.
            factory.afterPropertiesSet();
            
            // Return the properties.
            return factory.getObject();
        } catch (IllegalStateException e) {
            // for ignoreResourceNotFound
            Throwable cause = e.getCause();
            if (cause instanceof FileNotFoundException)
                throw (FileNotFoundException) e.getCause();
            throw e;
        }
    }
}
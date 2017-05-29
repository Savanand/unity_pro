package com.unity.jersey;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.unity.jersey.provider.CustomLoggingFilter;
 
public class CustomApplication extends ResourceConfig 
{
    public CustomApplication() 
    {
        packages("com.unity.jersey");
        register(JacksonFeature.class);
 
        register(CustomLoggingFilter.class);
    }
}

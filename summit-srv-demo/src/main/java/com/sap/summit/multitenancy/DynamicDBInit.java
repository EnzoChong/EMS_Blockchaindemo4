package com.sap.summit.multitenancy;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * ClassName: DynamicDBInit <br/>
 * <br/>
 * Description: initialize the multiTenancy service use default dataSource to initialize JPA
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public class DynamicDBInit implements ImportBeanDefinitionRegistrar, EnvironmentAware
{
    private final Logger logger = LoggerFactory.getLogger(DynamicDBInit.class);
    private PropertyValues dataSourcePropertyValues;
    private final ConversionService conversionService = new DefaultConversionService();
    //default dataSource for initialize JPA entityManager
    private DataSource defaultDataSource;

    /**
     * {@inheritDoc}
     * 
     * @see org.springframework.context.EnvironmentAware#setEnvironment(org.springframework.core.env.Environment)
     */
    @Override
    public void setEnvironment(Environment environment)
    {
        logger.info("DynamicDataSourceRegister.setEnvironment()");
        initDefaultDataSource(environment);
    }

    /**
     * Title: initDefaultDataSource <br/>
     * <br/>
     * Description: initDefaultDataSource
     * 
     * @param environment
     * @see
     * @since
     */
    private void initDefaultDataSource(Environment env)
    {
        final RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
        final Map<String, Object> dsMap = new HashMap<>();
        dsMap.put("driverClassName", propertyResolver.getProperty("driverClassName"));
        dsMap.put("url", propertyResolver.getProperty("url"));
        dsMap.put("username", propertyResolver.getProperty("username"));
        dsMap.put("password", propertyResolver.getProperty("password"));
        this.defaultDataSource = this.buildDataSource(dsMap);
        this.dataBinder(defaultDataSource, env);
    }

    /**
     * Title: buildDataSource <br/>
     * <br/>
     * Description:build default dataSource
     * 
     * @param dsMap
     * @return
     * @see
     * @since
     */
    private DataSource buildDataSource(Map<String, Object> dsMap)
    {
        try
        {
            final String driverClassName = dsMap.get("driverClassName").toString();
            final String url = dsMap.get("url").toString();
            final String username = dsMap.get("username").toString();
            final String password = dsMap.get("password").toString();
            final DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(username)
                .password(password);
            return factory.build();
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Title: dataBinder <br/>
     * <br/>
     * Description: add more configuration of spring.dataSource.*
     * 
     * @param dataSource
     * @param env
     * @see
     * @since
     */
    private void dataBinder(DataSource dataSource, Environment env)
    {
        final RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
        dataBinder.setConversionService(this.conversionService);
        dataBinder.setIgnoreNestedProperties(false);//false
        dataBinder.setIgnoreInvalidFields(false);//false
        dataBinder.setIgnoreUnknownFields(true);//true

        if (dataSourcePropertyValues == null)
        {
            final Map<String, Object> rpr = new RelaxedPropertyResolver(env, "spring.datasource").getSubProperties(".");
            final Map<String, Object> values = new HashMap<>(rpr);
            // remove the configurations which have setted
            values.remove("driverClassName");
            values.remove("url");
            values.remove("username");
            values.remove("password");
            dataSourcePropertyValues = new MutablePropertyValues(values);
        }
        dataBinder.bind(dataSourcePropertyValues);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.springframework.context.annotation.ImportBeanDefinitionRegistrar#registerBeanDefinitions(org.springframework.core.type.AnnotationMetadata,
     *      org.springframework.beans.factory.support.BeanDefinitionRegistry)
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry)
    {
        logger.info("DynamicDataSourceRegister.registerBeanDefinitions()");
        //generate DynamicDataSource
        final GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);
        final MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        //property:DynamicDataSource.defaultTargetDataSource
        mpv.addPropertyValue("defaultTargetDataSource", this.defaultDataSource);
        //initialize Bean named dataSource
        registry.registerBeanDefinition("dataSource", beanDefinition);
        DynamicDataSource.getTargetDataSources().put("defaultDataSource", this.defaultDataSource);
    }
}

package com.example.demo;

import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineServicesConfiguration;
import org.camunda.bpm.spring.boot.starter.CamundaBpmActuatorConfiguration;
import org.camunda.bpm.spring.boot.starter.CamundaBpmConfiguration;
import org.camunda.bpm.spring.boot.starter.CamundaBpmPluginConfiguration;
import org.camunda.bpm.spring.boot.starter.CamundaBpmTelemetryConfiguration;
import org.camunda.bpm.spring.boot.starter.event.ProcessApplicationEventPublisher;
import org.camunda.bpm.spring.boot.starter.property.CamundaBpmProperties;
import org.camunda.bpm.spring.boot.starter.property.ManagementProperties;
import org.camunda.bpm.spring.boot.starter.util.CamundaBpmVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

@EnableConfigurationProperties( {
		CamundaBpmProperties.class,
		ManagementProperties.class
} )
@Import( {
		CamundaBpmConfiguration.class,
		CamundaBpmActuatorConfiguration.class,
		CamundaBpmPluginConfiguration.class,
		CamundaBpmTelemetryConfiguration.class,
		SpringProcessEngineServicesConfiguration.class
} )
@Configuration
@ConditionalOnProperty( prefix = CamundaBpmProperties.PREFIX, name = "enabled", matchIfMissing = true )
@AutoConfigureAfter( HibernateJpaAutoConfiguration.class )
class MyCustomCamundaBpmnAutoConfiguration {

	@SuppressWarnings( "SpringJavaInjectionPointsAutowiringInspection" )
	@Configuration
	class ProcessEngineConfigurationImplDependingConfiguration {

		@Autowired
		protected ProcessEngineConfigurationImpl processEngineConfigurationImpl;

		@Bean
		public ProcessEngineFactoryBean processEngineFactoryBean( ) {
			final ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean( );
			factoryBean.setProcessEngineConfiguration( processEngineConfigurationImpl );

			return factoryBean;
		}

		@Bean
		@Primary
		public CommandExecutor commandExecutorTxRequired( ) {
			return processEngineConfigurationImpl.getCommandExecutorTxRequired( );
		}

		@Bean
		public CommandExecutor commandExecutorTxRequiresNew( ) {
			return processEngineConfigurationImpl.getCommandExecutorTxRequiresNew( );
		}

		@Bean
		public CommandExecutor commandExecutorSchemaOperations( ) {
			return processEngineConfigurationImpl.getCommandExecutorSchemaOperations( );
		}
	}

	@Bean
	public CamundaBpmVersion camundaBpmVersion( ) {
		return new CamundaBpmVersion( );
	}

	/*
	 * Begin modified code
	 */

	@Bean
	public ProcessApplicationEventPublisher processApplicationEventPublisher( final ApplicationEventPublisher publisher ) {
		return new MyCustomProcessApplicationEventPublisher( publisher );
	}

	/*
	 * End modified code
	 */

}

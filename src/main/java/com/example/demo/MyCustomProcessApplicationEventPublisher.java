package com.example.demo;

import org.camunda.bpm.spring.boot.starter.event.ProcessApplicationEventPublisher;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;

final class MyCustomProcessApplicationEventPublisher extends ProcessApplicationEventPublisher {

	MyCustomProcessApplicationEventPublisher( final ApplicationEventPublisher publisher ) {
		super( publisher );
	}

	@Override
	public void handleApplicationReadyEvent( final ApplicationReadyEvent applicationReadyEvent ) {
		// Disable default behaviour
	}

	// Omitted modified behaviour

}

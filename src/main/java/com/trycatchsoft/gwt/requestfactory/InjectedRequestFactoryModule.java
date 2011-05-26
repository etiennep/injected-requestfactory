package com.trycatchsoft.gwt.requestfactory;

import com.google.inject.AbstractModule;
import com.google.web.bindery.requestfactory.server.DefaultExceptionHandler;
import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;

/**
 * This Guice module is used to define all the necessary bindings to implement
 * a DI-enabled requestfactory.
 * 
 * @author Etienne Pelletier
 *
 */
public class InjectedRequestFactoryModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ExceptionHandler.class).to(DefaultExceptionHandler.class);
    bind(ServiceLayerDecorator.class).to(InjectedServiceLayerDecorator.class);
    bind(InjectingServiceLocator.class);
  }

}

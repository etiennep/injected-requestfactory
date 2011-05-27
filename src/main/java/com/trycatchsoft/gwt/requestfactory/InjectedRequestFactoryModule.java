/*
 * Copyright 2011 Etienne Pelletier
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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

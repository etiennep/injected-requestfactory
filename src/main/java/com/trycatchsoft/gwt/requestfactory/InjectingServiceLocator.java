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

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.web.bindery.requestfactory.shared.ServiceLocator;

/**
 * This is Guice-injected ServiceLocator.  Use this class to provide dependency-injected
 * instances of your RequestFactory services. When defining a new Request interface,
 * set this in the locator attribute of the @Service annotation.
 * 
 * @author Etienne Pelletier
 *
 */
public class InjectingServiceLocator implements ServiceLocator {

  private final Injector injector;
  
  @Inject
  protected InjectingServiceLocator(final Injector injector) {
    super();
    this.injector = injector;
  }

  @Override
  public Object getInstance(Class<?> clazz) {
    return injector.getInstance(clazz);
  }

}

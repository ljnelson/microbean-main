/* -*- mode: Java; c-basic-offset: 2; indent-tabs-mode: nil; coding: utf-8-unix -*-
 *
 * Copyright © 2017 MicroBean.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.microbean.main;

import java.util.Arrays;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;

import javax.enterprise.event.Observes;

import javax.enterprise.inject.se.SeContainer; // for javadoc only
import javax.enterprise.inject.se.SeContainerInitializer;

import javax.inject.Named;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * A <a href="http://junit.org/junit4/">JUnit</a> test class that
 * exercises the {@link Main} class.
 *
 * @author <a href="http://about.me/lairdnelson"
 * target="_parent">Laird Nelson</a>
 *
 * @see Main
 */
@ApplicationScoped
public class TestMain {

  
  /*
   * Static fields.
   */

  
  /**
   * The number of instances of this class that have been created (in
   * the context of JUnit execution; any other usage is undefined).
   */
  private static int instanceCount;

  
  /*
   * Constructors.
   */
  

  /**
   * Creates a new {@link TestMain}.
   *
   * <p>This constructor will be called by the JUnit framework as well
   * as by an {@link SeContainer} implementation.</p>
   */
  public TestMain() {
    super();
    instanceCount++;
  }


  /*
   * Instance methods.
   */


  /**
   * Asserts that command line arguments have been properly injected.
   *
   * <p>This method is called by the {@link SeContainer} as it comes
   * up.</p>
   *
   * @param event the event representing the CDI container startup;
   * ignored
   *
   * @param args the command line arguments
   */
  private final void onStartup(@Observes @Initialized(ApplicationScoped.class) final Object event,
                               @Named("commandLineArguments") final String[] args) {
    assertTrue(Arrays.equals(new String[] { "a", "b" }, args));
  }

  /**
   * Exercises the {@link Main#main(String[])} method.
   */
  @Test
  public void testContainerStartup() {
    final int oldInstanceCount = instanceCount;
    final SeContainerInitializer containerInitializer = SeContainerInitializer.newInstance();
    assertNotNull(containerInitializer);
    containerInitializer.disableDiscovery();
    containerInitializer.addBeanClasses(this.getClass());
    Main.main(containerInitializer, new String[] { "a", "b" });
    assertEquals(oldInstanceCount + 1, instanceCount);
  }
  
}

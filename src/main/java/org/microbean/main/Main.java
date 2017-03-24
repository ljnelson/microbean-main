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

import javax.enterprise.context.Dependent;

import javax.enterprise.inject.Produces;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * A class whose {@linkplain #main(String[]) <code>main</code> method}
 * {@linkplain SeContainerInitializer#initialize() initializes} a new
 * {@link SeContainer}.
 *
 * <h2>Thread Safety</h2>
 *
 * <p>This class is not safe for concurrent use by multiple
 * threads.</p>
 *
 * @author <a href="http://about.me/lairdnelson"
 * target="_parent">Laird Nelson</a>
 *
 * @see SeContainerInitializer#initialize()
 */
@Singleton
public class Main {


  /*
   * Static fields.
   */

  
  /**
   * Any command line arguments installed by the last invocation of
   * the {@link #main(String[])} method.
   *
   * <p>This field may be {@code null}.</p>
   *
   * @see #getCommandLineArguments()
   *
   * @see #main(String[])
   */
  private static String[] commandLineArguments;


  /*
   * Constructors.
   */

  
  /**
   * Creates a new {@link Main}.
   */
  public Main() {
    super();
  }


  /*
   * Static methods.
   */

  
  /**
   * A <a
   * href="http://docs.jboss.org/cdi/spec/2.0.Beta1/cdi-spec.html#producer_method">producer
   * method</a> that returns the command line arguments stored in the
   * {@link #commandLineArguments} field by the {@link
   * #main(String[])} method.
   *
   * <p>This method never returns {@code null}.</p>
   *
   * @return a {@link String} array of command line arguments; never
   * {@code null}
   *
   * @see #commandLineArguments
   *
   * @see #main(String[])
   */
  @Produces
  @Named("commandLineArguments")
  @Singleton
  private static final String[] getCommandLineArguments() {
    return commandLineArguments;
  }

  /**
   * {@linkplain SeContainerInitializer#initialize() Initializes} a
   * new {@link SeContainer} and then {@linkplain SeContainer#close()
   * closes} it.
   *
   * <p>This method calls the {@link #main(SeContainerInitializer,
   * String[])} method with the return value of the {@link
   * SeContainerInitializer#newInstance()} method and the supplied
   * {@code args} parameter value.</p>
   *
   * @param args command-line arguments; may be {@code null}
   *
   * @see #main(SeContainerInitializer, String[])
   */
  public static final void main(final String[] args) {
    main(SeContainerInitializer.newInstance(), args);
  }
  
  /**
   * {@linkplain SeContainerInitializer#initialize() Initializes} a
   * new {@link SeContainer} and then {@linkplain SeContainer#close()
   * closes} it.
   *
   * <p>This method has a deliberate side effect of making the {@code
   * args} parameter value available in the CDI container in {@link
   * Singleton} scope with a qualifier of {@link
   * Named @Named("commandLineArguments")}.  It also causes an
   * instance of this class to be created by the CDI container in
   * {@link Singleton} scope.</p>
   *
   * @param containerInitializer the {@link SeContainerInitializer} to
   * use to initialize the {@link SeContainer}; may be {@code null} in
   * which case the return value of {@link
   * SeContainerInitializer#newInstance()} will be used instead
   *
   * @param args command-line arguments; may be {@code null}
   *
   * @see SeContainerInitializer#newInstance()
   *
   * @see SeContainerInitializer#initialize()
   *
   * @see SeContainer#close()
   */
  public static final void main(SeContainerInitializer containerInitializer, final String[] args) {
    commandLineArguments = args == null ? new String[0] : args;
    if (containerInitializer == null) {
      containerInitializer = SeContainerInitializer.newInstance();
    }
    assert containerInitializer != null;
    containerInitializer.addBeanClasses(Main.class);
    try (final SeContainer container = containerInitializer.initialize()) {
      assert container != null;
      assert container.select(Main.class).get() != null;
    }
  }
  
}

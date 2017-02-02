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

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

/**
 * A class whose {@linkplain #main(String[]) <code>main</code> method}
 * {@linkplain SeContainerInitializer#initialize() initializes} a new
 * {@link SeContainer}.
 *
 * @author <a href="http://about.me/lairdnelson"
 * target="_parent">Laird Nelson</a>
 *
 * @see SeContainerInitializer#initialize()
 */
public class Main {

  /**
   * Creates a new {@link Main}.
   */
  public Main() {
    super();
  }

  /**
   * {@linkplain SeContainerInitializer#initialize() Initializes} a
   * new {@link SeContainer} and then {@linkplain SeContainer#close()
   * closes} it.
   *
   * @param args command-line arguments; may be {@code null}
   *
   * @see SeContainerInitializer#newInstance()
   *
   * @see SeContainerInitializer#initialize()
   *
   * @see SeContainer#close()
   */
  public static final void main(final String[] args) {    
    final SeContainerInitializer containerInitializer = SeContainerInitializer.newInstance();
    assert containerInitializer != null;
    try (final SeContainer container = containerInitializer.initialize()) {
      assert container != null;

    }
  }
  
}

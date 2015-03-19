/*
 * SonarQube JavaScript Plugin
 * Copyright (C) 2011 SonarSource and Eriks Nukis
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.javascript.model.statement;

import org.junit.Test;
import org.sonar.javascript.model.JavaScriptTreeModelTest;
import org.sonar.plugins.javascript.api.tree.Tree.Kind;
import org.sonar.plugins.javascript.api.tree.statement.VariableStatementTree;

import static org.fest.assertions.Assertions.assertThat;

public class VariableStatementTreeModelTest extends JavaScriptTreeModelTest {

  @Test
  public void single_declaration() throws Exception {
    VariableStatementTree tree = parse("var varDeclaration ;", Kind.VARIABLE_STATEMENT);

    assertThat(tree.is(Kind.VARIABLE_STATEMENT)).isTrue();
    assertThat(tree.declaration().variables()).hasSize(1);
  }

  @Test
  public void multiple_declarations() throws Exception {
    VariableStatementTree tree = parse("var varDeclaration , varDeclaration , varDeclaration ;", Kind.VARIABLE_STATEMENT);

    assertThat(tree.is(Kind.VARIABLE_STATEMENT)).isTrue();
    assertThat(tree.declaration().variables()).hasSize(3);
  }

}

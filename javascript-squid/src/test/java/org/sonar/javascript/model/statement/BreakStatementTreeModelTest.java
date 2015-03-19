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
import org.sonar.javascript.api.EcmaScriptKeyword;
import org.sonar.javascript.model.JavaScriptTreeModelTest;
import org.sonar.plugins.javascript.api.tree.Tree.Kind;
import org.sonar.plugins.javascript.api.tree.statement.BreakStatementTree;

import static org.fest.assertions.Assertions.assertThat;

public class BreakStatementTreeModelTest extends JavaScriptTreeModelTest {

  @Test
  public void with_label() throws Exception {
   BreakStatementTree tree = parse("break label ;", Kind.BREAK_STATEMENT);

    assertThat(tree.is(Kind.BREAK_STATEMENT)).isTrue();
    assertThat(tree.breakKeyword().text()).isEqualTo(EcmaScriptKeyword.BREAK.getValue());
    assertThat(tree.label().name()).isEqualTo("label");
  }

  @Test
  public void without_label() throws Exception {
    BreakStatementTree tree = parse("break ;", Kind.BREAK_STATEMENT);

    assertThat(tree.is(Kind.BREAK_STATEMENT)).isTrue();
    assertThat(tree.breakKeyword().text()).isEqualTo(EcmaScriptKeyword.BREAK.getValue());
    assertThat(tree.label()).isNull();
  }
}

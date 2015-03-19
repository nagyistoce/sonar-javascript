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
import org.sonar.javascript.model.internal.statement.CatchBlockTreeImpl;
import org.sonar.plugins.javascript.api.tree.Tree.Kind;
import org.sonar.plugins.javascript.api.tree.expression.IdentifierTree;
import org.sonar.plugins.javascript.api.tree.statement.CatchBlockTree;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class CatchBlockTreeModelTest extends JavaScriptTreeModelTest {

  @Test
  public void test() throws Exception {
   CatchBlockTree tree = parse("try { } catch ( e ) { }", Kind.CATCH_BLOCK);

    assertThat(tree.is(Kind.CATCH_BLOCK)).isTrue();
    assertThat(tree.catchKeyword().text()).isEqualTo("catch");
    assertThat(tree.openParenthesis().text()).isEqualTo("(");
    assertThat(expressionToString(tree.parameter())).isEqualTo("e");
    assertThat(tree.closeParenthesis().text()).isEqualTo(")");
  }

  @Test
  public void bindingIdentifiers() throws Exception {
    // Identifier
    CatchBlockTreeImpl tree = parse("try { } catch (e) { }", Kind.CATCH_BLOCK);

    List<IdentifierTree> bindingName = tree.parameterIdentifiers();
    assertThat(bindingName).hasSize(1);
    assertThat(bindingName.get(0).name()).isEqualTo("e");

    // Binding pattern
    tree = parse("try { } catch ( {a : e} ) { }", Kind.CATCH_BLOCK);

    bindingName = tree.parameterIdentifiers();
    assertThat(bindingName).hasSize(1);
    assertThat(bindingName.get(0).name()).isEqualTo("e");
  }

}

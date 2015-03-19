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
package org.sonar.javascript.model.expression;

import org.junit.Test;
import org.sonar.javascript.model.JavaScriptTreeModelTest;
import org.sonar.plugins.javascript.api.tree.Tree.Kind;
import org.sonar.plugins.javascript.api.tree.expression.TemplateLiteralTree;

import static org.fest.assertions.Assertions.assertThat;

public class TemplateLiteralTreeModelTest extends JavaScriptTreeModelTest {

  @Test
  public void with_substitution() throws Exception {
    TemplateLiteralTree tree = parse("` characters `", Kind.TEMPLATE_LITERAL);

    assertThat(tree.is(Kind.TEMPLATE_LITERAL)).isTrue();
    assertThat(tree.openBacktick().text()).isEqualTo("`");
    assertThat(tree.strings()).hasSize(1);
    assertThat(tree.strings().get(0).value()).isEqualTo(" characters ");
    assertThat(tree.expressions()).isEmpty();
    assertThat(tree.closeBacktick().text()).isEqualTo("`");
  }

  @Test
  public void without_substitution() throws Exception {
    TemplateLiteralTree tree = parse("` characters1 ${ expression1 } characters2 ${ expression2 } characters3 `", Kind.TEMPLATE_LITERAL);

    assertThat(tree.is(Kind.TEMPLATE_LITERAL)).isTrue();
    assertThat(tree.openBacktick().text()).isEqualTo("`");

    assertThat(tree.strings()).hasSize(3);
    assertThat(tree.strings().get(0).value()).isEqualTo(" characters1 ");
    assertThat(tree.strings().get(1).value()).isEqualTo(" characters2 ");

    assertThat(tree.expressions()).hasSize(2);

    assertThat(tree.closeBacktick().text()).isEqualTo("`");
  }

}

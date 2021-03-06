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
import org.sonar.javascript.api.EcmaScriptKeyword;
import org.sonar.javascript.api.EcmaScriptPunctuator;
import org.sonar.javascript.model.JavaScriptTreeModelTest;
import org.sonar.javascript.model.interfaces.Tree.Kind;
import org.sonar.javascript.model.interfaces.expression.FunctionExpressionTree;

import static org.fest.assertions.Assertions.assertThat;

public class GeneratorExpressionTreeModelTest extends JavaScriptTreeModelTest {

  @Test
  public void named() throws Exception {
    FunctionExpressionTree tree = parse("a = function * f() {}", Kind.GENERATOR_FUNCTION_EXPRESSION);

    assertThat(tree.is(Kind.GENERATOR_FUNCTION_EXPRESSION)).isTrue();
    assertThat(tree.functionKeyword().text()).isEqualTo(EcmaScriptKeyword.FUNCTION.getValue());
    assertThat(tree.name().name()).isEqualTo("f");
    assertThat(tree.star().text()).isEqualTo(EcmaScriptPunctuator.STAR.getValue());
    assertThat(tree.parameters().is(Kind.FORMAL_PARAMETER_LIST)).isTrue();
    assertThat(tree.body()).isNotNull();
  }

  @Test
  public void not_named() throws Exception {
    FunctionExpressionTree tree = parse("a = function * () {}", Kind.GENERATOR_FUNCTION_EXPRESSION);

    assertThat(tree.is(Kind.GENERATOR_FUNCTION_EXPRESSION)).isTrue();
    assertThat(tree.functionKeyword().text()).isEqualTo(EcmaScriptKeyword.FUNCTION.getValue());
    assertThat(tree.name()).isNull();
    assertThat(tree.star().text()).isEqualTo(EcmaScriptPunctuator.STAR.getValue());
    assertThat(tree.parameters().is(Kind.FORMAL_PARAMETER_LIST)).isTrue();
    assertThat(tree.body()).isNotNull();
  }

}

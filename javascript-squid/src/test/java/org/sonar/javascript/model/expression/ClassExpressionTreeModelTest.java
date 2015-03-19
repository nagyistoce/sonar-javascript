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
import org.sonar.javascript.model.JavaScriptTreeModelTest;
import org.sonar.plugins.javascript.api.tree.Tree.Kind;
import org.sonar.plugins.javascript.api.tree.expression.ClassTree;

import static org.fest.assertions.Assertions.assertThat;

public class ClassExpressionTreeModelTest extends JavaScriptTreeModelTest {

  @Test
  public void with_name() throws Exception {
    ClassTree tree = parse("var c = class C { }", Kind.CLASS_EXPRESSION);

    assertThat(tree.is(Kind.CLASS_EXPRESSION)).isTrue();
    assertThat(tree.classToken().text()).isEqualTo("class");
    assertThat(tree.name().name()).isEqualTo("C");
    assertThat(tree.extendsToken()).isNull();
    assertThat(tree.superClass()).isNull();
    assertThat(tree.openCurlyBraceToken().text()).isEqualTo("{");
    // TODO members
    assertThat(tree.closeCurlyBraceToken().text()).isEqualTo("}");
  }

  @Test
  public void without_name() throws Exception {
    ClassTree tree = parse("var c = class { }", Kind.CLASS_EXPRESSION);

    assertThat(tree.is(Kind.CLASS_EXPRESSION)).isTrue();
    assertThat(tree.classToken().text()).isEqualTo(EcmaScriptKeyword.CLASS.getValue());
    assertThat(tree.name()).isNull();
    assertThat(tree.extendsToken()).isNull();
    assertThat(tree.superClass()).isNull();
    assertThat(tree.openCurlyBraceToken().text()).isEqualTo("{");
    // TODO members
    assertThat(tree.closeCurlyBraceToken().text()).isEqualTo("}");
  }

  @Test
  public void extends_clause() throws Exception {
    ClassTree tree = parse("var c = class extends S { }", Kind.CLASS_EXPRESSION);

    assertThat(tree.extendsToken().text()).isEqualTo("extends");
    assertThat(tree.superClass()).isNotNull();
  }

}
